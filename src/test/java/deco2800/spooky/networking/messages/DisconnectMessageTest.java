package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class DisconnectMessageTest {

    @Test
    public void setUsername() {
        DisconnectMessage dm = new DisconnectMessage();
        dm.setUsername("Deco");
        assertEquals("Deco", dm.getUsername());
    }

    @Test
    public void setDisconnect() {
        DisconnectMessage dm = new DisconnectMessage();
        dm.setDisconnect(true);
        assertTrue(dm.getDisconnect());
    }

    @Test
    public void endGameMessage() {
        EndgameMessage egm = new EndgameMessage();
    }
}