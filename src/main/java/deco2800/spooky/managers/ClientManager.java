package deco2800.spooky.managers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.networking.Lobby;
import deco2800.spooky.networking.messages.*;
import deco2800.spooky.worlds.Tile;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A manager class to handle messages and behavior of client (as the remaining
 * players)
 *
 * @author Frazer Carsley
 * */
public class ClientManager extends NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(ClientManager.class);
    
    protected int messagesReceived = 0;
    protected int messagesSent = 0;
    protected int entCount = 0;

    private static final int TCP_PORT = 54556;
    private static final int UDP_PORT = 54778;

    private boolean isConnected = false;
    private boolean gameStart = false;
    private boolean gameEnd = false;


    protected String localUsername = "";
    protected String character;

    private Client client = new Client(16384, 16384);

    private Lobby clientLobby = null;

    /**
     * Connects to a host at specified ip and port
     * @param ip - the ip of the host
     * @param username - the username to connect with
     * @return boolean - Was the connection successful?
     */
    @SuppressWarnings("rawtypes")
	public boolean connectToHost(String ip, String username) {
        this.localUsername = username;
        logger.info("Connecting with username {}", this.localUsername);
        if (isConnected) {
            return false;
        }
        isConnected = true;
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Attempting to connect to server...");

        // Register all classes
        kyroCalled(ip);

        logger.info("Sending initial connect message to host, requesting initial information to be sent to this client.");
        
        ConnectMessage request = new ConnectMessage();
        request.setUsername(username);
        client.sendTCP(request);
        messagesSent++;

        // Broadcast to the host in-game that this client has connected.
        sendChatMessage("Joined the game.");

        // Broadcast to this client in-game that it has successfully connected to the server.
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Connected to server.");
        logger.info("CLIENT WAS INITIALISED SUCCESSFULLY.");
        return true;
    }

    private void kyroCalled(String ip) {
        Kryo kryo = client.getKryo();
        kryo.setReferences(true);
        // KYRO auto registration
        kryo.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // Start the client on ports 54555 and 54777
        client.start();
        try {
            client.connect(5000, ip, TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            logger.error("Failed initial connection due to {}", e);
            GameManager.get().getManager(OnScreenMessageManager.class).addMessage("Connection failed.");
            return;
        }

        // Add all listeners for the client, allowing it to receive information from the host.
        addListeners(client);
    }

    /**
     * Client reconnects to host after disconencting and starting game world.
     *
     * @param ip host ip address
     * @param username local username of client
     *
     * @return true if connects successfully
     */
	public boolean reconnect(String ip, String username) {
        this.localUsername = username;
        logger.info("Reconnecting with username {}", this.localUsername);
        if (isConnected) {
            logger.info("Already connected");
            return false;
        }
        isConnected = true;

        // Register all classes
        kyroCalled(ip);

        ReconnectMessage request = new ReconnectMessage();
        request.setUsername(username);
        client.sendTCP(request);
        messagesSent++;

        logger.info("RECONNECTED SUCCESSFULLY");

        return true;
    }

    /**
     * Adds listeners to the client so it can receive info from the host.
     *
     * @param client the client
     */
    private void addListeners(Client client) {
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                messagesReceived++;
                logger.info("{}", object);
                if (object instanceof TileUpdateMessage) {
                    // update tile
                    GameManager.get().getWorld().updateTile(((TileUpdateMessage) object).getTile());
                    GameManager.get().getWorld().generateNeighbours();
                } else if (object instanceof ChatMessage) {
                    // received chat message
                    GameManager.get().getManager(OnScreenMessageManager.class).addMessage(object.toString());
                } else if (object instanceof SingleEntityUpdateMessage){
                    // update entity
                    entCount++;
                    GameManager.get().getWorld().updateEntity(((SingleEntityUpdateMessage) object).getEntity());
                    logger.info("Get entities update: " + entCount);
                } else if (object instanceof TileDeleteMessage) {
                    // delete tile
                    GameManager.get().getWorld().deleteTile(((TileDeleteMessage) object).getTileID());
                } else if (object instanceof EntityDeleteMessage) {
                    // delete entity
                    GameManager.get().getWorld().deleteEntity(((EntityDeleteMessage) object).getEntityID());
                } else if (object instanceof ReadyMessage) {
                    // check game ready to start
                    clientLobbyPlayer((ReadyMessage) object);
                } else if (object instanceof Lobby) {
                    // the lobby has been sent
                    clientLobby = (Lobby) object;
                    logger.info("In lobby: " + ((Lobby) object).getUsername());
                } else if (object instanceof EndgameMessage) {
                    //reply to server endgame prompt
                    endGameMessage();
                } else if (object instanceof CharacterSelectedMessage && !((CharacterSelectedMessage) object).getSuccess()){
                    //if character is selected by another user
                    GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Major","Character Selected by another User",0);
                }
            }
         });
    }

    private void clientLobbyPlayer(ReadyMessage object) {
        if(!object.getUsername().equals("host")){
            if(this.clientLobby != null){
                clientLobby.getPlayersByName().get(object.getUsername()).getReady(true);
                logger.info("Player [" + object.getUsername() +"] is ready");
            }
        } else {
            setReadyStatus(object);
        }
    }

    private void endGameMessage() {
        if(GameManager.get().getManager(PopUpManager.class).endGamePrompt()){
            DisconnectMessage msg = new DisconnectMessage();
            msg.setDisconnect(true);
            msg.setUsername(localUsername);
            client.sendTCP(msg);
            client.stop();
        } else {
            DisconnectMessage msg = new DisconnectMessage();
            msg.setDisconnect(false);
            msg.setUsername(localUsername);
            client.sendTCP(msg);
        }
    }

    /**
     * Determines whether the game is ready to play or not.
     *
     * @param m the message that was sent
     */
    private void setReadyStatus(ReadyMessage m) {
        if ((m.getUsername().equals("host")) && (m.getIsReady())) {
            this.gameStart = true;
        }
    }

    /**
     * Sends a ready message with the given status.
     *
     * @param status the status of the ready message.
     */
    public void sendReadyMessage(boolean status) {
        ReadyMessage m = new ReadyMessage();
        m.setReady(status);
        m.setUsername(this.localUsername);
        
        client.sendTCP(m);
    }  

    /**
     * Returns whether the game is ready to start or not.
     *
     * @return gameStart
     */
    public boolean gameReady() {
        return this.gameStart;
    }

    /**
     * Stops execution of the client.
     */
    public void stop() {
        this.client.stop();
    }

    /**
     * Closes the client.
     */
    public void close() {
        this.client.close();
    }

    @Override
    public void onTick(long i) {
        if (gameStart) {
            for (AbstractEntity e : GameManager.get().getWorld().getEntities()) {
                if (!(e instanceof deco2800.spooky.entities.Character)) {
                    continue;
                }
                SingleEntityUpdateMessage message = new SingleEntityUpdateMessage();
                message.setEntity(e);
                client.sendTCP(message);
            }
        }
    }

    /**
     * Sends a chat message to the network clients
     * @param message
     */
    @Override
    public void sendChatMessage(String message) {
        client.sendTCP(new ChatMessage(this.localUsername, message));
        messagesSent++;
    }

    /**
     * Get the ID of the client.
     *
     * @return id of the client
     */
    @Override
    public int getID() {
        if (isConnected) {
            return client.getID();
        }

        return -1;
    }

    /**
     * Delete the given tile
     * @param t - the tile to delete
     */
    @Override
    public void deleteTile(Tile t) {
        TileDeleteMessage msg = new TileDeleteMessage();
        msg.setTileID(t.getTileID());
        client.sendTCP(msg);
    }
    
    /**
     * Delete the given entity.
     * <p>
     *     Client deletion of entities is handled in the EntityDeleteMessage class,
     *     which will be sent by the server once it receives the EntityDeleteMessage
     *     from the client.
     * </p>
     * @param e - the entity to delete
     */
    @Override
    public void deleteEntity(AbstractEntity e) {
        EntityDeleteMessage msg = new EntityDeleteMessage();
        msg.setEntityID(e.getEntityID());
        client.sendTCP(msg);
    }

    public Lobby getClientLobby() {
        return clientLobby;
    }

    /**
     * This method should be called when the client selected a character.
     */
    public void selectCharacter(String character) {
        this.character = character;
        CharacterSelectedMessage msg = new CharacterSelectedMessage(character);
        msg.setUsername(localUsername);
        client.sendTCP(msg);
    }

    /**
     * For when a client disconnects.
     */
    public void disconnect() {
        DisconnectMessage m = new DisconnectMessage();
        m.setUsername(this.localUsername);
        m.setDisconnect(true);
        client.sendTCP(m);

        client.stop();
        client.close();

        isConnected = false;
    }

    public void setGameEnd(){
        this.gameEnd = true;
        EndgameMessage msg = new EndgameMessage();
        client.sendTCP(msg);
    }

    public boolean getGameEnd(){
        return this.gameEnd;
    }
}
