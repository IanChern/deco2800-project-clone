package deco2800.spooky.worlds;

import deco2800.spooky.worlds.rooms.Room;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;


/**
 * Tests the RandomiseMap java class.
 * @author Frazer Carsley (@fcarsley)
 */
public class RandomiseMapTest {

    /**
     * Count number of rooms in folder.
     */
	@Test
	public void CountNumberOfRoomFilesTest() throws IOException, NumberFormatException, 
            InterruptedException {
        RandomiseMap random = new RandomiseMap();
        int roomNum = RandomiseMap.numberOfRooms();
        //assertEquals(9, roomNum);
	}

    /**
     * Tests that the rooms have been sorted correctly
     */
	@Test
    public void sortRoomTest() {

        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        Room room4 = new Room();
        Room room5 = new Room();
        Room room6 = new Room();

        room1.setExit(1, room2);
        room2.setExit(4, room1);

        room1.setExit(2, room3);
        room3.setExit(5, room1);

        room1.setExit(3, room4);
        room4.setExit(0, room1);

        room2.setExit(3, room3);
        room3.setExit(0, room2);

        room2.setExit(1, room5);
        room5.setExit(4, room2);

        room2.setExit(2, room6);
        room6.setExit(5, room2);

        room3.setExit(4, room4);
        room4.setExit(1, room3);

        room3.setExit(1, room6);
        room6.setExit(4, room3);

        room5.setExit(3, room6);
        room6.setExit(0, room5);

        Room[] testRoomArray = {room1, room2, room3, room4, room5, room6};

        Room[] testSorted;
        Room[] actualSorted = {room1, room2, room3, room4, room5, room6};

        testSorted = RandomiseMap.sortRoom(testRoomArray);

        assertArrayEquals(testSorted, actualSorted);

	}

    /**
     * Room with no exits should create at least 1.
     */
    @Test
    public void CreateOneExit() {
        RandomiseMap random = new RandomiseMap();
        Room[] roomArray = new Room[2];
        roomArray[0] = new Room();
        roomArray[1] = new Room();

        RandomiseMap.randomisingExits(roomArray);

        assertTrue("Every room should have at least 1 exit", roomArray[0].numberOfExits() > 0);
        assertTrue("Every room should have at least 1 exit", roomArray[1].numberOfExits() > 0);
    }

    /**
     * Rooms should create complement exits.
     */
    @Test
    public void ComplementExits() {
        RandomiseMap random = new RandomiseMap();
        Room[] roomArray = new Room[2];
        roomArray[0] = new Room();
        roomArray[1] = new Room();

        RandomiseMap.randomisingExits(roomArray);

        // check for complement exits
        for (int i = 0; i < 6; i++) {
            if (roomArray[0].thereIsExit(i)) {
                int complement = (i + 3) % 6;
                assertTrue("Exits should be complementary", roomArray[1].thereIsExit(complement));
            }
        }
    }

    /**
     * Rooms with 6 exits should not have more.
     */
    @Test
    public void MaxExits() {
        RandomiseMap random = new RandomiseMap();
        Room[] roomArray = new Room[2];
        roomArray[0] = new Room();
        roomArray[1] = new Room();

        for (int i = 0; i < 6; i++) {
            roomArray[0].setExit(i, roomArray[1]);
        }

        RandomiseMap.randomisingExits(roomArray);

        assertEquals("Rooms have max of 6 exits", 6, roomArray[0].numberOfExits());
        assertEquals(0, roomArray[1].numberOfExits());
    }
}
