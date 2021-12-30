package deco2800.spooky.worlds;

import deco2800.spooky.worlds.rooms.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testing Room2World
 * @author Hanh La
 */

public class Room2WorldTest {
    private Room sampleRoom;
    private Room2World room2World;
    protected LinkedList<LinkedList<String>> fileList;

    public LinkedList<LinkedList<String>> readFile(){
        String path = "resources/testrooms";
        /* Removing this as files may be listed in different orders in different systems.
         * Should automate set up of tests as little as possible in order to ensure
         * everything is ok.
         */
        /*
        ReadSerialisation readSerialisation = new ReadSerialisation();
        LinkedList<LinkedList<String>> readSerialization = ReadSerialisation.getRoomsToSend(path);
        */

        LinkedList<LinkedList<String>> readSerialization = new LinkedList<LinkedList<String>>();
        readSerialization.add(new LinkedList<String>());
        readSerialization.add(new LinkedList<String>());

        // first room - resources/testrooms/example.room
        readSerialization.get(0).add("3");
        readSerialization.get(0).add("6");
        readSerialization.get(0).add("3:1:(1,rock)");
        readSerialization.get(0).add("7:1:(1,rock)");
        readSerialization.get(0).add("9:3:(0.1,medicine)(0.2,dagger)(0.7,rock)");
        readSerialization.get(0).add("11:1:(1,rock)");
        readSerialization.get(0).add("12:3:(0.5,medicine)(0.3,rock)(0.2,axe)");
        readSerialization.get(0).add("18:1:(1,rock)");

        // second room - resources/testrooms/example3.room
        readSerialization.get(1).add("3");
        readSerialization.get(1).add("3");
        readSerialization.get(1).add("3:1:(1,entity_name)");
        readSerialization.get(1).add("7:2:(0.5,entity_name)(0.5,entity_name)");
        readSerialization.get(1).add("8:1:(1,entity_name)");

        return readSerialization;
    }

    /**
     * Test creating the room from file
     */
    @Test
    public void loadRoomFromFile(){
        if (fileList == null){
            this.fileList = readFile();
        }

        List<Room> roomList = new ArrayList<Room>();
        for (LinkedList<String> readFile:this.fileList){
            roomList.add(new Room(readFile));
        }

        assertEquals(6, roomList.get(0).getRoomEntity(), 0);
        assertEquals(3, roomList.get(1).getEntityMap().size(), 0);
        assertEquals("rock", roomList.get(0).getEntityMap().get(0).getEntityTexture());
    }

    /*@Test
    public void putRoom2Screen(){
        if (fileList == null){
            this.fileList = readFile();
        }

        List<Room> roomList = new ArrayList<Room>();
        for (LinkedList<String> readFile:this.fileList){
            roomList.add(new Room(readFile));
        }
    }*/
}
