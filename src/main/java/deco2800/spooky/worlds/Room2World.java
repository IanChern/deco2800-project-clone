package deco2800.spooky.worlds;

import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for creating room graphically from room data
 * @author Hanh La and Jacob Watson
 *
 */
public class Room2World {
    private Room room;
    private Map<HexVector,String>  textures = new HashMap<>();
    private Map<HexVector, Integer> walls = new HashMap<>();
    private Map<HexVector,Integer>  doors = new HashMap<>();
    private HexVector[] placeExit = new HexVector[6];

    //Trap Tiles
    private List<RoomTrap> roomTraps = new ArrayList<>();

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Room2World.class.getPackage().getName());


    /**
     * Creating Room2World class from Room object
     * @param room object to be graphically placed into the world
     */
    public Room2World(Room room){
        setRoom(room);
        setExits();
        setTextures();
    }

    public List<RoomTrap> getTraps() {
        return roomTraps;
    }

    public void addTraps(RoomTrap trap) { roomTraps.add(trap); }

    public Room getRoom() {
        return room;
    }

    public Map<HexVector, String> getTextures() {
        return textures;
    }
    public Map<HexVector, Integer> getWalls() { return walls; }
    public Map<HexVector, Integer> getDoors() {
        return doors;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    /**
     * Determine which direction and location all the walls are doors should be placed
     *
     * @author Jacob Watson @Jclass100
     */
    private void setTextures(){
        if (this.room == null) {
            return;
        }
        //radius is known as numberOfLayers in .room file, adding 1 here the walls should be 1 out
        int radius = this.room.getRadius() + 1;
        //used if an exit has been placed to not put a wall or corner
        boolean isExit;
        //used if a corner has been placed to not put a wall
        boolean isCorner;

        float offset = 0.0f;

        //these two loops go through the values of a hexVector on the World
        for (float i = -radius; i <= radius; i++){
            for (float j = -radius - offset; j <= radius; j++) {

                //placeExit has been populated by setExits called before this method
                //We are assigning textures to the exits here
                isExit = doorTextureExit(i, j);
                //if there is exit don't place a wall or corner
                if (isExit) {
                    continue;
                }
                //Corner Textures
                isCorner = cornerTexture(i, j, radius); //If there is a corner dont put a wall
                if (isCorner) {
                    LOGGER.debug("Corner added at {} {}", i, j);
                    continue;
                }
                wallTexture(i, j, radius);
            }
            offset = (Math.abs(i) % 2 == 0) ? 0.5f : 0f;

        }
    }

    private boolean doorTextureExit(float i, float j) {
        boolean exit = false;
        for (int k = 0; k < 6; k++){
            //if there is an exist at the current hexVector in loops place the texture
            if (this.placeExit[k] != null && (i == this.placeExit[k].getCol() && j == this.placeExit[k].getRow())){
                exit = true;
                //col and row to exit number k in doors map
                doors.put(new HexVector(i, j), k);
                LOGGER.debug("Door added at {} {} and is door k", i, j, k);
            }
        }
        return exit;
    }

    private boolean cornerTexture(float i, float j, int radius) {
        boolean isCorner = false;
        if (i == radius) {  //East corners
            if (j == radius/2f && GameManager.get().getWorld().getTile(i, j) != null) {
                walls.put(new HexVector(i, j), 1+6); //NE
                isCorner = true;
            } else if (j == -radius/2f && GameManager.get().getWorld().getTile(i, j) != null){
                walls.put(new HexVector(i, j), 5+6); //SE
                isCorner = true;
            }
        } else if (i == -radius) { //West corners
            if (j == radius/2f && GameManager.get().getWorld().getTile(i, j) != null) {
                walls.put(new HexVector(i, j), 2+6); //NW
                isCorner = true;
            } else if (j == -radius/2f && GameManager.get().getWorld().getTile(i, j) != null){
                walls.put(new HexVector(i, j), 4+6); //SW
                isCorner = true;
            }
        } else if (i == 0) { //South and North corners
            if (j == radius && GameManager.get().getWorld().getTile(i, j) != null) {
                walls.put(new HexVector(i, j), 0+6); //N
                isCorner = true;
            } else if (j == -radius && GameManager.get().getWorld().getTile(i, j) != null){
                walls.put(new HexVector(i, j), 3+6); //S
                isCorner = true;
            }
        }
        return isCorner;
    }

    private void wallTexture(float i, float j, int radius) {
        //Wall Textures
        //Top Textures
        if (i * 0.5f + j == radius) {
            walls.put(new HexVector(i, j), 0); //Top right wall
            LOGGER.debug("Wall added at {} {} direction is 0", i, j);
        } else if (-(i * 0.5f) + j == radius) {
            walls.put(new HexVector(i, j), 5);  //Left Top
            LOGGER.debug("Wall added at {} {} direction is 5", i, j);

            //Bottom Textures
        } else if ((i * 0.5f) + -j == radius) {
            walls.put(new HexVector(i, j), 2); //Right bottom
            LOGGER.debug("Wall added at {} {} direction is 2", i, j);
        } else if (-(i * 0.5f) + -j == radius) {
            walls.put(new HexVector(i, j), 3); //left bottom
            LOGGER.debug("Wall added at {} {} direction is 3", i, j);

            //Side Textures
        } else if (i == radius && Math.abs(j) <= radius / 2f) {
            walls.put(new HexVector(i, j), 1); //Right Wall textures
            LOGGER.debug("Wall added at {} {} direction is 1", i, j);
        } else if (-i == radius && Math.abs(j) <= radius / 2f) {
            walls.put(new HexVector(i, j), 4); //left Wall texture
            LOGGER.debug("Wall added at {} {} direction is 4", i, j);
        }
    }


    /**
     * Method for putting room exits into world
     */
    private void setExits(){
        if (this.room != null){
            float radius = this.room.getRadius() + 1;
            this.placeExit[0] = new HexVector(1, radius - 0.5f);
            this.placeExit[1] = new HexVector(radius, radius/2 - 1.0f);
            this.placeExit[2] = new HexVector(radius - 1, -radius/2 - 0.5f);
            this.placeExit[3] = new HexVector(-1, -radius + 0.5f);
            this.placeExit[4] = new HexVector(-radius, -radius/2 + 1.0f);
            this.placeExit[5] = new HexVector(-radius + 1, radius/2 + 0.5f);

            for (int i = 0; i < 6; i++){
                if (!this.room.thereIsExit(i)){
                    this.placeExit[i] = null;
                }
            }
        }

    }
}
