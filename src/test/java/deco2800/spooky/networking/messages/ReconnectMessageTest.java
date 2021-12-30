package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReconnectMessageTest {

    @Test
    public void setUsername() {
        ReconnectMessage rm = new ReconnectMessage();
        rm.setUsername("Deco");
        assertEquals("Deco", rm.getUsername());
    }
}