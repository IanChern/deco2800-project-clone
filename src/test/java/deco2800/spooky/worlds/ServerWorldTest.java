package deco2800.spooky.worlds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.renderers.PotateCamera;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class ServerWorldTest {
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

    @Test
    public void onTick() {
        ServerWorld server = new ServerWorld();
        server.generateWorld();
        server.onTick(1);
    }

    @Test
    public void generateWorld() {
        ServerWorld server = new ServerWorld();
        server.generateWorld();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}
