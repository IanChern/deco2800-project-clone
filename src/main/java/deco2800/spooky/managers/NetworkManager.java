package deco2800.spooky.managers;

import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.networking.Lobby;
import deco2800.spooky.worlds.Tile;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * NetworkManager
 *
 * Abstract class for both server and client side of the networking stack.
 */
public abstract class NetworkManager extends TickableManager  {
    protected int messagesReceived = 0;
    protected int messagesSent = 0;

    protected String localUsername = "";
    protected Lobby lobby;

    public Lobby gameLobby(String hostIP, String hostUsername) throws UnknownHostException {
        lobby = new Lobby(InetAddress.getByName(hostIP),hostUsername, 1);
        return lobby;
    }
    /**
     * Increments the number of messages received.
     * <p>
     *     Used be the client and server listeners to update the number of
     *     messages received by this particular NetworkManager.
     * </p>
     */
    public void incrementMessagesReceived() {
        messagesReceived++;
    }

    /**
     * Returns the number of messages received
     * @return the number of messages received
     */
    public int getMessagesReceived() {
        return messagesReceived;
    }

    /**
     * Returns the number of messages sent
     * @return the number of messages sent
     */
    public int getMessagesSent() {
        return messagesSent;
    }

    /**
     * On tick method
     * @param i
     */
    @Override
    public void onTick(long i) {
        /* Overrided by extending class */
    }

    /**
     * Sends a chat message to the network clients
     * @param message
     */
    public abstract void sendChatMessage(String message);

    /**
     * Get the username of a host/client
     * @return String - the username of the host/client
     */
    public String getUsername() {
        return localUsername;
    }

    /**
     * Get the ID of the manager
     * 
     * @return the id of the manager
     */
    public int getID() {
        /* Overrided by extending class */
        return -1;
    }

    public Lobby getLobby(){
        return this.lobby;
    }
    /**
     * Delete the given tile
     * @param t - the tile to delete
     */
    public abstract void deleteTile(Tile t);

    /**
     * Delete the given entity.
     * <p>
     *     The host will ensure the entity has also been deleted in its own game.
     *     Client deletion of entities is handled in the EntityDeleteMessage class,
     *     which will be sent by the server once it receives the EntityDeleteMessage
     *     from the client.
     * </p>
     * @param e - the entity to delete
     */
    public abstract void deleteEntity(AbstractEntity e);
}
