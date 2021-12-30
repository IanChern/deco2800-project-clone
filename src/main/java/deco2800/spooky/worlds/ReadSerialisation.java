package deco2800.spooky.worlds;

import deco2800.spooky.worlds.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Reads room serialisation and verifies it. A collection of helper
 * functions to achieve this and 1 that is callable to get them.
 *
 * @author Frazer Carsley
 */
public class ReadSerialisation {
    /**
     * Reads room seralisation and verifies it.
     *
     * @author Frazer Carsley
     *
     * @param path path to folder where files are
     *
     * @return list of serialisations to send to client and null on failure
     */

    private static final String LAYERERROR = "Cannot read number of layers";

    private static final String TILEERROR = "Cannot read number of non-empty tiles";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadSerialisation.class);

    /**
     * Used for compliance as a utility class
     */
    private ReadSerialisation() {
        throw new IllegalStateException("Utility class");
    }

    public static List<List<String>> getRoomsToSend(String path)
            throws InterruptedException {
        // check operating system family
        String os = System.getProperty("os.name").toLowerCase();
        String cmd;
        if (os.contains("win")) {
            // windows
            cmd = "cmd /c dir " + path + " /b";
        } else if ((os.contains("nix")) || (os.contains("mac")) || (os.contains("nux"))) {
            // unix type system
            cmd = "ls -1 " + path;
        } else {
            // unsupported
            LOGGER.error("Unsupported Operating System");
            return new LinkedList<>();
        }

        // get the rooms
        List<String> roomPaths = getRooms(cmd);
        if (roomPaths.isEmpty()) {
            return new LinkedList<>();
        }

        // read and verify the rooms
        return verifyRooms(roomPaths, path);
    }

    /**
     * Determine how many and which room files to read.
     *
     * @author Frazer Carsley
     *
     * @param cmd command to run to list files on the file path
     *
     * @return rooms to read and verify as a linkedlist of strings or null on failure
     */
    private static LinkedList<String> getRooms(String cmd)
            throws InterruptedException {
        LinkedList<String> rooms = new LinkedList<>();

        try {
            // get rooms
            Runtime r = Runtime.getRuntime();
            Process ls = r.exec(cmd);
            ls.waitFor();

            BufferedReader b = new BufferedReader(new InputStreamReader(ls.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                if (line.contains(".room")) {
                    // room file
                    rooms.add(line);
                }
            }
        } catch (IOException | NumberFormatException ioe) {
            return new LinkedList<>();
        }

        return rooms;
    }

    /**
     * Reads the rooms and verifies them. Rooms that do not meet
     * the specification are ignored.
     *
     * @author Frazer Carsley
     *
     * @param toRead file paths to rooms to read
     * @param path the path to the files
     *
     * @return correctly serialised rooms
     */
    public static List<List<String>> verifyRooms(List<String> toRead,
            String path) {
        String line;
        List<List<String>> rooms = new LinkedList<>();
        BufferedReader br = null;
        for (String file : toRead) {
            FileReader fre = null;
            try (FileReader fr = new FileReader(path + "/" + file)) {
                fre = fr;
                try (BufferedReader b = new BufferedReader(fr)) {
                    br = b;
                    // open file for reading
                    // first line must be natural number (>0)
                    LinkedList<String> lines = new LinkedList<>();
                    int layers = 0;
                    line = b.readLine();
//                    lines = readBetweenTheLines(b, br, line);
                    try {
                        layers = Integer.parseInt(line);
                        if (layers <= 0) {
                            LOGGER.warn(LAYERERROR);
                            continue;
                        }
                    } catch (NumberFormatException npe) {
                        LOGGER.warn(LAYERERROR);
                        continue;
                    }
                    lines.add(line);

                    // second line is number of non-empty tiles
                    line = b.readLine();
                    int numLines = 0;
                    try {
                        numLines = Integer.parseInt(line);
                        if (numLines < 0) {
                            LOGGER.warn(TILEERROR);
                            continue;
                        }
                    } catch (NumberFormatException nfe) {
                        LOGGER.warn(TILEERROR);
                        continue;
                    }
                    lines.add(line);

                    // lines are now tiles
                    int i;
                    for (i = 0; i < numLines; i++) {
                        if (((line = b.readLine()) == null) || (!verifyTile(line, layers))) {
                            break;
                        }
                        lines.add(line);
                    }

                    String lineB = b.readLine();
                    if (i < numLines) {
                        // tile was not serialised correctly
                        LOGGER.warn("Cannot read tile");
                        continue;
                    } else if (lineB != null) {
                        // number of non-empty tiles given was false
                        LOGGER.warn("Number of non-empty tiles given was false");
                        continue;
                    }

                    // everything checks out
                    rooms.add(lines);
                } catch (IOException ioe) {
                    LOGGER.error("Room could not be read");
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException ioe) {
                            LOGGER.error("Could not close reader");
                        }
                    }
                }
            } catch (IOException e) {
                try {
                    if (fre != null) {
                        fre.close();
                    }
                } catch (IOException ex) {
                    LOGGER.error("Couldn't close stream");
                }
            }
        }
        return rooms;
    }

    /**
     * Verifies the line of a tile is correct.
     *
     * @author Frazer Carsley
     *
     * @param line the line to verify
     * @param layers number of layers in the room
     *
     * @return true if correct
     */
    private static boolean verifyTile(String line, int layers) {
        String[] parts = line.split(":", 3);
        int maxTile = Room.maxTileNum(layers);
        // tile number must be within layer maximum
        try {
            int tile = Integer.parseInt(parts[0]);
            if (tile > maxTile) {
                LOGGER.warn("Tile number greater than max tile num");
                return false;
            }
            else if (tile < 1) {
                LOGGER.warn("Tile position is less than 1");
                return false;
            }
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Tile number NAN");
            return false;
        }

        return true;
    }

    private static boolean verifyEachEntity(String[] parts) {
        // get number of entities possible to spawn in this tile
        int numEntities = 0;
        try {
            numEntities = Integer.parseInt(parts[1]);
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Number of entities not specified");
            return false;
        }
        // check each entity
        String[] entitiesParse = parts[2].split("[\\(\\)]");
        List<String>  entities = new ArrayList<>();
        for (int i = 0; i < entitiesParse.length; i++){
            if(!entitiesParse[i].equals("") && entitiesParse[i] != null){
                entities.add(entitiesParse[i]);
            }
        }
        if (!subVerifyEntity(entities, numEntities)) {
            return false;
        }

        return true;
    }

    private static boolean subVerifyEntity(List<String> entities, int numEntities) {
        boolean random = false;
        float p = 0f;
        int k = 0;
        for (k = 0; k < entities.size(); k++) {
            String[] pair = entities.get(k).split(",");
            // check probability
            try {
                float prob = Float.parseFloat(pair[0]);
                p += prob;
            } catch (NumberFormatException nfe) {
                if (pair[0].equals("x")) {
                    random = true;
                }
            }

            // ensure entity is valid
            if (!validEntity(pair[1])) {
                LOGGER.warn("Invalid entity");
                return false;
            }
        }

        if (((p != 1f) && (!random)) || (k != numEntities)) {
            LOGGER.warn("Probabilities do not add to 1 or number of entities given is false");
            return false;
        }
        return true;
    }

    /**
     * Determines whether an entity is valid or not.
     *
     * @author Frazer Carsley, et al
     *
     * @param entity the entity to verify
     *
     * @return true if valid
     */
    private static boolean validEntity(String entity) {
        List<String> validEntities = new ArrayList<>();
        // add to this whenever an entity that is added to a room is made
        validEntities.add("rock");
        validEntities.add("dagger");
        validEntities.add("pot");
        validEntities.add("axe");
        validEntities.add("coin");
        validEntities.add("rupee");
        validEntities.add("trap");
        validEntities.add("entity_name");

        for (String valEnt :validEntities){
            if (entity.equals(valEnt)){
                return true;
            }
        }

        return false;
    }
}
