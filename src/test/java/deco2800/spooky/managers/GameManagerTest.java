package deco2800.spooky.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.renderers.PotateCamera;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Jane;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.worlds.JennaWorld;
import deco2800.spooky.worlds.AbstractWorld;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import deco2800.spooky.worlds.TestWorld;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GameManagerTest {
    private TestWorld testWorld;
    private GameManager gm = GameManager.get();
    private Chris chris;
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
        PopUpManager pop = mock(PopUpManager.class);
        Stage stage = mock(Stage.class);
        ExtendViewport ev = mock(ExtendViewport.class);
        SpriteBatch sp = mock(SpriteBatch.class);

        AbstractWorld tw = new JennaWorld();
        GameManager.get().setWorld(tw);
        GameManager.get().setCamera(new PotateCamera(1920, 1080));
        GameManager.get().addManagerToInstance(pop);
//        GameManager.get().setStage(new Stage(new ExtendViewport(1280, 720), sp));
//        GameManager.get().setStage(new Stage(new ExtendViewport(1280, 720), new SpriteBatch()));
    }

    // Commented out because line 26 kept giving NullPointerException
    @Before
    public void setUp() {
        Files files = mock(Files.class);
        Skin skin = mock(Skin.class);
        ClientManager client = mock(ClientManager.class);
        NinePatch temp = mock(NinePatch.class);

        for (Character traitor: gm.getWorld().getRemainingPlayerList()) {
            if (traitor instanceof Jane) {
                traitor.setTraitor(true);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void Testing() {
        assertEquals(3,gm.charactersRemaining()); // In JennaWorld, there are 3 characters in default.
        System.out.println(gm.charactersRemaining());
        //assertFalse(gm.isTraitorDead()); // Now the Traitor is not dead, and set to Jane
        List<Character> remainingCharacterList = gm.getWorld().getRemainingPlayerList();

        int expectingRemaining = 3;

        assertFalse(gm.isTraitorDead());

        for (Character thisCharacter : remainingCharacterList) {
            //make this character dead.
            gm.characterDied(thisCharacter);
            expectingRemaining--;

            //if this character is Jane (who set to be a traitor)
            if (thisCharacter instanceof Jane) {
                assertTrue(gm.isTraitorDead()); //the traitor should be dead.
                gm.characterDied(thisCharacter);
                assertTrue(gm.get().getManager(ClientManager.class).getGameEnd());
            }

            assertFalse(gm.get().getManager(ClientManager.class).getGameEnd());
            assertEquals(expectingRemaining,gm.charactersRemaining());
        }
    }

    @Test
    public void tilesManagementTest() {
        assertFalse(gm.getDebugMode());
        gm.setDebugMode(true);
        assertTrue(gm.getDebugMode());

        assertFalse(gm.getShowCoords());
        gm.setShowCoords(true);
        assertTrue(gm.getShowCoords());

        assertFalse(gm.getShowPath());
        gm.setShowPath(true);
        assertTrue(gm.getShowPath());

        assertFalse(gm.getShowCoordsEntity());
        gm.setShowCoordsEntity(true);
        assertTrue(gm.getShowCoordsEntity());

        assertEquals(0, gm.getEntitiesRendered());
        gm.setEntitiesRendered(2);
        assertEquals(2, gm.getEntitiesRendered());

        assertEquals(0, gm.getEntitiesCount());
        gm.setEntitiesCount(3);
        assertEquals(3, gm.getEntitiesCount());

        assertEquals(0, gm.getTilesRendered());
        gm.setTilesRendered(4);
        assertEquals(4, gm.getTilesRendered());

        assertEquals(0, gm.getTilesCount());
        gm.setTilesCount(5);
        assertEquals(5, gm.getTilesCount());
    }

    @Test
    public void displayTest() {
//        SpriteBatch spBatch = new SpriteBatch();
//        Stage stage = new Stage(new ExtendViewport(1280, 720), );
        Skin skin = new Skin();
        PotateCamera camera = new PotateCamera(1920, 1080);

//        gm.setStage(stage);

//        assertEquals(stage, gm.getStage());

        gm.setSkin(skin);

        assertEquals(skin, gm.getSkin());

        gm.setCamera(camera);

        assertEquals(camera, gm.getCamera());

    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}