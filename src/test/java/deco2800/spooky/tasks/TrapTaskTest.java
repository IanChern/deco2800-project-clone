package deco2800.spooky.tasks;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.entities.TrapType;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TrapManager;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CopyOnWriteArrayList;


public class TrapTaskTest {
    private TestWorld testWorld;
    private GameManager gameManager = GameManager.get();
    private Chris chris;
    private TrapTask trapTask;
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

    @Before
    public void setUp() {
        this.chris = new Chris(0,0,true);
        this.testWorld = new TestWorld();
        testWorld.addEntity(chris);
        gameManager.setWorld(testWorld);

        CopyOnWriteArrayList<Tile> tiles = new CopyOnWriteArrayList<>();

        tiles.add(new Tile("darkness", 0f, 0f));
        tiles.add(new Tile("darkness", 0f, -1.0f));
        tiles.add(new Tile("darkness", 0f, 1.0f));
        tiles.add(new Tile("darkness", 1f, 0.0f));
        tiles.add(new Tile("darkness", -1.0f, 0.0f));
        gameManager.getWorld().setTileMap(tiles);
        gameManager.getWorld().generateNeighbours();

    }

    @Test
    public void checkTrapActivationAndPitfall() {

        HexVector originalPos = chris.getPosition();
        TrapManager tm = gameManager.getWorld().getTrapManager();

        Tile t = GameManager.get().getWorld().getTile(0, 1);
        testWorld.generateNeighbours();

        RoomTrap trap = new RoomTrap(t.getCol(), t.getRow(), t, TrapType.PITFALL);
        testWorld.addEntity(tm.setTrap(t, trap.trapTypeIs()));

        trapTask = new TrapTask(trap);

        trap.onTick(0);
        chris.onTick(0);
        assertFalse(trap.isStep());

        chris.notifyKeyDown(Input.Keys.W);
        for(int i = 0; i < 19; i++) {
            chris.onTick(0);
        }
        trapTask.openTrap(trap, chris);
        assertTrue(trap.isStep());

        assertFalse (chris.getPosition() != originalPos);
    }

    private void ticking(Chris chris, int ticks) {
        for(int i = 0; i < ticks; i++) {
            chris.notifyKeyDown(Input.Keys.W);
            chris.onTick(0);
        }
    }

    @Test
    public void checkQuickSand() {
        TrapManager tm = gameManager.getWorld().getTrapManager();

        Tile t = GameManager.get().getWorld().getTile(1, 0);

        RoomTrap trap = new RoomTrap(t.getCol(), t.getRow(), t, TrapType.QUICKSAND);
        testWorld.addEntity(tm.setTrap(t, trap.trapTypeIs()));

        trapTask = new TrapTask(trap);

        chris.notifyKeyDown(Input.Keys.D);
        for(int i = 0; i < 19; i++) {
            chris.onTick(0);
        }
        trapTask.openTrap(trap, chris);
        assertEquals(0f, chris.getSpeed(),0.00001f);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}
