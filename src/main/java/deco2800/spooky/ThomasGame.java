package deco2800.spooky;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.spooky.mainmenu.MainMenuScreen;
import deco2800.spooky.managers.GameManager;

/**
 * This class provides the game wrapper that different screens are plugged into
 */
		public class ThomasGame extends Game {
			/**
			 * The SpriteBatch for the game
			 */
			private SpriteBatch batch;
			private MainMenuScreen mainMenuScreen;

			/**
			 * Creates the mainmenu screen
			 */
			public void create() {
				batch = new SpriteBatch();
				initUISkin();
				mainMenuScreen = new MainMenuScreen(this);
				this.setScreen(mainMenuScreen);

	}

	/**
	 * Disposes of the game
	 */
	public void dispose() {
		mainMenuScreen.dispose();
		batch.dispose();
	}

	public void initUISkin() {
		GameManager.get().setSkin(new Skin(Gdx.files.internal("resources/uiskin.skin")));
	}

	public MainMenuScreen getMainMenuScreen() {
		return(mainMenuScreen);
	}

	public SpriteBatch getBatch() {
		return(batch);
	}

	public void setBatch(SpriteBatch batchVar) {
		this.batch = batchVar;
	}
}
