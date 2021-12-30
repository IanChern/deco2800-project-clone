package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterSelectedMessageTest {

    @Test
    public void getCharacter() {
        CharacterSelectedMessage csm = new CharacterSelectedMessage();
        assertNull(csm.getCharacter());
        assertTrue(csm.getSuccess());
        assertNull(csm.getUsername());
    }

    @Test
    public void setSuccess() {
        CharacterSelectedMessage csm = new CharacterSelectedMessage();
        csm.setSuccess(false);
        assertFalse(csm.getSuccess());
    }

    @Test
    public void setUsername() {
        CharacterSelectedMessage csm = new CharacterSelectedMessage();
        csm.setUsername("HelloDeco");
        assertEquals("HelloDeco", csm.getUsername());
    }

    @Test
    public void constructor() {
        CharacterSelectedMessage csm = new CharacterSelectedMessage("HeyDECO");
        assertEquals("HeyDECO", csm.getCharacter());
    }
}