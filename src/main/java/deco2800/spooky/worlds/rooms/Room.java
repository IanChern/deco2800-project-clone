package deco2800.spooky.worlds.rooms;

import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.entities.AgentEntity;
import deco2800.spooky.entities.StaticEntity;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.AbstractWorld;

import java.util.*;

public class Room implements Comparable<Room> {
    //Radius of the room (a.k.a number of layers)
    protected int radius = 0;

    //Number of entities in this room
    protected int entNo = 0;

    //array of exits, North is index 0 going clockwise
    protected Room[] exits = new Room[6];

    //Array List of EntityTile to store info of entities in this room
    protected List<EntityTile> EntityMap = new ArrayList<EntityTile>();

    protected boolean isCreated;

    //HashMap for storing location of tiles by tile ID
    protected static Map<Integer, HexVector> tileIDtoMap = new HashMap<Integer, HexVector>();

    protected static int maxRadius = 0;

    public Room() {
        /* Nothing to do on creation */
    }

    /**
     * Create a room from linked list of read file lines.
     *
     * @author: Hanh La
     *
     * @param readFile the list of lines in a room file.
     *
     */

    public Room (List<String> readFile){
        String stage = "read radius";
        this.isCreated = true;
        for (String info:readFile){

            //set stage to extract information from String linked list
            //read the number of layers
            if (stage.equals("read radius")){
                this.setRadius(Integer.parseInt(info));
                if (this.radius > maxRadius){
                    updateTileID2Map(this.radius);
                    maxRadius = this.radius;
                }
                stage = "read entNo";
            }
            //read the number of entities in the room
            else if (stage.equals("read entNo")){
                this.setRoomEntity(Integer.parseInt(info));
                stage = "read tiles";
            }
            // read and add entities information to EntityMap
            else if (stage.equals("read tiles")){
                addEntityTile(info);
            } else {
                //error in creating a room from file lines.
                this.isCreated = false;
                break;
            }
        }
    }

    public static Map<Integer, HexVector> getTileIDtoMap() {
        return(tileIDtoMap);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRoomEntity() {
        return entNo;
    }

    public void setRoomEntity(int entNo) {
        this.entNo = entNo;
    }

    public List<EntityTile> getEntityMap() {
        return EntityMap;
    }

    //Method to add the EntityTile from file line
    public void addEntityTile(String line){
        EntityTile newEntTile = new EntityTile(line);
        if (newEntTile.getCreated() && EntityMap.size() < entNo){
            this.EntityMap.add(newEntTile);
        }
    }

    /**
     * Method to get if there is an exit in given direction
     *
     * @param direction north being 0, going clockwise around the hex
     * @return true if there is an exit at the location, else returns false
     */
    public boolean thereIsExit(int direction) {
        return exits[direction] != null;

    }

    /**
     * Method to get destination room at an exit
     *
     * @param direction north being 0, going clockwise around the hex
     * @return the Room the destination is pointing to
     */
    public Room getExit(int direction) {
        return exits[direction];
    }

    /**
     * Method to set if there is an exit at position direction
     *
     * @param direction north being 0, going clockwise around the hex
     */
    public void setExit(int direction, Room destination) {
        this.exits[direction] = destination;
    }

    /**
     * number of exits in the room
     *
     * @return the number of exits a room contains
     */
    public int numberOfExits() {
        int countingExits = 0;
        for (int i = 0; i < 6; i++) {
            if (thereIsExit(i)) {
                countingExits++;
            }
        }
        return countingExits;
    }

    /**
     * Maximum tile number of a room given the number of layers.
     *
     * @author Frazer Carsley
     *
     * @param layers the number of layers in the room.
     *
     * @return the maximum tile number (add 1 to get number of tiles)
     */
    public static int maxTileNum(int layers) {
        // \sum_{i=1}^{layers-1}6i
        int sum = 0;
        for (int i = 1; i < layers; i++) {
            sum += 6 * i;
        }
        return sum;
    }

    /**
     * Calculate tile ID based on their coordinates and store them in HashMap.
     *
     * @author Hanh La
     *
     * @param newRad new maximum number of layers.
     *
     *
     */
    protected void updateTileID2Map(int newRad){
        int maxTile = maxTileNum(this.radius);
        int IDcount = tileIDtoMap.size();

        if (IDcount == 0){
            tileIDtoMap.put(0, new HexVector(0,0));
            IDcount++;
        }
        for (int r = maxRadius + 1; r <= newRad; r++){
            /*
            How it works
             6/\1
            5|  |2
             4\/3

             TileID will increase clockwise, from the inner to the outer most layers
             (please refer to Map Generation/Concept wiki page).
             */
            float i, j;
            //first segment
            for (i = 0; i < r; i++){
                j = r - i * 0.5f;
                tileIDtoMap.put(IDcount, new HexVector(i, j));
                IDcount++;
            }

            //second segment
            for (j = r * 0.5f; j > -r * 0.5f; j--){
                tileIDtoMap.put(IDcount, new HexVector(r, j));
                IDcount++;
            }

            //third segment
            for (i = r; i > 0; i--){
                j = -r + i * 0.5f;
                tileIDtoMap.put(IDcount, new HexVector(i, j));
                IDcount++;
            }

            //forth segment
            for (i = 0; i > -r; i--){
                j = - r - i * 0.5f;
                tileIDtoMap.put(IDcount, new HexVector(i, j));
                IDcount++;
            }

            //fith segment
            for (j = -r * 0.5f; j < r * 0.5f; j++){
                tileIDtoMap.put(IDcount, new HexVector(-r, j));
                IDcount++;
            }

            //sixth segment
            for (i = -r; i < 0; i++){
                j = r + i * 0.5f;
                tileIDtoMap.put(IDcount, new HexVector(i, j));
                IDcount++;
            }
        }
    }

    /**
     * A room is "bigger" if its radius is smaller.
     *
     * @param room the room to compare this room to
     *
     * @return a negative integer, zero, or a positive integer as this rooms radius 
     * is less than, equal to, or greater than the compared room.
     */
    public int compareTo(Room room) {
        return this.radius - room.radius;
    }
}
