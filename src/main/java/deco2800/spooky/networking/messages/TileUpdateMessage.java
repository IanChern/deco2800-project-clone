package deco2800.spooky.networking.messages;

import deco2800.spooky.worlds.Tile;

public class TileUpdateMessage implements Message {
    private Tile tile;

    public Tile getTile() {
        return(tile);
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
