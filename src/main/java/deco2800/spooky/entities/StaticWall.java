package deco2800.spooky.entities;
import deco2800.spooky.worlds.Tile;

public class StaticWall extends StaticEntity {
    private static final String ENTITY_ID_STRING = "staticWall";

    public StaticWall() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public StaticWall(Tile tile, String texture, boolean obstructed) {
        super(tile, 2, texture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        //Empty
    }
}
