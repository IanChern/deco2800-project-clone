package deco2800.spooky.networking.messages;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Chris;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectMessageTest {

    @Test
    public void setUsername() {
        ConnectMessage cm = new ConnectMessage();
        cm.setUsername("HeyoDeco");
        assertEquals("HeyoDeco", cm.getUsername());
    }

    @Test
    public void setPlayerEntity() {
        Character chris = new Chris(0f, 0f, true);
        ConnectMessage cm = new ConnectMessage();
        cm.setPlayerEntity(chris);
        assertEquals(chris, cm.getPlayerEntity());
    }
}