package deco2800.spooky.networking;

import org.junit.Ignore;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class LobbyTest {
    private InetAddress hostIp = InetAddress.getByName("localhost");
    private String userName = "Bob";
    private int lobbyNumber = 1;
    private Lobby lobby = new Lobby(hostIp, userName, lobbyNumber);

    Player player1 = new Player(hostIp.toString(), "alice");
    Player player2 = new Player(hostIp.toString(), "peter");
    Player player3 = new Player(hostIp.toString(), "mary");

    public LobbyTest() throws UnknownHostException {

    }

    @Test
    public void testConstructor() {
        assertEquals("localhost/127.0.0.1", lobby.getHostIp().toString());
        assertEquals(1, lobby.getLobbyID());
        assertEquals("Bob", lobby.getUsername());
    }


    @Test
    public void testAddingPlayersIntoLobby() {
        assertEquals(0, lobby.numOfPlayers());
        lobby.addPlayer("alice", hostIp.toString());
        lobby.addPlayer("peter", hostIp.toString());
        assertEquals(2, lobby.numOfPlayers());
        lobby.addPlayer("steven", hostIp.toString());
        lobby.addPlayer("mary", hostIp.toString());
        lobby.addPlayer("sam", hostIp.toString());
        assertEquals(5, lobby.getPlayers().size());
    }

    @Test
    public void testReadyPlayer() {
        assertEquals(0, lobby.getReadyPlayers().size());
        lobby.addReadyPlayer(player1);
        lobby.addReadyPlayer(player2);
        lobby.addReadyPlayer(player3);
        assertEquals(3, lobby.getReadyPlayers().size());
        assertEquals(player1, lobby.getReadyPlayers().get(0));
        assertEquals(player2, lobby.getReadyPlayers().get(1));
        assertEquals(player3, lobby.getReadyPlayers().get(2));
    }

    @Test
    public void testPlayerDetails() {
        assertEquals("localhost/127.0.0.1", player1.getClientIP());
        assertEquals("localhost/127.0.0.1", player2.getClientIP());

        assertEquals("alice", player1.getUsername());
        assertEquals("mary", player3.getUsername());

        player1.changeId(10000);
        player2.changeId(10001);
        player3.changeId(10002);
        assertEquals(10000, player1.getId());
        assertEquals(10001, player2.getId());
        assertEquals(10002, player3.getId());
    }

    @Test
    public void getLobbyID() {
        assertEquals(1, lobby.getLobbyID());
    }

    @Test
    public void getUsername() {
        assertEquals("Bob", lobby.getUsername());
    }

    @Test
    public void getPlayers() {
        assertEquals(0, lobby.getPlayers().size());
    }

    @Test
    public void numOfPlayers() {
        assertEquals(0, lobby.numOfPlayers());
    }

    @Ignore
    public void getMaxPlayers() {
        assertEquals(2, lobby.getMaxPlayers());
    }

    @Test
    public void addReadyPlayer() {
        assertEquals(0, lobby.getReadyPlayers().size());
        lobby.addReadyPlayer(player1);
        lobby.addReadyPlayer(player2);
        lobby.addReadyPlayer(player3);
        assertEquals(3, lobby.getReadyPlayers().size());
        assertEquals(player1, lobby.getReadyPlayers().get(0));
        assertEquals(player2, lobby.getReadyPlayers().get(1));
        assertEquals(player3, lobby.getReadyPlayers().get(2));
    }

    @Test
    public void getClientIP() {
        assertEquals("localhost/127.0.0.1", player1.getClientIP());
        assertEquals("localhost/127.0.0.1", player2.getClientIP());
        assertEquals("localhost/127.0.0.1", player3.getClientIP());
    }

    @Test
    public void getUserName() {
        assertEquals("alice", player1.getUsername());
        assertEquals("peter", player2.getUsername());
        assertEquals("mary", player3.getUsername());
    }

    @Test
    public void readyPlayer() {
        assertFalse(player1.isReadyGame());
        player1.getReady(true);
        assertTrue(player1.isReadyGame());
    }

    @Test
    public void Id() {
        assertEquals(0, player1.getId());
        player1.changeId(2);
        assertEquals(2, player1.getId());
        player1.changeId(0);
        assertEquals(0, player1.getId());
    }
}