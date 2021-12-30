package deco2800.spooky.networking.messages;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Chris;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingleEntityUpdateMessageTest {

    @Test
    public void setEntity() {
        SingleEntityUpdateMessage seum = new SingleEntityUpdateMessage();
        Character chris = new Chris(0f, 0f, true);
        seum.setEntity(chris);
        assertEquals(chris, seum.getEntity());
    }
}