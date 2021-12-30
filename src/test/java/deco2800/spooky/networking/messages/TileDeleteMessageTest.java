package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileDeleteMessageTest {

    @Test
    public void setTileID() {
        TileDeleteMessage tdm = new TileDeleteMessage();
        tdm.setTileID(2801);
        assertEquals(2801, tdm.getTileID());
    }

}