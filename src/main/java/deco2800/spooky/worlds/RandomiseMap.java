package deco2800.spooky.worlds;

import deco2800.spooky.worlds.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;


public class RandomiseMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadSerialisation.class);

    /**
     * Count the number of files in a particular path. This should equate to the number of room
     * files that can possibly be read from. The filepath must only contain room files
     *
     * @author Frazer Carsley
     *
     * @return the number of files counted in the file
     * @throws IOException reading io for counting numbers of files
     * @throws NumberFormatException parsing string to int
     * @throws InterruptedException thread is interrupted while waiting
     */
    public static int numberOfRooms() throws IOException, InterruptedException {
        String cmd;

        // check operating system family
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            // windows
            cmd = "cmd /c dir src\\main\\java\\deco2800\\spooky\\worlds\\rooms /b";

        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            cmd = "ls -1 src/main/java/deco2800/spooky/worlds/rooms/";
        } else {
            // unsupported
            cmd = "";
        }

        return countRooms(cmd);
    }

    /**
     * Counts the number of rooms in the given file path.
     *
     * @author Frazer Carsley
     *
     * @param cmd the command to run to list the files on the file path
     *
     * @return number of room files.
     */
    private static int countRooms(String cmd) throws IOException, InterruptedException {
        int numberOfRooms = 0;


            Runtime r = Runtime.getRuntime();
            Process ls = r.exec(cmd);
        try {
            ls.waitFor();

            // count rooms
            BufferedReader b = new BufferedReader(new InputStreamReader(ls.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                if (line.contains(".room")) {
                    // room file
                    numberOfRooms++;
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("IO");
            numberOfRooms = -1;
        } catch (NumberFormatException nfe) {
            LOGGER.error("Format");
            numberOfRooms = -1;
        }

        return numberOfRooms;
    }


    /**
     * Create and make an array of Room objects, this method will not check if the rooms will all
     * fit in a map
     *
     * @author Jacob Watson
     *
     * @param numberOfRooms how many rooms should be added to the array, could depend on map and
     *                      room sizes
     * @return The newly created roomArray which has been populated by room
     */
    private static Room[] createRoomArray(int numberOfRooms) {
        Room[] roomArray = new Room[numberOfRooms];
        //  array of room, make a new room in each spot
        for (int i = 0; i < numberOfRooms; i++) {
            roomArray[i] = new Room();
        }

        return roomArray;
    }


    /**
     * Method to help out randomisingExits to calculate probability if a new exit should be made
     *
     * @author Jacob Watson
     *
     * @param currentNumberOfExits to be worked into the probability calculation
     * @return did the probability allow for a new exit
     */
    private static boolean newExit(int currentNumberOfExits){
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        double comparison = (Math.exp(-((double)currentNumberOfExits)/2))*1000;
        return randomNumber <= comparison;
    }


    /**
     * Assigning all of the rooms within roomArray to have exits
     * These exits are assigned with probability and random aspects
     *
     * @author Designed by Frazer Carsley, implemented by Jacob Watson
     *
     * @param roomArray the array of all rooms
     */
    public static void randomisingExits(Room[] roomArray) {
        //foreach Room in roomArray
        for (Room room : roomArray) {
            //if a room already has exits we dont want to try and put one there so we loop over
            // the number of possible exit locations
            for (int i = room.numberOfExits(); i < 6 ; i++) {

                //if the probability faction allows for a new exit
                if(newExit(i)) {

                    //try and place an exit at a random location
                    ArrayList<Integer> availableExits = new ArrayList<>();
                    for (int j = 0; j < 6; j++) {
                        if (!room.thereIsExit(j)) {
                            availableExits.add(j);
                        }
                    }
                    placeRandomExit(roomArray, room, availableExits);
                } else {
                    // no more exits for this room
                    break;
                }
            }
        }
    }

    private static void placeRandomExit(Room[] roomArray, Room room, ArrayList<Integer> availableExits) {
        // pick the random exit number to try and place
        Random random = new Random();
        boolean placedExit = false;
        while ((!availableExits.isEmpty()) && (!placedExit)) {
            int rand = random.nextInt(availableExits.size());
            int newExitNumber = availableExits.remove(rand).intValue();
            //complementExitNumber is the opposite exit to newExitNumber
            int complementExitNumber = (newExitNumber + 3) % 6;
            //Look for a room without an exit
            for(Room complementRoom: roomArray) {
                //could remove this sometimes for fun loops in the map
                //dont look for a complement exit in room for room
                if(complementRoom.equals(room)) {
                    continue;
                }
                //If a room has a valid complement exit
                else if (!complementRoom.thereIsExit(complementExitNumber)) {
                    //add both exits to the respective rooms
                    room.setExit(newExitNumber, complementRoom);
                    complementRoom.setExit(complementExitNumber, room);
                    //stop looking for a complement room to fit room with newExit
                    placedExit = true;
                    break;
                }
            }
        }
    }

    /**
     * Method to sort room
     * @author Victor Ngo
     * @param roomArray the room array needed to sort
     * @return sorted room array
     */
    public static Room[] sortRoom(Room[] roomArray) {
        //sorted room array
        Room[] sortedRoom = new Room[roomArray.length];
        int index = 0;

        //rooms to add to the array
        Queue<Room> roomToVisit = new ArrayDeque<>();

        roomToVisit.add(roomArray[0]);

        while (!roomToVisit.isEmpty()) {

            Room room = roomToVisit.element();

            //check if we have already visited the room before
            boolean isVisited = false;
            for (Room addedRoom : sortedRoom) {
                if (room == addedRoom) {
                    isVisited = true;
                    break;
                }
            }

            //add the room if it is not added and remove it from the queue
            if (!isVisited) {
                sortedRoom[index] = room;
            }
            roomToVisit.remove();

            exitNumbering(sortedRoom, room, roomToVisit);
            index++;
        }

        return sortedRoom;
    }

    private static void exitNumbering(Room[] sortedRoom, Room room, Queue roomToVisit) {
        //see Room.java for exit numbering
        for (int value = 0; value < 6; value++) {
            Room roomInDirection;

            if (room.thereIsExit(value)) {
                roomInDirection = room.getExit(value);

                //check if we have already added the room for future processing
                boolean isAdded = false;
                for (Room addedRoom : sortedRoom) {
                    if (roomInDirection == addedRoom) {
                        isAdded = true;
                        break;
                    }
                }

                //add the room to process if it is not added
                if (!isAdded) {
                    roomToVisit.add(roomInDirection);
                }
            }

        }

    }
}
