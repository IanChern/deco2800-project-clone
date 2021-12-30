package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReadyMessageTest {

    @Test
    public void setUsername() {
        ReadyMessage rm = new ReadyMessage();
        rm.setUsername("CheckOneTwo");
        assertEquals("CheckOneTwo", rm.getUsername());
    }

    @Test
    public void setReady() {
        ReadyMessage rm = new ReadyMessage();
        rm.setReady(true);
        assertTrue(rm.getIsReady());
    }

    @Test
    public void constructor() {
        ReadyMessage rm = new ReadyMessage("rm", false);
        assertEquals("rm", rm.getUsername());
        assertFalse(rm.getIsReady());
    }
}