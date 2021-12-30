package deco2800.spooky.networking;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player("localhost/127.0.0.1", "alice");

    @Test
    public void testContructor() {
        assertEquals("localhost/127.0.0.1", player.getClientIP());
        assertEquals("alice", player.getUsername());
        assertFalse(player.isReadyGame());
    }

    @Test
    public void testIsReadyGame() {
        player.getReady(true);
        assertTrue(player.isReadyGame());

        player.getReady(false);
        assertFalse(player.isReadyGame());
    }

    @Test
    public void testGetId() {
        player.changeId(10);
        assertEquals(10, player.getId());
    }

    @Test
    public void testGetCharacter1() {
        player.setCharacter("damien");
        assertEquals("damien", player.getCharacter());
    }
}
