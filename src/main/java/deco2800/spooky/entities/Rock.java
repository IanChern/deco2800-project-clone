package deco2800.spooky.entities;
import deco2800.spooky.worlds.Tile;

public class Rock extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "rock";
    
    public Rock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Rock(Tile tile, boolean obstructed) {
        super(tile, 2, "rock", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        /* Rocks do nothing */
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
