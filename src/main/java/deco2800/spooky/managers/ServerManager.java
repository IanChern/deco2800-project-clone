package deco2800.spooky.managers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.entities.Character;
import deco2800.spooky.networking.Lobby;
import deco2800.spooky.networking.Player;
import deco2800.spooky.networking.messages.*;
import deco2800.spooky.worlds.RandomWorld;
import deco2800.spooky.worlds.Tile;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles server side of networking.
 */
public class ServerManager extends NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(ServerManager.class);

    // number of players to start a game
    private static final int NUMPLAYERS = 1;

    private boolean isHosting = false;
    private boolean gameStart = false;
    private boolean sentTiles = false;

    private Integer id = 0;

    protected int messagesReceived = 0;
    protected int messagesSent = 0;

    private static final int BUFFER_SIZE = 1048576;
    private static final int TCP_PORT = 54556;
    private static final int UDP_PORT = 54778;

    private Server server = null;

    private HashMap<String, Boolean> userConnections = new HashMap<>();
    private HashMap<String, Integer> userId = new HashMap<>();
    private HashMap<String, String> characterSelected = new HashMap<>();

    // Unique identifier for this application. Attempting to connect to a server
    // with the same username as another User on that server is not allowed.
    private String localUsername = "";

    /**
     * Add a user connection to the list of connections.
     *
     * @param username the username of the connected user
     */
    public void addClient(String username) {
        if (!userConnections.containsKey(username)) {
            userConnections.put(username, false);
        } else {
            logger.error("A client is attempting to connect on the same ConnectionID as another client!");
        }
    }

    /**
     * Add a user connection ID to the list of connection, used for single TCP request.
     * @param username the username of the connected user.
     */
    public void addClientID(String username, int connectionID) {
        if (!userId.containsKey(username)){
            userId.put(username,connectionID);
            //id++;
        }
    }

    /**
     * Deletes a mapping between the given client and user.
     * <p>
     *     Once a client disconnects from the server (for whatever reason),
     *     if they had a user then it will be deleted.
     * </p>

     * @param username The username of the client to be removed.
     */
    public void removeClient(String username) {
        userConnections.remove(username);
    }

    public void removeConnectionID(String username) {
        userId.remove(username);
    }

    /**
     * Retrieves clients ready Status
     *
     * @param username The connection username of the client to retrieve the ready status of.
     *
     * @return the username mapped to the given username, or false if it doesn't exist.
     */
    public boolean getReadyStatus(String username) {
        return userConnections.getOrDefault(username, false);
    }

    /**
     * Sets the ready status for a player.
     *
     * @param username the player to set the ready status for
     * @param status the new ready status
     *
     * @return true if the status was updated, false if the player doesnt exist
     */
    public boolean setReadyStatus(String username, boolean status) {
        return userConnections.replace(username, status) != null;
    }

    /**
     * Returns the maximum number of players that can be connected.
     *
     * @return maximum number of clients
     */
    public int maxPlayers() {
        return NUMPLAYERS;
    }

    /**
     * Hosts a game with the specified username on ports (54555, 54777).
     * @param username - the username of the host
     * @return true if
     */
    @SuppressWarnings("rawtypes")
	public boolean startHosting(String username) throws UnknownHostException {
        this.localUsername = username;
        logger.info("Hosting with username {}", this.localUsername);
        if (isHosting) {
            return false;
        }
        isHosting = true;

        server = new Server(BUFFER_SIZE, BUFFER_SIZE);

        // Register the classes to be serialised with kryo
        Kryo kryo = server.getKryo();
        kryo.setReferences(true);
        // KYRO auto registration
        kryo.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // Start the server on ports 54555 and 54777
        server.start();
        try {
            server.bind(TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            logger.error("Failed to bind server to ports 54555 and 54777");
            return false;
        }

        GameManager.get().setLobby(gameLobby("localhost", username));
        this.lobby = GameManager.get().getLobby();
        logger.info("Successfully create a new lobby at ip:" + this.lobby.getHostIp().toString());
        // Add all listeners for the host, allowing it to receive information from all its clients.
        logger.info("Attempting to add message listeners to server.");
        addListeners(server);

        logger.info("HOST WAS INITIALISED SUCCESSFULLY.");
        return true;
    }

    /**
     * Sets up listeners for server.
     *
     * @param server the server
     */
    private void addListeners(Server server) {
        server.addListener(new Listener() {
            @Override
            public void received (Connection connection, Object object) {
                messagesReceived++;
                if (object instanceof SingleEntityUpdateMessage) {
                    GameManager.get().getWorld().updateEntity(((SingleEntityUpdateMessage) object).getEntity());
                } else if (object instanceof ChatMessage) {
                    // Forward the chat message to all clients
                    server.sendToAllTCP(object);
                    GameManager.get().getManager(OnScreenMessageManager.class).addMessage(object.toString());
                } else if (object instanceof ConnectMessage) {
                    // player is connecting
                    lobbyGetPlayers(connection, (ConnectMessage) object);
                } else if (object instanceof ReconnectMessage) {
                    // player reconnecting
                    reconnectPlayer(connection, (ReconnectMessage)object);
                } else if (object instanceof TileDeleteMessage) {
                    // delete tile in world. will be sent to other clients in next OnTick()
                    GameManager.get().getWorld().deleteTile(((TileDeleteMessage) object).getTileID());
                } else if (object instanceof ReadyMessage) {
                    // player is ready to begin
                    readyMessageSettings((ReadyMessage) object);
                } else if (object instanceof EndgameMessage){
                    // game is over
                    //endGame();
                } else if (object instanceof DisconnectMessage){
                    // client attempting to disconnect
                    if (((DisconnectMessage) object).getDisconnect()){
                        removeClient(((DisconnectMessage) object).getUsername());
                        removeConnectionID(((DisconnectMessage) object).getUsername());
                    }
                } else if (object instanceof CharacterSelectedMessage){
                    // player has selected a character
                    characterSelectedSettings(connection, (CharacterSelectedMessage) object);
                }
            }
        });
    }

    private void reconnectPlayer(Connection connection, ReconnectMessage m) {
        logger.info("{} is reconnecting", m.getUsername());
        addClient(m.getUsername());
        addClientID(m.getUsername(), connection.getID());
        sendChatMessage(connection.getID() + " connected.");
        sendLobby(lobby);
        gameStart = gameReady();
    }

    private void lobbyGetPlayers(Connection connection, ConnectMessage object) {
        // add client to list of connected clients
        if (lobby.getPlayers().size() < NUMPLAYERS){
            addClient(object.getUsername());
            addClientID(object.getUsername(), connection.getID());
            lobby.addPlayer(object.getUsername(), connection.getRemoteAddressTCP().toString());
            sendLobby(lobby);
        } else {
            //refuse connection
        }
    }

    private void readyMessageSettings(ReadyMessage object) {
        // set player ready status
        setReadyStatus(object.getUsername(), object.getIsReady());
        lobby.getPlayers().get(userId.get(object.getUsername())).getReady(object.getIsReady());
        logger.info("Player [" + lobby.getPlayers().get(userId.get(object.getUsername())).getUsername()+"] is ready");
        server.sendToAllTCP(object);
        gameStart = gameReady();
    }  

    private void characterSelectedSettings(Connection connection, CharacterSelectedMessage object) {
        if(characterSelected.get(object.getCharacter()) == null){
            logger.info("Player " + object.getUsername() + " chose character " + object.getCharacter());
            characterSelected.put(object.getCharacter(), object.getUsername());
            lobby.getPlayersByName().get(object.getUsername()).setCharacter(object.getCharacter());
        } else {
            CharacterSelectedMessage msg = new CharacterSelectedMessage(object.getCharacter());
            msg.setSuccess(false);
            server.sendToTCP(connection.getID(),msg);
        }
    }

    /**
     * Determines whether the game is ready to start or not.
     * 
     * @return true iff all players are ready
     */
    private boolean gameReady() {
        List<Player> players = lobby.getPlayers();
        if (players.size() == NUMPLAYERS){
            for (Player player: players){
                if(player.getCharacter() == null || player.getCharacter() == ""){
                    logger.info("Player " + player.getUsername() + " haven't choose character");
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Tells players the game is ready and sends them the tilemap.
     */
    private void sendGame() {
        // send tilemap
        logger.info("Sending tilemap...");
        TileUpdateMessage message = new TileUpdateMessage();
        List<Tile> tiles = GameManager.get().getWorld().getTileMap();

        for (Tile t : tiles) {
            if (t != null){
                message.setTile(t);
                server.sendToAllTCP(message);
            }
        }
    }

    /**
     * Returns the number of players connected.
     *
     * @return number of connected players
     */
    public int numPlayers() {
        return this.userConnections.size();
    }

    @Override
    public void onTick(long i) {
        if (gameStart) {
            if (!sentTiles) {
                //sendGame();
                logger.info("Sending characters...");
                for (Map.Entry<String, String> d : characterSelected.entrySet()) {
                    String c = d.getKey();
                    Character newChar = ((RandomWorld)(GameManager.get().getWorld())).makeCharacter(c);
                    SingleEntityUpdateMessage m = new SingleEntityUpdateMessage();
                    m.setEntity(newChar);
                    server.sendToTCP(userId.get(characterSelected.get(c)), m);
                }
                sentTiles = true;
                logger.info("GAME HAS STARTED");
            } else {
                //Only send messages if the game has begun
                for (AbstractEntity e : GameManager.get().getWorld().getEntities()) {
                    if (e instanceof deco2800.spooky.entities.Character) {
                        continue;
                    }
                    SingleEntityUpdateMessage message = new SingleEntityUpdateMessage();
                    message.setEntity(e);
                    server.sendToAllTCP(message);
                }
            }
        }
    }

    /**
     * Sends a chat message to the network clients
     * @param message
     */
    @Override
    public void sendChatMessage(String message) {
        /* Overrided by extending class */
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("[" + this.localUsername + "] " + message);
        server.sendToAllTCP(new ChatMessage(this.localUsername, message));
    }

    
    /**
     * Get the ID of the server
     *
     * @return the server id
     */
    public int getID() {
        if (isHosting) {
            return 0;
        }
        return -1;
    }

    /**
     * Delete the given tile
     * @param t - the tile to delete
     */
    public void deleteTile(Tile t) {
        TileDeleteMessage msg = new TileDeleteMessage();
        msg.setTileID(t.getTileID());
        server.sendToAllTCP(msg);
    }

    /**
     * Delete the given entity.
     * <p>
     *     The host will ensure the entity has also been deleted in its own game.
     * </p>
     * @param e - the entity to delete
     */
    public void deleteEntity(AbstractEntity e) {
        EntityDeleteMessage msg = new EntityDeleteMessage();
        msg.setEntityID(e.getEntityID());
        server.sendToAllTCP(msg);
    }

    /**
     * Send lobby data to new connections
     * @param lobby
     */
    public void sendLobby(Lobby lobby) {
        GameManager.get().getManager(OnScreenMessageManager.class).addMessage("[" + this.localUsername + "] " + "Sending Lobby");
        server.sendToAllTCP(new ChatMessage(this.localUsername, "Sending Lobby"));
        server.sendToAllTCP(lobby);
    }


    public void endGame() {
        EndgameMessage msg = new EndgameMessage();
        server.sendToAllTCP(msg);

    }
}
