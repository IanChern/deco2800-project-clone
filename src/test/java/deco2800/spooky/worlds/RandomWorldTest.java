package deco2800.spooky.worlds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.entities.Rock;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.worlds.rooms.Room;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.lang.InterruptedException;

/**
 * Tests the RandomWorld java class.
 *
 * @author Frazer Carsley
 */
public class RandomWorldTest {
    /**
     * Gets room array from given path
     */
    private Room[] getRoomArray(String path) throws InterruptedException {
        List<List<String>> roomsFromFile = ReadSerialisation.getRoomsToSend(path);

        int i = 0;
        Room[] roomArray = new Room[roomsFromFile.size()];
        for (List<String> readFile : roomsFromFile) {
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
     * Test the radius is calculated properly with only 1 room
     */
    @Test
    public void RadiusTest1() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "resources\\testrooms2";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "resources/testrooms2";
        } else {
            // unsupported
            path = "";
        }
        Room[] roomArray = getRoomArray(path);
        RandomWorld world = new RandomWorld();

        assertEquals(14, world.calculateRadius(roomArray));
    }

    /**
     * Test the radius is calculated properly with 2 rooms
     */
    @Test
    public void RadiusTest2() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "resources\\testrooms";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "resources/testrooms";
        } else {
            // unsupported
            path = "";
        }
        Room[] roomArray = getRoomArray(path);
        RandomWorld world = new RandomWorld();

        assertEquals(28, world.calculateRadius(roomArray));
    }

    /**
     * Test the radius is calculated properly with 3 rooms
     */
    @Test
    public void RadiusTest3() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "resources\\testrooms3";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "resources/testrooms3";
        } else {
            // unsupported
            path = "";
        }
        Room[] roomArray = getRoomArray(path);
        RandomWorld world = new RandomWorld();

        assertEquals(28, world.calculateRadius(roomArray));
    }

    /**
     * Test the radius is calculated properly with 8 rooms
     */
    @Test
    public void RadiusTest4() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "resources\\testrooms4";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "resources/testrooms4";
        } else {
            // unsupported
            path = "";
        }
        Room[] roomArray = getRoomArray(path);
        RandomWorld world = new RandomWorld();

        assertEquals(57, world.calculateRadius(roomArray));
    }

    /**
     * Ensure rooms are retrieved in descending order of radius.
     */
    @Test
    public void getRoomsTest() throws InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.indexOf("win") >= 0) {
            // windows
            path = "resources\\testrooms4";
        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
            // unix type system
            path = "resources/testrooms4";
        } else {
            // unsupported
            path = "";
        }

        Room[] roomArray = RandomWorld.getRooms(path);

        for (int i = 0; i < roomArray.length - 1; i++) {
            assertTrue("Rooms must be arranged largest to smallest, based on radius",
                    roomArray[i].getRadius() >= roomArray[i + 1].getRadius());
        }
    }

    private static Application game;

    @BeforeClass
    public static void setUpBeforeClass() {
        game = new HeadlessApplication(
                new ApplicationListener() {
                    @Override public void create() {}
                    @Override public void resize(int width, int height) {}
                    @Override public void render() {}
                    @Override public void pause() {}
                    @Override public void resume() {}
                    @Override public void dispose() {}
                });
        // Use Mockito to mock the OpenGL methods
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
        TestWorld tw = new TestWorld();
        GameManager.get().setWorld(tw);
        GameManager.get().setCamera(new PotateCamera(1920, 1080));
    }

    /**
     * Tests the base RandomWorld is generated and does not throw an error
     */
    @Test
    public void testWorldGenerates() {
        RandomWorld world = new RandomWorld();
        GameManager.get().setWorld(world);
        world.generateWorld();

        //Let the world run for a while
        world.onTick(0);

        //Makes sure that entities were added
        assertFalse(world.entities.isEmpty());
    }

//    @Test
//    public void getCurrentRoomTest() throws InterruptedException {
//        RandomWorld world = new RandomWorld();
//        GameManager.get().setWorld(world);
//        world.generateWorld();
//        Chris chris = new Chris(0,0,true);
//
//        String os = System.getProperty("os.name").toLowerCase();
//        String path;
//        if (os.indexOf("win") >= 0) {
//            // windows
//            path = "resources\\testrooms4";
//        } else if ((os.indexOf("nix") >= 0) || (os.indexOf("mac") >= 0) || (os.indexOf("nux") >= 0)) {
//            // unix type system
//            path = "resources/testrooms4";
//        } else {
//            // unsupported
//            path = "";
//        }
//
//        Room[] roomArray = world.getRooms(path);
//
//        GameManager.get().getWorld().addEntity(chris);
//
//        assertEquals(room, ((RandomWorld) GameManager.get().getWorld()).getCurrentRoom(chris));
//    }
    /**
     * Tests if a rock which has a 100% success rate of generating has been added to the entities list
     * note: if this test fails then the rock on position [1, 1.5] has been changed or removed
     */
    @Test
    public void testRockAdded() {
        boolean check = true;
        RandomWorld world = new RandomWorld();
        GameManager.get().setWorld(world);
        world.generateWorld();
        world.onTick(0);

        for (AbstractEntity entity : world.entities) {
            if (entity instanceof Rock && entity.getPosition().equals(new HexVector(1.0f, 1.5f))) {
                check = true;
            }
        }

        assertTrue(check);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}
