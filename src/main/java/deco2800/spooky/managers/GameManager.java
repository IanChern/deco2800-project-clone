package deco2800.spooky.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.spooky.entities.Character;
import deco2800.spooky.networking.Lobby;
import deco2800.spooky.renderers.PotateCamera;
import deco2800.spooky.worlds.AbstractWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
	//debug values stored here
	private int entitiesRendered;
	private int entitiesCount;
	private int tilesRendered;
	private int tilesCount;

	private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

	private static GameManager instance = null;

	// The list of all instantiated managers classes.
	private List<AbstractManager> managers = new ArrayList<>();

	// The game world currently being played on.
	private AbstractWorld gameWorld;

	// The camera being used by the Game Screen to project the game world.
	private PotateCamera camera;

	// The stage the game world is being rendered on to
	private Stage stage;

	// The UI skin being used by the game for libGDX elements
	private Skin skin;

	//The lobby contains players info in the multiplayer mode.
	private Lobby lobby;

	//debugging mode
	private boolean debugMode = false;

	/**
	 * Whether or not we render info over the tiles.
	 */
	// Whether or not we render the movement path for Players
	private boolean showCoords = false;
	
	// The game screen for a game that's currently running
	private boolean showPath = false;

	/**
	 * Whether or not we render info over the entities
	 */
	private boolean showCoordsEntity = false;

	/**
	 * Returns an instance of the GM
	 *
	 * @return GameManager
	 */
	public static GameManager get() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	/**
	 * Private constructor to inforce use of get()
	 */
	private GameManager() {

	}

	/**
	 * Add a manager to the current instance, making a new instance if none
	 * exist
	 *
	 * @param manager
	 */
	public static void addManagerToInstance(AbstractManager manager) {
		get().addManager(manager);
	}

	/**
	 * Adds a manager component to the GM
	 *
	 * @param manager
	 */
	public void addManager(AbstractManager manager) {
		managers.add(manager);
	}

	/**
	 * Retrieves a manager from the list.
	 * If the manager does not exist one will be created, added to the list and returned
	 *
	 * @param type The class type (ie SoundManager.class)
	 * @return A Manager component of the requested type
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractManager> T getManager(Class<T> type) {
		/* Check if the manager exists */
		for (AbstractManager m : managers) {
			if (m.getClass() == type) {
				return (T) m;
			}
		}
		logger.info("creating new manager instance");
		/* Otherwise create one */
		AbstractManager newInstance;
		try {
			Constructor<?> ctor = type.getConstructor();
			newInstance = (AbstractManager) ctor.newInstance();
			this.addManager(newInstance);
			return (T) newInstance;
		} catch (Exception e) {
			// Gotta catch 'em all
			logger.error("Exception occurred when adding Manager.");
		}

		logger.warn("GameManager.getManager returned null! It shouldn't have!");
		return null;
	}

	/**
	 * Retrieve a manager from the current GameManager instance, making a new
	 * instance when none are available.
	 *
	 * @param type The class type (ie SoundManager.class)
	 * @return A Manager component of the requested type
	 */
	public static <T extends AbstractManager> T getManagerFromInstance(Class<T> type) {
		return get().getManager(type);
	}

	
	/* ------------------------------------------------------------------------
	 * 				GETTERS AND SETTERS BELOW THIS COMMENT.
	 * ------------------------------------------------------------------------ */

	/**Set debugMode
	 * @param debugMode
	 */
	public void setDebugMode(boolean mode) { this.debugMode = mode; }

	/**Get debugMode
	 * @return debugMode
	 */
	public boolean getDebugMode() { return this.debugMode; }

	/**Get show coords
	 * @return coords show
	 */
	public boolean getShowCoords() { return this.showCoords; }

	/**Get show coords
	 * @param coords show
	 */
	public void setShowCoords(boolean coords) { this.showCoords = coords; }

	/**Get show path
	 * @return path show
	 */
	public boolean getShowPath() { return this.showPath; }

	/**Set show path
	 * @param path show
	 */
	public void setShowPath(boolean path) { this.showPath = path; }

	/**Get show coords entity
	 * @return showcoords entity
	 */
	public boolean getShowCoordsEntity() { return this.showCoordsEntity; }

	/** Set entities rendered to new amount
	 * @param entitiesRendered the new amount
	 */
	public void setShowCoordsEntity(boolean entity) { this.showCoordsEntity = entity; }

	/**Get entities rendered count
	 * @return entities rendered count
	 */
	public int getEntitiesRendered() {
		return this.entitiesRendered;
	}

	/** Set entities rendered to new amount
	 * @param entitiesRendered the new amount
	 */
	public void setEntitiesRendered(int entitiesRendered) {
		this.entitiesRendered = entitiesRendered;
	}
	/**Get number of entities
	 * @return entities count
	 */
	public int getEntitiesCount() {
		return this.entitiesCount;
	}

	/** Set entities count to new amount
	 * @param entitiesCount the new amount
	 */
	public void setEntitiesCount(int entitiesCount) {
		this.entitiesCount = entitiesCount;
	}

	/**Get tiles rendered count
	 * @return tiles rendered count
	 */
	public int getTilesRendered() {
		return this.tilesRendered;
	}

	/** Set tiles rendered to new amount
	 * @param tilesRendered the new amount
	 */
	public void setTilesRendered(int tilesRendered) {
		this.tilesRendered = tilesRendered;
	}

	/**Get number of tiles
	 * @return tiles count
	 */
	public int getTilesCount() {
		return this.tilesCount;
	}

	/** Set tiles count to new amount
	 * @param tilesCount the new amount
	 */
	public void setTilesCount(int tilesCount) {
		this.tilesCount = tilesCount;
	}
	
	/**
	 * Sets the current game world
	 *
	 * @param world
	 */
	public void setWorld(AbstractWorld world) {
		this.gameWorld = world;
	}

	/**
	 * Gets the current game world
	 *
	 * @return
	 */
	public AbstractWorld getWorld() {
		return gameWorld;
	}


	public void setCamera(PotateCamera camera) {
		this.camera = camera;
	}

	/**
	 * @return current game's stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage - the current game's stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return current game's skin
	 */
	public Skin getSkin() {
		return skin;
	}

	/**
	 * @param skin - the current game's skin
	 */
	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public PotateCamera getCamera() {
		return camera;
	}

	public void setLobby(Lobby gameLobby){
		this.lobby = gameLobby;
	}

	public Lobby getLobby(){
		return this.lobby;
	}
	/**
	 * On tick method for ticking managers with the TickableManager interface
	 *
	 * @param i
	 */
	public void onTick(long i) {
		for (AbstractManager m : managers) {
			if (m instanceof TickableManager) {
				((TickableManager) m).onTick(0);
			}
		}
		gameWorld.onTick(0);
	}
	

	/**
	 * @return how many Characters remaining in the world
	 */
	public int charactersRemaining() {
		return(gameWorld.getRemainingPlayerList().size());
	}

	/**
	 * @return true if Traitor died in the world
	 */
	public boolean isTraitorDead() {
		List<Character> remainingCharacter = gameWorld.getRemainingPlayerList();
		logger.info("Remaining players in the game {}", remainingCharacter);
		for (Character thisCharacter : remainingCharacter) {
			if (thisCharacter.isTraitor()) {
				logger.warn("This is the traitor {}", thisCharacter);
				return false;
			}
		}
		return true;
	}

	/**
	 * Make the character to be died, and decide the status
	 * @param diedCharacter
	 */
	public void characterDied(Character diedCharacter) {
		GameManager.get().getWorld().removeCharacter(diedCharacter);

		if (charactersRemaining() == 1 && !isTraitorDead() &&
				gameWorld.getRemainingPlayerList().get(0).isPlayerControlled()) {
			youWin();
		}

		if (isTraitorDead()) {
			GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Killing", "A traitor has been killed");
			if(diedCharacter.isTraitor()) {
				youLose();
			} else {
				youWin();
			}
			
		} else {
			GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Killing", "A survivor has been killed");
		}
	}
	private static void youLose(){
		getManagerFromInstance(StatusManager.class).endTheGame(true);
		GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Major", "You win ", 0);
		GameManager.get().getManager(ClientManager.class).setGameEnd();
	}

	private static void youWin(){
		getManagerFromInstance(StatusManager.class).endTheGame(false);
		GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Major", "You lose ", 0);
		GameManager.get().getManager(ClientManager.class).setGameEnd();
	}
}