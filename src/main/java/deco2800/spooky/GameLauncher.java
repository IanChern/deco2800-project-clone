package deco2800.spooky;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DesktopLauncher
 * @author Tim Hadwen
 * Collision branch !
 */
public class GameLauncher {

	public static final ThomasGame game = new ThomasGame();
	private static final Logger LOGGER = LoggerFactory.getLogger(GameScreen.class);

	/**
	 * 
	 */
    public GameLauncher() {
		//Changed GameLauncher constructor to public for use in JennaWorld
	}

	/**
	 * Main function for the game
	 * @param arg Command line arguments (we wont use these)
	 */
	@SuppressWarnings("unused") //app
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LwjglApplication app;
		config.width = 1280;
		config.height = 720;
		config.title = "Temple Traitors";
		config.addIcon("resources/icon32x32.png", Files.FileType.Internal);
		app = new LwjglApplication(game, config);
		GameManager.get().getManager(SoundManager.class).backgroundList();
		LOGGER.info(app.toString());
	}
}