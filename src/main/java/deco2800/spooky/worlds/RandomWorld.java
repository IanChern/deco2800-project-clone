package deco2800.spooky.worlds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import deco2800.spooky.entities.*;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Items.Coin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.entities.Creep.CreepFactory;
import deco2800.spooky.entities.Creep.Mummy;
import deco2800.spooky.entities.Creep.Guard;
import deco2800.spooky.entities.Items.ItemFactory;
import deco2800.spooky.entities.Items.Rupee;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.tasks.ItemGeneration;
import deco2800.spooky.util.Cube;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Point;
import deco2800.spooky.worlds.rooms.EntityTile;
import deco2800.spooky.worlds.rooms.Room;

/**
 * The main game world for this studio. The world is generated
 * by rooms which are connected in a random way.
 */
public class RandomWorld extends AbstractWorld {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Room2World.class.getPackage().getName());
    private static List<Character> playerList = new ArrayList<>();
    private RoomCoord[] coordRooms;
    private ItemGeneration itemGeneration;
    private static CreepFactory creepFactory;
    private static ItemFactory itemFactory;
    private long moveCoolDownTime = 1000;
    private long movedTime;

    private static final float BULLET_BOX_LENGTH = 0.1f;

    /**
     * 3-tuple which contains a room and its coords.
     *
     * @author Frazer Carsley
     */
    private class RoomCoord {
        private int x;
        private int y;
        private Room room;
        RoomCoord(int x, int y, Room room) {
            this.x = x;
            this.y = y;
            this.room = room;
        }

    }

    /*
     * radius for tiles 1 - 7 2 - 19 3 - 37 4 - 61 5 - 91 10 - 331 25 - 1951 50 -
     * 7,651 100 - 30,301 150 - 67,951 200 - 120601
     *
     * N = 1 + 6 * summation[0 -> N]
     */
    private boolean notGenerated = true;

    // this is a default value
    private int mapRadius = 25;

    // this should be replaced with whatever the FoV value is to ensure players
    // cannot see other rooms than the one theyre in
    private final static int lightRadius = 5;

    // the rooms that make up this world
    private Room[] rooms;

    public RandomWorld() {
        super();
    }

    /**
     * Returns the radius of the world.
     *
     * @return the radius of the world
     */
    public int getRadius() {
        return this.mapRadius;
    }

    /**
     * Returns the total Room Coordinates of the RandomWorld
     * @return the roomCoords of the world
     */
    public RoomCoord[] getRoomCoords() { return this.coordRooms; }

    /**
     * Getting the current Room the player is at
     * @param player who is being controlled
     * @return the room of the player is at
     */
    public Room getCurrentRoom (Character player) {
        for(int i = 0; i < this.coordRooms.length; i++) {
            if (player.getCol() - this.coordRooms[i].x < this.coordRooms[i].room.getRadius()
                    && player.getRow() - this.coordRooms[i].y < this.coordRooms[i].room.getRadius()
                    && this.coordRooms[i].room.compareTo(this.rooms[i]) == 0) {
                return this.rooms[i];
            }
        }
        return null;
    }

    /**
     * Generates the world with rooms in it.
     */
    @Override
    protected void generateWorld() {
        itemGeneration = new ItemGeneration();
        entities.add(itemGeneration);
        playerList = new ArrayList<>();
        creepFactory = new CreepFactory();
        itemFactory = new ItemFactory();
        
        // calculate radius of world
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "src\\main\\java\\deco2800\\spooky\\worlds\\rooms";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "src/main/java/deco2800/spooky/worlds/rooms";
        } else {
            // unsupported
            path = "";
        }

        try {
            this.rooms = getRooms(path);
        } catch (InterruptedException ie) {
            LOGGER.warn("Room loaded is null, Try again later!");
            Thread.currentThread().interrupt();
        }
        this.mapRadius = calculateRadius(rooms);

        Random random = new Random();
        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= this.mapRadius) {

                    int col = q;

                    float oddCol = (col % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(3);

                    String type = "grass_" + elevation;

                    tiles.add(new Tile(type, q, r + oddCol));
                }
            }
        }
        Chris chris = new Chris(0.0f, 0.0f, true);

        Character mummy = (Mummy) creepFactory.createCreep("Mummy", 3f, 3f);
        addEntity(mummy);

        Character guard = (Guard) creepFactory.createCreep("Guard", 1f, 1f);
        guard.setSpeed(0.01f);
        addEntity(guard);

        // Create the entities in the game
        addEntity(chris);
        chris.setMainCharacter(true);
        playerList.add(chris);
        remainingPlayerList.add(chris);
        initializeRemainingCharacter(playerList);

        //Axe and dagger
        MeleeWeapon axe = new MeleeWeapon(-2, 0, MeleeType.AXE);
        MeleeWeapon dagger = new MeleeWeapon(-4,0, MeleeType.DAGGER);
        entities.add(axe);
        entities.add(dagger);

        itemGeneration = new ItemGeneration();
        entities.add(itemGeneration);
        itemGeneration.start();
    }

    public static CreepFactory getCreepFactory() {
        return (creepFactory);
    }
    /**
     * Determines the size of the world needed. Rooms are placed radially, so
     * only certain rooms contribute to radius of world. See documentation
     * on the wiki for details.
     *
     * @author Frazer Carsley
     *
     * @param rooms the rooms to fit in the world
     *
     * @return the radius needed
     */
    public int calculateRadius(Room[] rooms) {
        int total = 0;
        int n = 0;
        int i = 0;
        while (i < rooms.length) {
            // layers + 1 for total radius of room, measured in tiles
            LOGGER.info("f("+n+")="+i);
            int radius = (rooms[0].getRadius() + 1) + (lightRadius * 2);
            total += radius;

            // calculate next room. sequence is
            // f(0) = 0
            // f(1) = 1
            // f(n) = f(n-1)+4(n-1)-1 for n>=2
            // closed form given by f(n) = 1+4(n choose 2)
            n++;
            if (n == 1) {
                i = 1;
            } else {
                i += 4 * (n - 1);
            }
        }

        LOGGER.info("Radius of world calculated as {}", total);
        return total;
    }

    /**
     * Determines how to place rooms in the world with space between. The largest
     * room is placed first and then other rooms around it in decreasing order of size.
     * This means that no room should be visible from another.
     * NOTE: this function doesn't render rooms on screen; it only determines where
     * they should be
     *
     * @author Frazer Carsley
     *
     * @return array of tuples of room with coords
     */
    private RoomCoord[] placeRooms() {
        int toAdd = 0;  // room being added

        RoomCoord[] roomCoords = new RoomCoord[this.rooms.length];

        // put first room down first
        roomCoords[0] = new RoomCoord(0, 0, rooms[0]);
        LOGGER.info("ADDED: first room");
        toAdd++;
        for (int i = 0; i < this.rooms.length; i++) {
            // add rooms around this one
            // function pointers would be really helpful here
            if (toAdd < this.rooms.length) {
                toAdd = placeAbove(toAdd, roomCoords, i);
            }
            if (toAdd < this.rooms.length) {
                toAdd = placeRight(toAdd, roomCoords, i);
            }
            if (toAdd < this.rooms.length) {
                toAdd = placeDown(toAdd, roomCoords, i);
            }
            if (toAdd < this.rooms.length) {
                toAdd = placeLeft(toAdd, roomCoords, i);
            }
        }
        return roomCoords;
    }

    private void updateCreep() {
        for (Character creep : creepFactory.getCreeps()) {
            if (creep.getHealth() == 0 && !creep.isKilled()) {
                Coin coin = (Coin) itemFactory.createItem("Coin", creep.getCol(), creep.getRow());
                removeEntity(creep);
                addEntity(coin);
                creep.setKilled(true);
            }
        }
    }

    public static List<Character> getPlayerList() {
        return playerList;
    }
    /**
     * Checks if a room can be placed above the given room.
     *
     * @author Frazer Carsley
     *
     * @param toAdd the room number of the room being placed
     * @param roomCoords array of placed rooms with coordinates
     * @param roomNum the room number of the room already placed
     *
     * @return the next room number that can be added
     */
    private int placeAbove(int toAdd, RoomCoord[] roomCoords, int roomNum) {
        Room toAddRoom = this.rooms[toAdd];
        RoomCoord placedRoom = roomCoords[roomNum];
        int toAddY = placedRoom.y + rooms[0].getRadius() +
                placedRoom.room.getRadius() + (lightRadius * 2);
        int toAddX = placedRoom.x;
        boolean canAdd = true;

        for (int i = 0; i < toAdd; i++) {
            // a room is between these 2 if there is some room whose x coords match and
            // whose y coords are between the center of the first and the farthest
            // edge of the second
            if ((placedRoom.y < roomCoords[i].y)
                    && (roomCoords[i].y < toAddY + toAddRoom.getRadius())
                    && (roomCoords[i].x == toAddX)) {
                // there is a room between these 2
                canAdd = false;
                LOGGER.warn("Room {} is already above room {}", i, roomNum);
                break;
            }
        }

        if (canAdd) {
            // add the room
            LOGGER.info("ADDED: room {} above {} with coords ({},{})",
                    toAdd, roomNum, toAddX, toAddY);
            roomCoords[toAdd] = new RoomCoord(toAddX, toAddY, toAddRoom);
            toAdd++;
        }

        return toAdd;
    }

    /**
     * Checks if a room can be placed to the right of the given room.
     *
     * @author Frazer Carsley
     *
     * @param toAdd the room number of the room being placed
     * @param roomCoords array of placed rooms with coordinates
     * @param roomNum the room number of the room already placed
     *
     * @return the next room number that can be added
     */
    private int placeRight(int toAdd, RoomCoord[] roomCoords, int roomNum) {
        Room toAddRoom = this.rooms[toAdd];
        RoomCoord placedRoom = roomCoords[roomNum];
        int toAddX = placedRoom.x + rooms[0].getRadius() +
                placedRoom.room.getRadius() + (lightRadius * 2);
        int toAddY = placedRoom.y;
        boolean canAdd = true;

        for (int i = 0; i < toAdd; i++) {
            // a room is between these 2 if there is some room whose y coords match and
            // whose x coords are between the center of the first and the farthest
            // edge of the second
            if ((placedRoom.x < roomCoords[i].x)
                    && (roomCoords[i].x < toAddX + toAddRoom.getRadius())
                    && (roomCoords[i].y == toAddY)) {
                // there is a room between these 2
                canAdd = false;
                LOGGER.warn("Room {} is already to the right of room {}", i, roomNum);
                break;
            }
        }

        if (canAdd) {
            // add the room
            LOGGER.info("ADDED: room {} right of {} with coords ({},{})",
                    toAdd, roomNum, toAddX, toAddY);
            roomCoords[toAdd] = new RoomCoord(toAddX, toAddY, toAddRoom);
            toAdd++;
        }

        return toAdd;
    }

    /**
     * Checks if a room can be placed below the given room.
     *
     * @author Frazer Carsley
     *
     * @param toAdd the room number of the room being placed
     * @param roomCoords array of placed rooms with coordinates
     * @param roomNum the room number of the room already placed
     *
     * @return the next room number that can be added
     */
    private int placeDown(int toAdd, RoomCoord[] roomCoords, int roomNum) {
        Room toAddRoom = this.rooms[toAdd];
        RoomCoord placedRoom = roomCoords[roomNum];
        int toAddX = placedRoom.x - rooms[0].getRadius() -
                placedRoom.room.getRadius() - (lightRadius * 2);
        int toAddY = placedRoom.y;
        boolean canAdd = true;

        for (int i = 0; i < toAdd; i++) {
            // a room is between these 2 if there is some room whose x coords match and
            // whose y coords are between the center of the first and the farthest
            // edge of the second
            if ((placedRoom.y > roomCoords[i].y)
                    && (roomCoords[i].y > toAddY - toAddRoom.getRadius())
                    && (roomCoords[i].x == toAddX)) {
                // there is a room between these 2
                canAdd = false;
                LOGGER.warn("Room {} is already below room {}", i, roomNum);
                break;
            }
        }

        if (canAdd) {
            // add the room
            LOGGER.info("ADDED: room {} below {} with coords ({},{})",
                    toAdd, roomNum, toAddX, toAddY);
            roomCoords[toAdd] = new RoomCoord(toAddX, toAddY, toAddRoom);
            toAdd++;
        }

        return toAdd;
    }

    /**
     * Checks if a room can be placed to the left of the given room.
     *
     * @author Frazer Carsley
     *
     * @param toAdd the room number of the room being placed
     * @param roomCoords array of placed rooms with coordinates
     * @param roomNum the room number of the room already placed
     *
     * @return the next room number that can be added
     */
    private int placeLeft(int toAdd, RoomCoord[] roomCoords, int roomNum) {
        Room toAddRoom = this.rooms[toAdd];
        RoomCoord placedRoom = roomCoords[roomNum];
        int toAddY = placedRoom.y - rooms[0].getRadius() -
                placedRoom.room.getRadius() - (lightRadius * 2);
        int toAddX = placedRoom.x;
        boolean canAdd = true;

        for (int i = 0; i < toAdd; i++) {
            // a room is between these 2 if there is some room whose x coords match and
            // whose y coords are between the center of the first and the farthest
            // edge of the second
            if ((placedRoom.x > roomCoords[i].x)
                    && (roomCoords[i].x > toAddX - toAddRoom.getRadius())
                    && (roomCoords[i].y == toAddY)) {
                // there is a room between these 2
                canAdd = false;
                LOGGER.warn("Room {} is already left of room {}", i, roomNum);
                break;
            }
        }

        if (canAdd) {
            // add the room
            LOGGER.info("ADDED: room {} left of {} with coords ({},{})",
                    toAdd, roomNum, toAddX, toAddY);
            roomCoords[toAdd] = new RoomCoord(toAddX, toAddY, toAddRoom);
            toAdd++;
        }

        return toAdd;
    }

    /**
     * Reads a list of room files and joins them via random exits.
     *
     * @param path the path to the files
     *
     * @return array of rooms to add to the world
     */
    public static Room[] getRooms(String path) throws InterruptedException {
        // get room in serialized form
        List<List<String>> roomsFromFile = ReadSerialisation.getRoomsToSend(path);

        // connect rooms with random exits
        int i = 0;
        Room[] roomArray = new Room[roomsFromFile.size()];
        for (List<String> readFile:roomsFromFile){
            roomArray[i++] = new Room(readFile);
        }
        RandomiseMap.randomisingExits(roomArray);

        // sort rooms smallest to largest
        Arrays.sort(roomArray);
        for (i = 0; i < roomArray.length / 2; i++) {
            Room temp = roomArray[i];
            roomArray[i] = roomArray[roomArray.length - 1 - i];
            roomArray[roomArray.length - 1 - i] = temp;
        }

        return roomArray;
    }


    /**
     * Creating a list of wall entities for all of the rooms
     * This method creates a factory and list of walls, it then loops through every room from
     * roomCoords and inside loops there every wall grabing its position and direction to
     * complete make the entity
     *
     * @author Jacob Watson @Jclass100
     * @param roomCoords an array of Coords of rooms as generated by placeRooms() in this class
     * @return an array of walls to be placed into the map, the return does care about rooms just
     */
    private LinkedList<Door> createDoors(RoomCoord[] roomCoords) {
        LinkedList<Door> doors = new LinkedList<>();
        String[] texture = new String[6];
        texture[0] = "door1";
        texture[1] = "door2";
        texture[2] = "door3";
        texture[3] = "door4";
        texture[4] = "door5";
        texture[5] = "door6";


        //for each room within the set of rooms selected in
        for (RoomCoord roomCoord: roomCoords) {
            //get the information form the serialised room and make it a proper data structure
            Room2World room2World = new Room2World(roomCoord.room);


            //for each wall within the particular room
            for (HexVector doorPos : room2World.getDoors().keySet()) {

                float col = doorPos.getCol() + roomCoord.x;
                float row = doorPos.getRow() + roomCoord.y;

                Tile tile = GameManager.get().getWorld().getTile(col, row);
                Door door = new Door(tile,
                        texture[room2World.getDoors().get(doorPos)], true);

                //add this wall to the list of walls
                doors.add(door);
            }
        }
        return doors;

    }

    /**
     * Creating a list of wall entities for all of the rooms
     * This method creates a factory and list of walls, it then loops through every room from
     * roomCoords and inside loops there every wall grabing its position and direction to
     * complete make the entity
     *
     * @author Jacob Watson @Jclass100
     * @param roomCoords an array of Coords of rooms as generated by placeRooms() in this class
     * @return an array of walls to be placed into the map, the return does care about rooms just
     */
    private LinkedList<StaticWall> createWalls(RoomCoord[] roomCoords) {
        LinkedList<StaticWall> walls = new LinkedList<>();
        String[] texture = new String[12];
        texture[0] = "wall1";
        texture[1] = "wall2";
        texture[2] = "wall3";
        texture[3] = "wall4";
        texture[4] = "wall5";
        texture[5] = "wall6";
        texture[6] = "wall7";
        texture[7] = "wall8";
        texture[8] = "wall9";
        texture[9] = "wall10";
        texture[10] = "wall11";
        texture[11] = "wall12";

        //for each room within the set of rooms selected in
        for (RoomCoord roomCoord: roomCoords) {
            //get the information form the serialised room and make it a proper data structure
            Room2World room2World = new Room2World(roomCoord.room);


            //for each wall within the particular room
            for (HexVector wallPos : room2World.getWalls().keySet()) {

                float col = wallPos.getCol() + roomCoord.x;
                float row = wallPos.getRow() + roomCoord.y;

                Tile tile = GameManager.get().getWorld().getTile(col, row);
                StaticWall wall = new StaticWall(tile,
                        texture[room2World.getWalls().get(wallPos)], true);

                //add this wall to the list of walls
                walls.add(wall);
            }
        }
        return walls;

    }


    /**
     * Creates the entities specified in the .room files
     *
     * @param roomCoords A list of the roooms added
     * @return A list of the entities to be added to the world
     */
    private ArrayList<AbstractEntity> createOtherEntites(RoomCoord[] roomCoords) {
        ArrayList<AbstractEntity> entitiesOther = new ArrayList<>();
        for (int i = 0; i < roomCoords.length; i++) {
            Room2World room2World = new Room2World(roomCoords[i].room);
            for (int j = 0; j < roomCoords[i].room.getEntityMap().size(); j++) {
                EntityTile tempEntityTile = roomCoords[i].room.getEntityMap().get(j);
                LOGGER.debug("EntityTile = {}", tempEntityTile.getEntityTexture());
                if(tempEntityTile.getCreated()) {
                    HexVector coordinates = Room.getTileIDtoMap().get(tempEntityTile.getTileID());
                    Tile newEntity = GameManager.get().getWorld().getTile(coordinates.getCol() + roomCoords[i].x, coordinates.getRow() + roomCoords[i].y);
                    AbstractEntity addedEntity;

                    switch (tempEntityTile.getEntityTexture()) {
                        case "pit":
                            addedEntity = this.trapManager.setTrap(newEntity, TrapType.PITFALL);
                            room2World.addTraps((RoomTrap) addedEntity);
                            LOGGER.debug("TRAP HAS BEEN PLANTED: {}, {}", addedEntity.getCol(), addedEntity.getRow());
                            LOGGER.info("Trap section Room Radius = " + roomCoords[i].room.getRadius());
                            break;
                        case "sand":
                            addedEntity = this.trapManager.setTrap(newEntity, TrapType.QUICKSAND);
                            room2World.addTraps((RoomTrap) addedEntity);
                            LOGGER.debug("TRAP HAS BEEN PLANTED: {}, {}", addedEntity.getCol(), addedEntity.getRow());
                            LOGGER.info("Trap section Room Radius = " + roomCoords[i].room.getRadius());
                            break;

                        case "rupee":
                            addedEntity = new Rupee(newEntity.getCol(), newEntity.getRow());
                            break;
                        case "pot":
                            // generate random pots with different type
                            Random r = new Random();
                            int index = r.nextInt(3);
                            addedEntity = new Pot(newEntity, index);
                            break;
                        default:
                            //adding a default as a rock
                            addedEntity = new Rock(newEntity, true);
                            break;
                    }
                    entitiesOther.add(addedEntity);
                }
            }
        }
        return entitiesOther;
    }

    /**
     * Determines a random room to place a player in the world.
     *
     * @author Frazer Carsley
     * 
     * @return coords to place player at 
     */
    public Point getCoordsForPlayer() {
        int numRooms = this.rooms.length;
        Random rand = new Random();
        RoomCoord roomCoord = this.coordRooms[rand.nextInt(numRooms)];

        return new Point(roomCoord.x, roomCoord.y);
    }
    
    private int randomDamage(int damage) {
        int damageTaken;
        switch (damage) {
            case 0: { // damage 1
                damageTaken = 1;
                break;
            }
            case 1: { // damage 2
                damageTaken = 2;
                break;
            }
            case 2: {// damage 3
                damageTaken = 3;
                break;
            }
            case 3: {// damage 5
                damageTaken = 5;
                break;
            }
            case 4: {// damage 10
                damageTaken = 10;
                break;
            }
            default: {
                damageTaken = 0;
            }
        }
        return damageTaken;
    }

    /**
     * Makes a new character for the game
     *
     * @author Frazer Carsley
     *
     * @param character the name of the class of the character
     */
    public Character makeCharacter(String character) {
        Character newChar;
        Point coords = getCoordsForPlayer();
        if (character.equals("chris")) {
            newChar = new Chris(coords.getX(), coords.getY(), true);
        } else if (character.equals("damien")) {
            newChar = new Damien(coords.getX(), coords.getY(), true);
        } else if (character.equals("jane")) {
            newChar = new Jane(coords.getX(), coords.getY(), true);
        } else if (character.equals("katie")) {
            newChar = new Katie(coords.getX(), coords.getY(), true);
        } else if (character.equals("larry")) {
            newChar = new Larry(coords.getX(), coords.getY(), true);
        } else {
            newChar = new Titus(coords.getX(), coords.getY(), true);
        }
        LOGGER.info("Character " + character + " created at X:" + coords.getX() + " Y:" + coords.getY());
        addEntity(newChar);
        return newChar;
    }

	@Override
	public void onTick(long i) {
        super.onTick(i);
        long currentTime = System.currentTimeMillis();
        Random r = new Random();
        int damage;
        int damageTaken;
		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}

        //Start making a map of room, add to entities walls doors and items as read from a file
        if (notGenerated) {
            RoomCoord[] roomCoords = placeRooms();
            coordRooms = roomCoords;

            entities.addAll(createWalls(roomCoords));
            entities.addAll(createDoors(roomCoords));

            entities.addAll(createOtherEntites(roomCoords));

            notGenerated = false;
            LOGGER.debug("Finished generating onTick in RandomWorld");
		}

        for (Character player : playerList) {
            for (Character mummy : creepFactory.getMummies()) {
                if (mummy instanceof Peon) {
                    int overLapResult = player.overlaps(mummy,
                            BULLET_BOX_LENGTH * 72, 1.5f, 0.2f);
                    //player picks up an ammo packet and the ammo refills
                    if (overLapResult >= 1 && (mummy instanceof Mummy && (currentTime - movedTime) >= moveCoolDownTime)) {
                        damage = r.nextInt(4);
                        damageTaken = randomDamage(damage);
                        player.setHealth(player.getHealth() - damageTaken);

                        LOGGER.info(damageTaken + "damage taken");
                        movedTime = currentTime;
                    }
                }
            }

            for (Character guard : creepFactory.getGuards()) {
                if (guard instanceof Peon) {
                    int overLapResult = player.overlaps(guard, BULLET_BOX_LENGTH * 72, 1.5f, 0.2f);
                    if (overLapResult >= 1 && ((guard instanceof Guard) && (currentTime - movedTime) >= moveCoolDownTime)) {
                        // player picks up an ammo packet and the ammo refills
                        damageTaken = 5;
                        player.setHealth(player.getHealth() - damageTaken);
                        LOGGER.info(damageTaken + "damage taken");
                        movedTime = currentTime;
                    }
                }
            }
            updateCreep();


        }

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

    }

}
