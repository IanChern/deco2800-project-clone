package deco2800.spooky.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.entities.Rock;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CollisionManagerTest {

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
     * Generates a test world and places a rock in a particular position
     */
    private GameManager directionSetCollision(float rockCol, float rockRow, Chris chris) {
        GameManager gameManager = GameManager.get();
        TestWorld testWorld = new TestWorld();
        testWorld.addEntity(chris);
        gameManager.setWorld(testWorld);
        Tile t = GameManager.get().getWorld().getTile(rockCol, rockRow);
        Rock rock = new Rock(t, true);
        testWorld.addEntity(rock);
        return(gameManager);
    }

    private void ticking(Chris chris, int ticks) {
        for(int i = 0; i < ticks; i++) {
            chris.onTick(0);
        }
    }

    /**
     * Checks the collision using key 'W'
     */
    @Test
    public void checkCollisionUp() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.W;

        GameManager gameManager = directionSetCollision(0.0f, 1.0f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Checks the collision using key 'S'
     */
    @Test
    public void checkCollisionDown() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.S;

        GameManager gameManager = directionSetCollision(0.0f, -1.0f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Checks the collision using key 'Q'
     */
    @Test
    public void checkCollisionLeftUp() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.Q;

        GameManager gameManager = directionSetCollision(-1.0f, 0.5f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Checks the collision using key 'A'
     */
    @Test
    public void checkCollisionLeftDown() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.A;

        GameManager gameManager = directionSetCollision(-1.0f, -0.5f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Checks the collision using key 'E'
     */
    @Test
    public void checkCollisionRightUp() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.E;

        GameManager gameManager = directionSetCollision(1.0f, 0.5f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Checks the collision using key 'D'
     */
    @Test
    public void checkCollisionRightDown() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.D;

        GameManager gameManager = directionSetCollision(1.0f, -0.5f, chris);

        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 0.0f, 0.0001f);
    }

    /**
     * Moves up one tile then collides
     */
    @Test
    public void checkCollisionUpTwoTiles() {
        Chris chris = new Chris(0,0, true);
        int keycode = Input.Keys.W;

        GameManager gameManager = directionSetCollision(0.0f, 2.0f, chris);

        //Moves up
        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);


        //Collides
        chris.notifyKeyDown(keycode);

        //To se off movement as takes extra 7 ticks to enable task to move
        ticking(chris, 40);
        chris.notifyKeyUp(keycode);

        //Checks after movement is finished only takes 10 ticks
        ticking(chris, 40);

        assertEquals(chris.getCol(), 0.0f, 0.0001f);
        assertEquals(chris.getRow(), 1.0f, 0.0001f);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}