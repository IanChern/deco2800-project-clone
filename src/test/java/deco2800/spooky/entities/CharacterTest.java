package deco2800.spooky.entities;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.tasks.PlayerMovement;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.TestWorld;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.worlds.AbstractWorld;
import org.junit.Before;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import static org.junit.Assert.*;

public class CharacterTest {

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
        Character chris = new Chris(0, 0, false);

        Character jane = new Jane(1, 1, false);
        Character damien = new Damien(1, 0, false);
        Character katie = new Katie(10f, 10f, false);
        Character larry = new Larry(5f, 5f, false);
        Character titus = new Titus(3f, 3f, false);

        assertEquals("down", chris.getDirection());
        assertFalse(chris.isMainCharacter());
        chris.setMainCharacter(true);
        assertTrue(chris.isMainCharacter());
    }

    @Test
    public void testSetTraitor() {
        Character chris = new Chris(0f, 0f,true);
        assertFalse(chris.isTraitor());
        chris.setTraitor(true);
        assertTrue(chris.isTraitor());
    }

    @Test
    public void moveUp() {
        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(0.0f, 1.0f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        for(int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), 0.0f, 0.00001f);
        assertEquals(chris.getRow(), 1.0f, 0.00001f);
    }

    @Test
    public void playerControlled() {
        Chris chris = new Chris(0, 0, true);
        assertTrue(chris.isPlayerControlled());
    }

    @Test
    public void isTraitor() {
        Chris chris = new Chris(0, 0, true);
        chris.setTraitor(true);
        assertTrue(chris.isTraitor());
    }

    @Test
    public void visualSightTest() {
        Chris chris = new Chris(0, 0, true);
        chris.setVisualSight(1);
        assertEquals(1, chris.getVisualSight());
    }

    @Test
    public void appearanceTest() {
        Chris chris = new Chris(0, 0, true);
        Appearance app = new Appearance("Grass");
        chris.setAppearance(app);
        assertEquals(app, chris.getAppearance());
    }

    @Test
    public void setDestinationTest() {
        Chris chris = new Chris(0, 0, true);
        chris.setDestination(new HexVector(0, 1));
    }

    @Test
    public void updateHealth() {
        Chris chris = new Chris(0, 0, true);
        chris.setHealth(100);
        chris.updateHealth(-10);
        assertEquals(90, chris.getHealth());
    }

    @Test
    public void updateHealthShield() {
        Chris chris = new Chris(0, 0, true);
        chris.setHealth(100);
        chris.updateHealth(-10);
        assertEquals(90, chris.getHealth());
    }

    @Test
    public void killPlayer() {
        Chris chris = new Chris(0, 0, true);
        assertFalse(chris.isKilled());
        chris.setKilled(true);
        assertTrue(chris.isKilled());
    }

    @Test
    public void moveDown() {

        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(0.0f, -1.0f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        for(int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), 0f, 0.00001f);
        assertEquals(chris.getRow(), -1.0f, 0.00001f);
    }

    @Test
    public void moveLeftUp() {
        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(-1.0f, 0.5f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        for(int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), -1.0f, 0.00001f);
        assertEquals(chris.getRow(), 0.5f, 0.00001f);
    }

    @Test
    public void moveRightUp() {
        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(1.0f, 0.5f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        for(int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), 1.0f, 0.00001f);
        assertEquals(chris.getRow(), 0.5f, 0.00001f);
    }

    @Test
    public void moveLeftDown() {
        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(-1.0f, -0.5f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        for(int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), -1.0f, 0.00001f);
        assertEquals(chris.getRow(), -0.5f, 0.00001f);
    }

    @Test
    public void moveRightDown() {
        Character chris = new Chris(0.0f, 0.0f, true);
        HexVector destination = new HexVector(1.0f, -0.5f);

        //Sets up and tests the PlayerMovement to avoid using managers
        chris.task = new PlayerMovement(chris, destination, 0.1f);
        //Should only take 10 ticks but checks after that it dosen't keep moving
        for (int i = 0; i < 20; i++) {
            chris.onTick(i);
        }

        assertEquals(chris.getCol(), 1.0f, 0.00001f);
        assertEquals(chris.getRow(), -0.5f, 0.00001f);
    }

    @Test
    public void testIsTraitor() {
        Character chris = new Chris(0f, 0f,true);
        assertFalse(chris.isTraitor());
    }

    @Test
    public void testSetHealth() {
        Character chris = new Chris(0f, 0f,true);
        chris.setHealth(40);
        assertEquals(40, chris.getHealth());
    }

    @Test
    public void testSetSpeed() {
        Character chris = new Chris(0f, 0f,true);
        chris.setSpeed(1);
        assertEquals(1f, chris.getSpeed(), 0.001);
    }

    @Test
    public void testRefillHealth() {
        Character chris = new Chris(0f, 0f,true);
        chris.setHealth(40);
        chris.refillHealth();
        assertEquals(100, chris.getHealth());
    }

    @Test
    public void testUpdateHealth() {
        Character chris = new Chris(0f, 0f,true);
        chris.setHealth(100);
        chris.updateHealth(-19);
        assertEquals(81, chris.getHealth());
    }


    @Test
    public void testRandomInt() {
        for (int i = 1; i < 100; i++) {
            int randomInt = Character.randInt(-i, i);
            assertTrue(randomInt <  i);
            assertTrue(randomInt >= -1 * i);
        }
    }

    @Test
    public void resetId() {
        AbstractEntity.resetID();
        assertEquals(0, AbstractEntity.getNextID());
    }

    @Test
    public void getColRenderWidth() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals(1f, chris.getColRenderWidth(), 0.0f);
    }

    @Test
    public void getRowRenderWidth() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals(1f, chris.getRowRenderWidth(), 0.0f);
    }

    @Test
    public void getColRenderLength() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals(1f, chris.getColRenderLength(), 0.0f);
    }

    @Test
    public void getRowRenderLength() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals(1f, chris.getRowRenderLength(), 0.0f);
    }

    @Test
    public void setRenderOrder() {
        Character chris = new Chris(0f, 0f,true);
        chris.setRenderOrder(10);
        assertEquals(10, chris.getRenderOrder());
    }

    @Test
    public void equalsNull() {
        Character chris = new Chris(0f, 0f,true);
        assertFalse(chris.equals(null));
    }

    @Test
    public void distance() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals(1, chris.distance(new Chris(0f, 1f, false)), 0.0f);
    }

    @Test
    public void getName() {
        Character chris = new Chris(0f, 0f,true);
        assertEquals("playerCharacter", chris.getObjectName());
    }

    @Test
    public void setEntityID() {
        Character chris = new Chris(0f, 0f,true);
        chris.setEntityID(100);
        assertEquals(100, chris.getEntityID());
    }

    @Test
    public void entityCompare() {
        Character chris = new Chris(0f, 0f,true);
        Character chris2 = new Chris(0f, 0f,true);
        EntityCompare ec = new EntityCompare();
        assertEquals(chris.getEntityID() - chris2.getEntityID(), ec.compare(chris, chris2));
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }

    @Test
    public void isKilled() {
        Character chris = new Chris(0,0, true);
        assertFalse(chris.isKilled());
    }

    @Test
    public void setKilled() {
        Character chris = new Chris(0,0, true);
        assertFalse(chris.isKilled());
        chris.setKilled(true);
        assertTrue(chris.isKilled());
        chris.setKilled(false);
        assertFalse(chris.isKilled());
    }

    @Test
    public void isPlayerControlled() {
        Character chris = new Chris(0,0, true);
        assertTrue(chris.isPlayerControlled());
    }

    @Test
    public void getDirection() {
        Character chris = new Chris(0,0, true);
        assertEquals("down", chris.getDirection());
    }

    @Test
    public void setMainCharacter() {
        Character chris = new Chris(0,0, true);
        assertFalse(chris.isMainCharacter());
        chris.setMainCharacter(true);
        assertTrue(chris.isMainCharacter());
        chris.setMainCharacter(false);
        assertFalse(false);
    }

    @Test
    public void setTraitor() {
        Character chris = new Chris(0,0, true);
        assertFalse(chris.isMainCharacter());
        chris.setTraitor(true);
        assertTrue(chris.isTraitor());
        chris.setTraitor(false);
        assertFalse(chris.isTraitor());
    }

    @Test
    public void getHealth() {
        Character chris = new Chris(0,0, true);
        assertEquals(75, chris.getHealth());
    }

    @Test
    public void getSpeed() {
        Character chris = new Chris(0,0, true);
        assertEquals(0.1f, chris.getSpeed(), 0f);
    }

    @Test
    public void setVisualSight() {
        Character chris = new Chris(0,0, true);
        assertEquals(6, chris.getVisualSight());
        chris.setVisualSight(5);
        assertEquals(5, chris.getVisualSight());
    }

    @Test
    public void getVisualSight() {
        Character chris = new Chris(0,0, true);
        assertEquals(6, chris.getVisualSight());
    }

    @Test
    public void setAppearance1() {
        Character damien = new Damien(0,0, true);
        Appearance testAppearance = new Appearance("damien");
        damien.setAppearance(testAppearance);
        assertEquals("damien_standing", damien.getAppearance().getDefaultAppearance());
        assertEquals("spacman_blue", damien.getAppearance().getName());
        assertEquals("damien_neutral_up", damien.getAppearance().getNeutralDirectionAppearance("up"));
    }

    @Test
    public void setAppearance2() {
        Character damien = new Damien(0,0, true);
        Appearance testAppearance = new Appearance("damien");
        damien.setAppearance(testAppearance);
        assertNull(damien.getAppearance().getHair());
        assertNull(damien.getAppearance().getSkin());
        assertNull(damien.getAppearance().getCloth());
    }

    @Test
    public void setDestination() {
        Character damien = new Damien(0,0, true);
        assertEquals(new HexVector(0, 0), damien.getDestination());
        damien.setDestination(new HexVector(0,1));
        assertEquals(new HexVector(0, 1), damien.getDestination());
        damien.setDestination(new HexVector(3,1));
        assertEquals(new HexVector(3, 1), damien.getDestination());
    }

    @Test
    public void getMovement1() {
        Character damien = new Damien(0,0, true);
        assertEquals("standing", damien.getMovement());
        damien.setKeyCodeDown(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeDown(false);
        assertEquals("standing", damien.getMovement());
    }

    @Test
    public void getMovement2() {
        Character damien = new Damien(0,0, true);
        damien.setKeyCodeNE(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeNE(false);
        assertEquals("standing", damien.getMovement());
    }

    @Test
    public void getMovement3() {
        Character damien = new Damien(0,0, true);
        damien.setKeyCodeNW(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeNW(false);
        assertEquals("standing", damien.getMovement());
    }

    @Test
    public void getMovement4() {
        Character damien = new Damien(0,0, true);
        damien.setKeyCodeSE(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeSE(false);
        assertEquals("standing", damien.getMovement());

    }

    @Test
    public void getMovement5() {
        Character damien = new Damien(0,0, true);
        damien.setKeyCodeSW(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeSW(false);
        assertEquals("standing", damien.getMovement());
    }

    @Test
    public void getMovement6() {
        Character damien = new Damien(0,0, true);
        damien.setKeyCodeUp(true);
        assertEquals("walking", damien.getMovement());
        damien.setKeyCodeUp(false);
        assertEquals("standing", damien.getMovement());
    }

    @Test
    public void stopWalk() {
        Character damien = new Damien(0,0, true);
        String[] DIRECTION = {"up","down","left","right", "NorthW", "NorthE", "SouthW", "SouthE"};
        for (String direction : DIRECTION) {
            damien.stopWalk(direction);
            assertEquals("damien_neutral_" + direction, damien.getTexture());
        }
    }

    @Test
    public void pickUpWeapon() {
        Character damien = new Damien(0, 0, true);
        MeleeWeapon axe = new MeleeWeapon(0, 0, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        assertEquals(1, damien.getWeapons().size());
        assertEquals(MeleeType.AXE, damien.getWeapons().get(0).getName());
        assertEquals(0, damien.getWeapons().get(0).getCol(), 0);
        assertEquals(0, damien.getWeapons().get(0).getRow(), 0);
    }

    @Test
    public void findWeapon1() {
        Character damien = new Damien(0, 0, true);
        assertNull(null, damien.findWeapon(MeleeType.AXE));
    }

    @Test
    public void findWeapon2() {
        Character damien = new Damien(5, 5, true);
        MeleeWeapon axe = new MeleeWeapon(5, 5, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        assertEquals(MeleeType.AXE, damien.findWeapon(MeleeType.AXE).getName());
        assertEquals(5, damien.findWeapon(MeleeType.AXE).getCol(), 0);
        assertEquals(5, damien.findWeapon(MeleeType.AXE).getRow(), 0);
    }

    @Test
    public void findWeapon3() {
        Character damien = new Damien(5, 5, true);
        MeleeWeapon axe = new MeleeWeapon(5, 5, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        MeleeWeapon dagger = new MeleeWeapon(1, 1, MeleeType.DAGGER);
        damien.pickUpWeapon(dagger);
        //Find the axe
        assertEquals(MeleeType.AXE, damien.findWeapon(MeleeType.AXE).getName());
        assertEquals(5, damien.findWeapon(MeleeType.AXE).getCol(), 0);
        assertEquals(5, damien.findWeapon(MeleeType.AXE).getRow(), 0);

        //Find the dagger
        assertEquals(MeleeType.DAGGER, damien.findWeapon(MeleeType.DAGGER).getName());
        assertEquals(1, damien.findWeapon(MeleeType.DAGGER).getCol(), 0);
        assertEquals(1, damien.findWeapon(MeleeType.DAGGER).getRow(), 0);

    }

    @Test
    public void dropCurrentWeapon() {
        Character damien = new Damien(0, 0, true);
        MeleeWeapon axe = new MeleeWeapon(0, 0, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        damien.switchWeapon(1);
    }

    @Test
    public void switchWeapon1() {
        Character damien = new Damien(0, 0, true);
        MeleeWeapon axe = new MeleeWeapon(0, 0, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        damien.switchWeapon(1);
        assertEquals(MeleeType.AXE, damien.getCurrentWeapon().getName());
        assertEquals(0, damien.getCurrentWeapon().getCol(), 0);
        assertEquals(0, damien.getCurrentWeapon().getRow(), 0);
    }

    @Test
    public void switchWeapon2() {
        Character damien = new Damien(0, 0, true);
        MeleeWeapon axe = new MeleeWeapon(0, 0, MeleeType.AXE);
        damien.pickUpWeapon(axe);
        MeleeWeapon dagger = new MeleeWeapon(1, 1, MeleeType.DAGGER);
        damien.pickUpWeapon(dagger);
        damien.pickUpWeapon(axe);
        damien.switchWeapon(2);
        assertEquals(MeleeType.DAGGER, damien.getCurrentWeapon().getName());
        assertEquals(1, damien.getCurrentWeapon().getCol(), 0);
        assertEquals(1, damien.getCurrentWeapon().getRow(), 0);
    }

    @Test
    public void switchWeapon3() {
        Character damien = new Damien(1, 2, true);
        MeleeWeapon handbag = new MeleeWeapon(1, 2, MeleeType.HANDBAG);
        damien.pickUpWeapon(handbag);
        damien.switchWeapon(3);
        assertEquals(MeleeType.HANDBAG, damien.getCurrentWeapon().getName());
        assertEquals(1, damien.getCurrentWeapon().getCol(), 0);
        assertEquals(2, damien.getCurrentWeapon().getRow(), 0);
    }

    @Test
    public void switchWeapon4() {
        Character damien = new Damien(3, 3, true);
        MeleeWeapon sword = new MeleeWeapon(3, 3, MeleeType.SWORD);
        damien.pickUpWeapon(sword);
        damien.switchWeapon(4);
        assertEquals(MeleeType.SWORD, damien.getCurrentWeapon().getName());
        assertEquals(3, damien.getCurrentWeapon().getCol(), 0);
        assertEquals(3, damien.getCurrentWeapon().getRow(), 0);
    }

    @Test
    public void getWeapons() {
        Character damien = new Damien(0, 0, true);
        assertEquals(0, damien.getWeapons().size());
    }
}


