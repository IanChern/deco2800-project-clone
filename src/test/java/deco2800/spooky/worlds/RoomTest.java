package deco2800.spooky.worlds;

import deco2800.spooky.worlds.rooms.Room;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Room sampleRoom = new Room();
    private String[] lines = new String[4];

    @Test
    public void RoomCreateTest(){
        this.lines[0] = "3";
        this.lines[1] = "2";
        this.lines[2] = "3:2:(0.2,example.room)(0.8,example2.room)";
        this.lines[3] = "7:1:(1,example.room)";

        for (int i = 0; i < 4; i++){
            switch(i){
                case 0:
                    sampleRoom.setRadius(Integer.parseInt(lines[i]));
                    break;
                case 1:
                    sampleRoom.setRoomEntity(Integer.parseInt(lines[i]));
                    break;
                default:
                    sampleRoom.addEntityTile(lines[i]);
                    break;
            }
        }

        assertEquals(3, sampleRoom.getRadius(),0);
        assertEquals(2, sampleRoom.getRoomEntity(),0);
        assertEquals(true, sampleRoom.getEntityMap().get(0).getCreated());
        assertEquals(true, sampleRoom.getEntityMap().get(1).getCreated());
        assertEquals(3, sampleRoom.getEntityMap().get(0).getTileID());
        assertEquals(7,sampleRoom.getEntityMap().get(1).getTileID());
    }

    @Test
    public void getAndSetExitTest() {
        Room roomNumber2 = new Room();

        //room should have no exits
        for (int i = 0; i < 6; i++) {
            assertFalse(roomNumber2.thereIsExit(i));
        }

        //give a room exits in NE=1 and SW=3
        roomNumber2.setExit(1, new Room());
        roomNumber2.setExit(3, new Room());

        //check exit 1 and 3 are true and rest are false
        for (int i = 0; i < 6; i++) {
            if (i == 1 || i == 3) {
                assertTrue(roomNumber2.thereIsExit(i));
            } else {
                assertFalse(roomNumber2.thereIsExit(i));
            }
        }
    }

    @Test
    public void numberOfExits() {
        Room roomNumber3 = new Room();

        //make sure it has no exits
        assertEquals(roomNumber3.numberOfExits(),0);

        //add 2 exits and check
        roomNumber3.setExit(0, new Room());
        roomNumber3.setExit(1, new Room());
        assertEquals(roomNumber3.numberOfExits(),2);

        //add all 6 exits and check
        for (int i = 2; i < 6; i++) {
            roomNumber3.setExit(i, new Room());
        }
        assertEquals(roomNumber3.numberOfExits(), 6);

    }

    @Test
    public void getExit() {
        Room roomNumber4 = new Room();

        Room exit0 = new Room();
        Room exit5 = new Room();
        Room exit3 = new Room();

        roomNumber4.setExit(0, exit0);
        roomNumber4.setExit(3, exit3);
        roomNumber4.setExit(5, exit5);

        assertEquals(roomNumber4.getExit(0), exit0);
        assertEquals(roomNumber4.getExit(3), exit3);
        assertEquals(roomNumber4.getExit(5), exit5);

    }
}
