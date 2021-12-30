package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityDeleteMessageTest {

    @Test
    public void setEntityID() {
        EntityDeleteMessage edm = new EntityDeleteMessage();
        edm.setEntityID(2800);
        assertEquals(2800, edm.getEntityID());
    }
}