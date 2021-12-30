package deco2800.spooky.networking.messages;

import deco2800.spooky.worlds.Tile;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileUpdateMessageTest {

    @Test
    public void setTile() {
        TileUpdateMessage tum = new TileUpdateMessage();
        Tile tile = new Tile();
        tum.setTile(tile);
        assertEquals(tile, tum.getTile());
    }
}