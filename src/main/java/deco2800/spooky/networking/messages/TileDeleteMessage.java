package deco2800.spooky.networking.messages;

public class TileDeleteMessage implements Message {
    private int tileID;

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public int getTileID() {
        return(tileID);
    }
}
