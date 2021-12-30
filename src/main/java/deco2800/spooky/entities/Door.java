package deco2800.spooky.entities;

import deco2800.spooky.worlds.Tile;

/**
 * This class is the entity handling all of the interactions and textures associated with doors
 * and moving the placer between one door and another
 * This class can hold the information for moving a character between rooms if necessary
 *
 * @author Jacob Watson @Jclass100
 */
public class Door extends StaticEntity{
    private static final String ENTITY_ID_STRING = "Door";

    public Door() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Door(Tile tile, String texture, boolean obstructed) {
        super(tile, 2, texture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        //Currently empty
    }
}
