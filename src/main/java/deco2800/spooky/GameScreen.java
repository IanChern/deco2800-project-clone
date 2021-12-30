package deco2800.spooky;

import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Peon;
import deco2800.spooky.handlers.KeyboardManager;
import deco2800.spooky.managers.DatabaseManager;
import deco2800.spooky.managers.FlashManager;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.InputManager;
import deco2800.spooky.managers.PathFindingService;
import deco2800.spooky.managers.PauseScreenManager;
import deco2800.spooky.managers.ScreenManager;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.managers.SoundManager;
import deco2800.spooky.managers.StatusManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.observers.KeyDownObserver;
import deco2800.spooky.renderers.OverlayRenderer;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.renderers.Renderer3D;
import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.ClientWorld;
import deco2800.spooky.worlds.RandomWorld;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;


public class GameScreen implements Screen,KeyDownObserver {
	private final Logger log = LoggerFactory.getLogger(Renderer3D.class);
	@SuppressWarnings("unused")
	private final ThomasGame game;
	/**
	 * Set the renderer.
	 * 3D is for Isometric worlds
	 * Check the documentation for each renderer to see how it handles WorldEntity coordinates
	 */
	Renderer3D renderer = new Renderer3D();
	OverlayRenderer rendererDebug = new OverlayRenderer();
	AbstractWorld world;
	private Skin skin;

	private PauseScreenManager pauseScreenManager;
	private FlashManager flashManager;
	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.gra
	 */
	PotateCamera camera;
	PotateCamera cameraDebug;

	private ProgressBar bar;
	private Label label;

	private Stage stage;

	private StatusManager statusManager;
	long lastGameTick = 0;
	long lastFlashTick = 0;
	long currentTick = 0;
	int lastHealth;

	private Skin skin1;
	private TextureManager textureManager;
	private SoundManager sound;
	private ImageButton heart;

	public GameScreen(final ThomasGame game, boolean isHost, boolean isMultiplayer,
            String username, String hostIP) throws UnknownHostException {
		/* Create an example world for the engine */
		this.game = game;
		this.textureManager = new TextureManager();
		sound = new SoundManager();
		/* Health bar created */
		stage = new Stage(new ExtendViewport(1280, 720));

		skin = new Skin();
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fill();
		skin.add("black", new Texture(pixmap));
		pixmap.dispose();

		pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		skin.add("red", new Texture(pixmap));

		//adding a title to the health bar
		skin1 = new Skin(Gdx.files.internal("resources/uiskin.skin"));
		Label label = new Label("Health Bar", skin1);
		label.setColor(Color.RED);
		label.setSize(1000, 1000);
		label.setPosition(15,5);
		label.setAlignment(20);
		stage.addActor(label);


		TextureRegionDrawable textureBar = new TextureRegionDrawable();
		textureBar.setRegion(new TextureRegion(new Texture(pixmap)));
		ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle
				(skin.newDrawable("black", Color.BLACK), textureBar);
		barStyle.knobBefore = barStyle.knob;
		bar = new ProgressBar(0,100,1,false,barStyle);
		bar.setValue(100);
		bar.setOrigin(20);//bottomRight
		bar.setSize(1000, bar.getPrefHeight());
		bar.setAnimateDuration(1);
		bar.getVisualPercent();
		stage.addActor(bar);

		bar.setValue(100);

		// heart
		Image heartImage = new Image(this.textureManager.getTexture("heart3"));
		this.heart = new ImageButton(heartImage.getDrawable());
		this.stage.addActor(this.heart);
		this.heart.setPosition(1020, 10);

		// add health bar value
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		BitmapFont barFont = new BitmapFont();
		labelStyle.font = barFont;
		labelStyle.fontColor = Color.WHITE;
		Label barLabel = new Label(String.valueOf((int) bar.getValue()), labelStyle);
		barLabel.setSize(40, 40);
		barLabel.setPosition(1050, -2);
		stage.addActor(barLabel);


		// original code
		GameManager gameManager = GameManager.get();
		StatusManager statusManager1 = new StatusManager(game);
		GameManager.addManagerToInstance(statusManager1);
		statusManager1.newGame();

		// Create main world
		if (!isHost) {
			world = new ClientWorld();
            //GameManager.get().getManager(ClientManager.class).reconnect(hostIP, username);
		} else {
			world = new RandomWorld();
			GameManager.get().getManager(ServerManager.class).startHosting("host");
			for (Character player: world.getRemainingPlayerList()){
				if(player.isPlayerControlled()) {
					updateHealth(player.getHealth());
					lastHealth = player.getHealth();
				}
			}

		}

		//setWorld
		gameManager.setWorld(world);
		gameManager.setStage(stage);

		// Add first peon to the world
		camera = new PotateCamera(1920, 1080);
		cameraDebug = new PotateCamera(1920, 1080);

		/* Add the window to the stage */
		GameManager.get().setSkin(skin);
		GameManager.get().setStage(stage);
		GameManager.get().setCamera(camera);

		PathFindingService pathFindingService = new PathFindingService();
		GameManager.get().addManager(pathFindingService);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(GameManager.get().getManager(KeyboardManager.class));
		multiplexer.addProcessor(GameManager.get().getManager(InputManager.class));
		Gdx.input.setInputProcessor(multiplexer);

		GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);

		this.pauseScreenManager = new PauseScreenManager(this.game, this.stage);
		this.flashManager = new FlashManager(this.game, this.stage);

		GameManager.get().getManager(ScreenManager.class).setCurrentScreen(this);

		//The timer for staging the game

	}

	

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render(float delta) {

		handleRenderables();

		moveCamera();

		cameraDebug.position.set(camera.position);
		cameraDebug.update();
		camera.update();

		SpriteBatch batchDebug = new SpriteBatch();
		batchDebug.setProjectionMatrix(cameraDebug.combined);
		Image bg = new Image(new TextureRegion(
				GameManager.getManagerFromInstance(TextureManager.class).getTexture("bgOfLabel")));
		bg.setBounds(0, 1000, 800, 800);


		stage.addActor(bg);
		// Clear the entire display as we are using lazy rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rerenderMapObjects(batchDebug, camera);
		rendererDebug.render(batchDebug, cameraDebug);

		stage.addActor(bg);

		/* Refresh the experience UI for if information was updated */
		stage.act(delta);
		stage.draw();
		batchDebug.dispose();
	}

	private void handleRenderables() {
		if (System.currentTimeMillis() - lastGameTick > 20) {
			lastGameTick = System.currentTimeMillis();
			GameManager.get().onTick(0);
		}
		flashManager.onTick(0);
		for (Character player: world.getRemainingPlayerList()){
			if(player.isPlayerControlled()) {
				updateHealth(player.getHealth());
				lastHealth = player.getHealth();
			}
		}

	}

	public void updateHealth(int health){

		bar.setValue(health);

	}


	/**
	 * get current time
	 * @return current time in millis
	 */

	public long getCurrentTime() {
		return TimeUtils.millis();
	}

	/**
	 * get the time difference between two renderings
	 * @return float - time difference
	 */
	public long getDeltaTime(long startTime) {
		return TimeUtils.timeSinceMillis(startTime);
	}

	/**
	 * Use the selected renderer to render objects onto the map
	 */
	private void rerenderMapObjects(SpriteBatch batch, OrthographicCamera camera) {
		renderer.render(batch, camera);
	}

	@Override
	public void show() {
		/* This is empty for some reason */
	}

	/**
	 * Resizes the viewport
	 *
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

		cameraDebug.viewportWidth = width;
		cameraDebug.viewportHeight = height;
		cameraDebug.update();
	}

	@Override
	public void pause() {
		//do nothing
	}

	@Override
	public void resume() {
		//do nothing
	}

	@Override
	public void hide() {
		//do nothing
	}

	/**
	 * Disposes of assets etc when the rendering system is stopped.
	 */
	@Override
	public void dispose() {
		// Don't need this at the moment
		System.exit(0);
	}

	@Override
	public void notifyKeyDown(int keycode) {
		if (keycode == Input.Keys.F12) {
			GameManager.get().setDebugMode(!GameManager.get().getDebugMode());
		}

		if (keycode == Input.Keys.F5) {
			world = new TestWorld();
			AbstractEntity.resetID();
			Tile.resetID();
			GameManager gameManager = GameManager.get();
			gameManager.setWorld(world);

			// Add first peon to the world
			Peon peon1 = new Peon(0f, 0f, 0.05f);
			world.addEntity(peon1);
		}

		if (keycode == Input.Keys.F1) {
			world = new RandomWorld();
			AbstractEntity.resetID();
			Tile.resetID();
			GameManager gameManager = GameManager.get();
			gameManager.setWorld(world);
		}

		if (keycode == Input.Keys.F11) { // F11
			GameManager.get().setShowCoords(!GameManager.get().getShowCoords());
			log.info("Show coords is now {}", GameManager.get().getShowCoords());
		}


		if (keycode == Input.Keys.C) { // F11
			GameManager.get().setShowCoords(!GameManager.get().getShowCoords());
			log.info("Show coords is now {}", GameManager.get().getShowCoords());
		}

		if (keycode == Input.Keys.F10) { // F10
			GameManager.get().setShowPath(!GameManager.get().getShowPath());
			log.info("Show Path is now {}", GameManager.get().getShowPath());
		}

		if (keycode == Input.Keys.F3) { // F3
			// Save the world to the DB
			DatabaseManager.saveWorld(null);
		}

		if (keycode == Input.Keys.F4) { // F4
			// Load the world to the DB
			DatabaseManager.loadWorld(null);
		}

		if (keycode == Input.Keys.F6) {
			sound.muteSound();
		}

		if (keycode == Input.Keys.F7) {
			sound.smallerSound();
		}

		if (keycode == Input.Keys.F8) {
			sound.louderSound();
		}

		if (keycode == Input.Keys.F9) {
			sound.changeBackground();
		}
		//The Status Manager Testing
		if (keycode == Input.Keys.PAGE_UP) {
			statusManager.nextStatus();
		}

		if (keycode == Input.Keys.P) {
			this.pauseScreenManager.setPauseTableVisible();
		}

		if (keycode == Input.Keys.F) {
			this.flashManager.showFlash();
			this.flashManager.setLastFlash(flashManager.getTickCount());
		}
 }


	private void potateMoveCamera(float normilisedGameSpeed, int goFastSpeed) {
		//move the camera
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			goFastSpeed *= goFastSpeed * goFastSpeed;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-goFastSpeed, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(goFastSpeed, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -goFastSpeed, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, goFastSpeed, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
			camera.zoom *=1-0.01*normilisedGameSpeed;
			if (camera.zoom < 0.5) {
				camera.zoom = 0.5f;
			}
		}
	}

	public void moveCamera() {
		//timmeh to fix hack.  // fps is not updated cycle by cycle
		float normilisedGameSpeed = (60.0f/Gdx.graphics.getFramesPerSecond());

		int goFastSpeed = (int) (5 * normilisedGameSpeed *camera.zoom);

		if (!camera.isPotate()) {
			potateMoveCamera(normilisedGameSpeed, goFastSpeed);

			if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
				camera.zoom *=1+0.01*normilisedGameSpeed;
			}
		}

	}
}

