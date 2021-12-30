package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game saving
 * file reads from being completed during rendering.
 * <p>
 * With this in mind don't load textures you're not going to use.
 * Textures that are not used should probably (at some point) be removed
 * from the list and then read from disk when needed again using some type
 * of reference counting
 *
 * @Author Tim Hadwen
 */
public class TextureManager implements AbstractManager {
	
	/**
	 * The width of the tile to use then positioning the tile.
	 */
	public static final int TILE_WIDTH = 320;
	
	/**
	 * The height of the tile to use when positioning the tile.7
	 */
	public static final int TILE_HEIGHT = 278;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);


    /**
     * A HashMap of all textures with string keys
     */
    private Map<String, Texture> textureMap = new HashMap<>();

    private Map<String, String> intendedTexture =  new HashMap<>();

    private Map<String, Texture> explosionTexture = new HashMap<>();
    /**
     * Constructor
     * Currently loads up all the textures but probably shouldn't/doesn't
     * need to.
     */
    public TextureManager() {
            String chrisLeft = "resources/characters/christopher/384x384/neutral_left.png";
            String chrisRight = "resources/characters/christopher/384x384/neutral_right.png";
            String janeLeft = "resources/characters/Jane/384x384/neutral_left.png";
            String janeRight = "resources/characters/Jane/384x384/neutral_right.png";
            String damLeft = "resources/characters/damien/384x384/neutral_left.png";
            String damRight = "resources/characters/damien/384x384/neutral_right.png";
            String katLeft = "resources/characters/katie/384x384/neutral_left.png";
            String katRight = "resources/characters/katie/384x384/neutral_right.png";
            String larLeft = "resources/characters/larry/384x384/neutral_left.png";
            String larRight = "resources/characters/larry/384x384/neutral_right.png";
            String titusLeft = "resources/characters/titus/384x384/neutral_left.png";
            String titusRight = "resources/characters/titus/384x384/neutral_right.png";

            intendedTexture.put("loading5", "resources/loading/Loading_5.png");
            intendedTexture.put("loading25", "resources/loading/Loading_25.png");
            intendedTexture.put("loading50", "resources/loading/Loading_50.png");
            intendedTexture.put("loading75", "resources/loading/Loading_75.png");
            intendedTexture.put("loading100", "resources/loading/Loading_100.png");

            intendedTexture.put("background", "resources/ui/game/game-start-evening.png");
            intendedTexture.put("backgroundLobby", "resources/ui/lobby-ui/background.png");
            intendedTexture.put("cardLobby", "resources/ui/lobby-ui/card.png");
            intendedTexture.put("iconLobby", "resources/ui/lobby-ui/lobby-icon.png");
            intendedTexture.put("mummyLobby", "resources/ui/lobby-ui/mummy.png");
            intendedTexture.put("joinedLobby", "resources/ui/lobby-ui/player-joined.png");
            intendedTexture.put("readyLobby", "resources/ui/lobby-ui/ready.png");


            intendedTexture.put("spacman_ded", "resources/spacman_ded.png");
            intendedTexture.put("spacman_blue", "resources/spacman_blue.png");
            intendedTexture.put("spatman_blue", "resources/spatman_blue.png");
            intendedTexture.put("spatman_green", "resources/spatman_green.png");
            intendedTexture.put("grass_0", "Floor and Traps/Alternative floors and traps1.png");
            intendedTexture.put("grass_1", "Floor and Traps/Alternative floors and traps3.png");
            intendedTexture.put("grass_2", "Floor and Traps/Alternative floors and traps4.png");
            intendedTexture.put("grass_3", "resources/grass_4.png");
            intendedTexture.put("darkness", "resources/Darkness.png");
            intendedTexture.put("streetlight", "resources/streetlight.png");

            intendedTexture.put("ark_still","resources/shrines/base_files/ark.png");
            intendedTexture.put("chalice_still","resources/shrines/base_files/chalice.png");
            intendedTexture.put("cross_still","resources/shrines/base_files/cross.png");
            intendedTexture.put("ankh_still","resources/shrines/base_files/fowl.png");

            // pot texture manager
            intendedTexture.put("blue_pot_still","resources/map_extras/pots/large_pot/base_files/blue_still.png");
            intendedTexture.put("blue_pot_break","resources/map_extras/pots/large_pot/base_files/blue_break.png");
            intendedTexture.put("red_pot_still","resources/map_extras/pots/large_pot/base_files/red_still.png");
            intendedTexture.put("red_pot_break","resources/map_extras/pots/large_pot/base_files/red_break.png");
            intendedTexture.put("yellow_pot_still","resources/map_extras/pots/large_pot/base_files/yellow_still.png");
            intendedTexture.put("yellow_pot_break","resources/map_extras/pots/large_pot/base_files/yellow_break.png");

            intendedTexture.put("selection", "resources/blue_selection.png");
            intendedTexture.put("path", "resources/blue_selection.png");

            // add character textures
            intendedTexture.put("assassin", "resources/assassin-1.png");
            intendedTexture.put("hunter", "resources/hunter.png");
            intendedTexture.put("ninja", "resources/ninja-1.png");
            intendedTexture.put("robot", "resources/robot-1.png");
            intendedTexture.put("tank", "resources/tank_1.png");

            intendedTexture.put("chris_standing", "resources/characters/christopher/384x384/neutral_down.png");
            intendedTexture.put("chris_neutral_up", "resources/characters/christopher/384x384/neutral_up.png");
            intendedTexture.put("chris_neutral_down", "resources/characters/christopher/384x384/neutral_down.png");
            intendedTexture.put("chris_neutral_left", chrisLeft);
            intendedTexture.put("chris_neutral_SouthW", chrisLeft);
            intendedTexture.put("chris_neutral_NorthW", chrisLeft);
            intendedTexture.put("chris_neutral_NorthE", chrisRight);
            intendedTexture.put("chris_neutral_SouthE", chrisRight);
            intendedTexture.put("chris_neutral_right", chrisRight);

            intendedTexture.put("chris_walking_up1", "resources/characters/christopher/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("chris_walking_up2", "resources/characters/christopher/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("chris_walking_down1", "resources/characters/christopher/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("chris_walking_down2", "resources/characters/christopher/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("chris_walking_left1", "resources/characters/christopher/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("chris_walking_left2", "resources/characters/christopher/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("chris_walking_right1", "resources/characters/christopher/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("chris_walking_right2", "resources/characters/christopher/384x384/walking_right_frames/walking_right2.png");

            intendedTexture.put("jane_standing", "resources/characters/Jane/384x384/neutral_down.png");
            intendedTexture.put("jane_neutral_up", "resources/characters/Jane/384x384/neutral_up.png");
            intendedTexture.put("jane_neutral_down", "resources/characters/Jane/384x384/neutral_down.png");
            intendedTexture.put("jane_neutral_SouthW", janeLeft);
            intendedTexture.put("jane_neutral_NorthW", janeLeft);
            intendedTexture.put("jane_neutral_left", janeLeft);
            intendedTexture.put("jane_neutral_right", janeRight);
            intendedTexture.put("jane_neutral_NorthE", janeRight);
            intendedTexture.put("jane_neutral_SouthE", janeRight);
            intendedTexture.put("jane_walking_up1", "resources/characters/Jane/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("jane_walking_up2", "resources/characters/Jane/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("jane_walking_down1", "resources/characters/Jane/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("jane_walking_down2", "resources/characters/Jane/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("jane_walking_left1", "resources/characters/Jane/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("jane_walking_left2", "resources/characters/Jane/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("jane_walking_right1", "resources/characters/Jane/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("jane_walking_right2", "resources/characters/Jane/384x384/walking_right_frames/walking_right2.png");

            intendedTexture.put("damien_standing", "resources/characters/damien/384x384/neutral_down.png");
            intendedTexture.put("damien_neutral_up", "resources/characters/damien/384x384/neutral_up.png");
            intendedTexture.put("damien_neutral_down", "resources/characters/damien/384x384/neutral_down.png");
            intendedTexture.put("damien_neutral_SouthW", damLeft);
            intendedTexture.put("damien_neutral_NorthW", damLeft);
            intendedTexture.put("damien_neutral_left", damLeft);
            intendedTexture.put("damien_neutral_right", damRight);
            intendedTexture.put("damien_neutral_NorthE", damRight);
            intendedTexture.put("damien_neutral_SouthE", damRight);
            intendedTexture.put("damien_walking_up1", "resources/characters/damien/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("damien_walking_up2", "resources/characters/damien/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("damien_walking_down1", "resources/characters/damien/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("damien_walking_down2", "resources/characters/damien/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("damien_walking_left1", "resources/characters/damien/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("damien_walking_left2", "resources/characters/damien/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("damien_walking_right1", "resources/characters/damien/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("damien_walking_right2", "resources/characters/damien/384x384/walking_right_frames/walking_right2.png");

            intendedTexture.put("katie_standing", "resources/characters/katie/384x384/neutral_down.png");
            intendedTexture.put("katie_neutral_up", "resources/characters/katie/384x384/neutral_up.png");
            intendedTexture.put("katie_neutral_down", "resources/characters/katie/384x384/neutral_down.png");
            intendedTexture.put("katie_neutral_SouthW", katLeft);
            intendedTexture.put("katie_neutral_NorthW", katLeft);
            intendedTexture.put("katie_neutral_left", katLeft);
            intendedTexture.put("katie_neutral_right", katRight);
            intendedTexture.put("katie_neutral_NorthE",katRight);
            intendedTexture.put("katie_neutral_SouthE", katRight);
            intendedTexture.put("katie_walking_up1", "resources/characters/katie/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("katie_walking_up2", "resources/characters/katie/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("katie_walking_down1", "resources/characters/katie/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("katie_walking_down2", "resources/characters/katie/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("katie_walking_left1", "resources/characters/katie/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("katie_walking_left2", "resources/characters/katie/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("katie_walking_right1", "resources/characters/katie/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("katie_walking_right2", "resources/characters/katie/384x384/walking_right_frames/walking_right2.png");

            intendedTexture.put("larry_neutral_down", "resources/characters/larry/384x384/neutral_down.png");
            intendedTexture.put("larry_standing", "resources/characters/larry/384x384/neutral_down.png");
            intendedTexture.put("larry_neutral_up", "resources/characters/larry/384x384/neutral_up.png");
            intendedTexture.put("larry_neutral_SouthW", larLeft);
            intendedTexture.put("larry_neutral_NorthW", larLeft);
            intendedTexture.put("larry_neutral_left", larLeft);
            intendedTexture.put("larry_neutral_right", larRight);
            intendedTexture.put("larry_neutral_NorthE", larRight);
            intendedTexture.put("larry_neutral_SouthE", larRight);
            intendedTexture.put("larry_walking_up1", "resources/characters/larry/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("larry_walking_up2", "resources/characters/larry/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("larry_walking_down1", "resources/characters/larry/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("larry_walking_down2", "resources/characters/larry/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("larry_walking_left1", "resources/characters/larry/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("larry_walking_left2", "resources/characters/larry/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("larry_walking_right1", "resources/characters/larry/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("larry_walking_right2", "resources/characters/larry/384x384/walking_right_frames/walking_right2.png");


            intendedTexture.put("titus_neutral_down", "resources/characters/titus/384x384/neutral_down.png");
            intendedTexture.put("titus_standing", "resources/characters/titus/384x384/neutral_down.png");
            intendedTexture.put("titus_neutral_up", "resources/characters/titus/384x384/neutral_up.png");
            intendedTexture.put("titus_neutral_SouthW", titusLeft);
            intendedTexture.put("titus_neutral_NorthW", titusLeft);
            intendedTexture.put("titus_neutral_left", titusLeft);
            intendedTexture.put("titus_neutral_right", titusRight);
            intendedTexture.put("titus_neutral_NorthE", titusRight);
            intendedTexture.put("titus_neutral_SouthE", titusRight);
            intendedTexture.put("titus_walking_up1", "resources/characters/titus/384x384/walking_up_frames/walking_up1.png");
            intendedTexture.put("titus_walking_up2", "resources/characters/titus/384x384/walking_up_frames/walking_up2.png");
            intendedTexture.put("titus_walking_down1", "resources/characters/titus/384x384/walking_down_frames/walking_down1.png");
            intendedTexture.put("titus_walking_down2", "resources/characters/titus/384x384/walking_down_frames/walking_down2.png");
            intendedTexture.put("titus_walking_left1", "resources/characters/titus/384x384/walking_left_frames/walking_left1.png");
            intendedTexture.put("titus_walking_left2", "resources/characters/titus/384x384/walking_left_frames/walking_left2.png");
            intendedTexture.put("titus_walking_right1", "resources/characters/titus/384x384/walking_right_frames/walking_right1.png");
            intendedTexture.put("titus_walking_right2", "resources/characters/titus/384x384/walking_right_frames/walking_right2.png");

            // character hover dark version
            intendedTexture.put("damien-dark", "resources/characters/dark-character/damien-dark.png");
            intendedTexture.put("christopher-dark", "resources/characters/dark-character/christopher-dark.png");
            intendedTexture.put("jane-dark", "resources/characters/dark-character/jane-dark.png");
            intendedTexture.put("katie-dark", "resources/characters/dark-character/katie-dark.png");
            intendedTexture.put("larry-dark", "resources/characters/dark-character/larry-dark.png");
            intendedTexture.put("titus-dark", "resources/characters/dark-character/titus-dark.png");


            intendedTexture.put("buildingB", "resources/building3x2.png");

            intendedTexture.put("buildingA", "resources/buildingA.png");

            intendedTexture.put("fenceN-S", "resources/fence N-S.png");


            intendedTexture.put("fenceNE-SW", "resources/fence NE-SW.png");
            intendedTexture.put("fenceNW-SE", "resources/fence NW-SE.png");
            intendedTexture.put("fenceNE-S", "resources/fence NE-S.png");
            intendedTexture.put("fenceNE-SE", "resources/fence NE-SE.png");
            intendedTexture.put("fenceN-SE", "resources/fence N-SE.png");
            intendedTexture.put("fenceN-SW", "resources/fence N-SW.png");
            intendedTexture.put("fenceNW-NE", "resources/fence NW-NE.png");
            intendedTexture.put("fenceSE-SW", "resources/fence SE-SW.png");
            intendedTexture.put("fenceNW-S", "resources/fence NW-S.png");

            intendedTexture.put("skate", "resources/skate.png");

            intendedTexture.put("rock", "resources/rocks.png");

            //Items
            intendedTexture.put("medicine", "resources/medicine.png");

            //Dagger and axe
            intendedTexture.put("daggerItem", "resources/dagger.png");
            intendedTexture.put("axeItem", "resources/axe.png");

            // add heart image
            intendedTexture.put("heart1", "resources/health/heart1.png");
            intendedTexture.put("heart2", "resources/health/heart2.png");
            intendedTexture.put("heart3", "resources/health/heart3.png");

            //sound
            intendedTexture.put("soundOption","resources/sound_image/sound_symbol.png");


            intendedTexture.put("wall1", "resources/walls/final/walls/topRight.png");
            intendedTexture.put("wall2", "resources/walls/final/walls/middleRight.png");
            intendedTexture.put("wall3", "resources/walls/final/walls/bottomRight.png");
            intendedTexture.put("wall4", "resources/walls/final/walls/bottomLeft.png");
            intendedTexture.put("wall5", "resources/walls/final/walls/middleLeft.png");
            intendedTexture.put("wall6", "resources/walls/final/walls/topLeft.png");
            intendedTexture.put("wall7", "resources/walls/final/corners/topCorner.png");
            intendedTexture.put("wall8", "resources/walls/final/corners/topRightCorner.png");
            intendedTexture.put("wall9", "resources/walls/final/corners/topLeftCorner.png");
            intendedTexture.put("wall10", "resources/walls/final/corners/bottomCorner.png");
            intendedTexture.put("wall11", "resources/walls/final/corners/bottomLeftCorner.png");
            intendedTexture.put("wall12", "resources/walls/final/corners/bottomRightCorner.png");
            intendedTexture.put("door1", "resources/walls/final/doors/topRightDoor.png");
            intendedTexture.put("door2", "resources/walls/final/doors/middleRightDoor.png");
            intendedTexture.put("door3", "resources/walls/final/doors/bottomRightDoor.png");
            intendedTexture.put("door4", "resources/walls/final/doors/bottomLeftDoor.png");
            intendedTexture.put("door5", "resources/walls/final/doors/middleLeftDoor.png");
            intendedTexture.put("door6", "resources/walls/final/doors/topLeftDoor.png");

            intendedTexture.put("pitfall", "resources/Floor and Traps/Alternative floors and traps8.png");
            intendedTexture.put("quicksand", "resources/Floor and Traps/Alternative floors and traps10.png");

            //ui components
            intendedTexture.put("singleButton", "resources/ui/game/single.png");
            intendedTexture.put("serverButton", "resources/ui/game/server.png");
            intendedTexture.put("single-dark", "resources/ui/game/single-dark.png");
            intendedTexture.put("single-small", "resources/ui/game/single-small.png");
            intendedTexture.put("server-dark", "resources/ui/game/server-dark.png");
            intendedTexture.put("server-small", "resources/ui/game/server-small.png");
            intendedTexture.put("connectButton", "resources/ui/game/connect.png");
            intendedTexture.put("connect-dark", "resources/ui/game/connect-dark.png");
            intendedTexture.put("connect-small", "resources/ui/game/connect-small.png");
            intendedTexture.put("createButton", "resources/ui/game/create.png");
            intendedTexture.put("create-dark", "resources/ui/game/create-dark.png");
            intendedTexture.put("create-small", "resources/ui/game/create-small.png");
            intendedTexture.put("overBackground", "resources/ui/game/game-over-pyramid.png");
            intendedTexture.put("startBackground", "resources/ui/game/game-start-evening.png");
            intendedTexture.put("backButton", "resources/ui/character_selection/back.png");
            intendedTexture.put("back-small", "resources/ui/character_selection/back-small.png");
            intendedTexture.put("startButton", "resources/ui/character_selection/start.png");
            intendedTexture.put("start-small", "resources/ui/character_selection/start-small.png");
            intendedTexture.put("start-dark", "resources/ui/character_selection/start-dark.png");
            intendedTexture.put("uiBackground", "resources/ui/game/game-select-night.png");
            intendedTexture.put("p1Label", "resources/ui/lobby-ui/p1.png");
            intendedTexture.put("p2Label", "resources/ui/lobby-ui/p2.png");
            intendedTexture.put("p3Label", "resources/ui/lobby-ui/p3.png");
            intendedTexture.put("p4Label", "resources/ui/lobby-ui/p4.png");
            intendedTexture.put("p5Label", "resources/ui/lobby-ui/p5.png");
            intendedTexture.put("p6Label", "resources/ui/lobby-ui/p6.png");
            intendedTexture.put("blueCard", "resources/ui/character_selection/blue-card.png");
            intendedTexture.put("brownCard", "resources/ui/character_selection/brown-card.png");
            intendedTexture.put("greenCard", "resources/ui/character_selection/green-card.png");
            intendedTexture.put("orangeCard", "resources/ui/character_selection/orange-card.png");
            intendedTexture.put("purpleCard", "resources/ui/character_selection/purple-card.png");
            intendedTexture.put("yellowCard", "resources/ui/character_selection/yellow-card.png");
            intendedTexture.put("uiTitle", "resources/ui/character_selection/choose-character.png");
            intendedTexture.put("chrisName", "resources/ui/character_selection/chris-name.png");
            intendedTexture.put("deanName", "resources/ui/character_selection/dean-name.png");
            intendedTexture.put("janeName", "resources/ui/character_selection/jane-name.png");
            intendedTexture.put("katieName", "resources/ui/character_selection/katie-name.png");
            intendedTexture.put("larryName", "resources/ui/character_selection/larry-name.png");
            intendedTexture.put("titusName", "resources/ui/character_selection/titus-name.png");
            intendedTexture.put("p1", "resources/ui/character_selection/p1.png");
            intendedTexture.put("p2", "resources/ui/character_selection/p2.png");
            intendedTexture.put("p3", "resources/ui/character_selection/p3.png");
            intendedTexture.put("p4", "resources/ui/character_selection/p4.png");
            intendedTexture.put("p5", "resources/ui/character_selection/p5.png");
            intendedTexture.put("p6", "resources/ui/character_selection/p6.png");
            intendedTexture.put("bgOfLabel", "resources/ui/game/pixil-frame-0-4.png");
            intendedTexture.put("mainTitle","resources/ui/game/title.png");


            intendedTexture.put("boxIns","resources/ui/instruction/big_box.png");
            intendedTexture.put("controlIns","resources/ui/instruction/controls.png");
            intendedTexture.put("orIns","resources/ui/instruction/or.png");
            intendedTexture.put("roleIns","resources/ui/instruction/role_words.png");
            intendedTexture.put("smallIns","resources/ui/instruction/small_boxes.png");
            intendedTexture.put("storyIns","resources/ui/instruction/story_words.png");
            intendedTexture.put("titleIns","resources/ui/instruction/title.png");
            intendedTexture.put("bgIns","resources/ui/instruction/instruction_background.png");
            intendedTexture.put("nextButton","resources/ui/instruction/next.png");
            intendedTexture.put("next-small","resources/ui/instruction/next-small.png");
            intendedTexture.put("next-dark","resources/ui/instruction/next-dark.png");
            intendedTexture.put("back-dark","resources/ui/instruction/back-dark.png");

            intendedTexture.put("medkit", "resources/items/Health1.png");
            intendedTexture.put("coin1", "resources/items/coin1.png");

            intendedTexture.put("blue-rupee0", "resources/items/BlueRupee/pixil-frame-00.png");
            intendedTexture.put("blue-rupee2", "resources/items/BlueRupee/pixil-frame-02.png");
            intendedTexture.put("blue-rupee4", "resources/items/BlueRupee/pixil-frame-04.png");
            intendedTexture.put("blue-rupee6", "resources/items/BlueRupee/pixil-frame-06.png");
            intendedTexture.put("blue-rupee8", "resources/items/BlueRupee/pixil-frame-08.png");
            intendedTexture.put("blue-rupee10", "resources/items/BlueRupee/pixil-frame-10.png");
            intendedTexture.put("blue-rupee12", "resources/items/BlueRupee/pixil-frame-12.png");
            intendedTexture.put("blue-rupee14", "resources/items/BlueRupee/pixil-frame-14.png");
            intendedTexture.put("blue-rupee16", "resources/items/BlueRupee/pixil-frame-16.png");
            intendedTexture.put("blue-rupee18", "resources/items/BlueRupee/pixil-frame-18.png");
            intendedTexture.put("blue-rupee1", "resources/items/BlueRupee/pixil-frame-01.png");
            intendedTexture.put("blue-rupee3", "resources/items/BlueRupee/pixil-frame-03.png");
            intendedTexture.put("blue-rupee5", "resources/items/BlueRupee/pixil-frame-05.png");
            intendedTexture.put("blue-rupee7", "resources/items/BlueRupee/pixil-frame-07.png");
            intendedTexture.put("blue-rupee9", "resources/items/BlueRupee/pixil-frame-09.png");
            intendedTexture.put("blue-rupee11", "resources/items/BlueRupee/pixil-frame-11.png");
            intendedTexture.put("blue-rupee13", "resources/items/BlueRupee/pixil-frame-13.png");
            intendedTexture.put("blue-rupee15", "resources/items/BlueRupee/pixil-frame-15.png");
            intendedTexture.put("blue-rupee17", "resources/items/BlueRupee/pixil-frame-17.png");

            intendedTexture.put("yellow-rupee0", "resources/items/YellowRupee/pixil-frame-0.png");
            intendedTexture.put("yellow-rupee2", "resources/items/YellowRupee/pixil-frame-2.png");
            intendedTexture.put("yellow-rupee4", "resources/items/YellowRupee/pixil-frame-4.png");
            intendedTexture.put("yellow-rupee6", "resources/items/YellowRupee/pixil-frame-6.png");
            intendedTexture.put("yellow-rupee8", "resources/items/YellowRupee/pixil-frame-8.png");
            intendedTexture.put("yellow-rupee10", "resources/items/YellowRupee/pixil-frame-10.png");
            intendedTexture.put("yellow-rupee12", "resources/items/YellowRupee/pixil-frame-12.png");
            intendedTexture.put("yellow-rupee14", "resources/items/YellowRupee/pixil-frame-14.png");
            intendedTexture.put("yellow-rupee16", "resources/items/YellowRupee/pixil-frame-16.png");
            intendedTexture.put("yellow-rupee18", "resources/items/YellowRupee/pixil-frame-18.png");
            intendedTexture.put("yellow-rupee1", "resources/items/YellowRupee/pixil-frame-1.png");
            intendedTexture.put("yellow-rupee3", "resources/items/YellowRupee/pixil-frame-3.png");
            intendedTexture.put("yellow-rupee5", "resources/items/YellowRupee/pixil-frame-5.png");
            intendedTexture.put("yellow-rupee7", "resources/items/YellowRupee/pixil-frame-7.png");
            intendedTexture.put("yellow-rupee9", "resources/items/YellowRupee/pixil-frame-9.png");
            intendedTexture.put("yellow-rupee11", "resources/items/YellowRupee/pixil-frame-11.png");
            intendedTexture.put("yellow-rupee13", "resources/items/YellowRupee/pixil-frame-13.png");
            intendedTexture.put("yellow-rupee15", "resources/items/YellowRupee/pixil-frame-15.png");
            intendedTexture.put("yellow-rupee17", "resources/items/YellowRupee/pixil-frame-17.png");


            intendedTexture.put("gameover_background", "resources/ui/game_over/background.png");
            intendedTexture.put("gameover_character_frame", "resources/ui/game_over/character-frame.png");
            intendedTexture.put("gameover_game_over_icon", "resources/ui/game_over/game-over-icon.png");
            intendedTexture.put("gameover_other_player_frame", "resources/ui/game_over/other-player-frame.png");
            intendedTexture.put("gameover_performance_icon", "resources/ui/game_over/performance-icon.png");
            intendedTexture.put("gameover_victory_icon", "resources/ui/game_over/victory-icon.png");
            intendedTexture.put("gameover_lose_icon", "resources/ui/game_over/lose-icon.png");
            intendedTexture.put("gameover_win_icon", "resources/ui/game_over/win-icon.png");

            intendedTexture.put("game_pause_button", "resources/ui/game/pausebtn.png");
            intendedTexture.put("game_pause_button-dark", "resources/ui/game/pausebtn-dark.png");
            intendedTexture.put("pauseMenuBackground", "resources/pauseMenu/pauseMenuBackground" +
                    ".png");
            intendedTexture.put("resume", "resources/pauseMenu/resume.png");
            intendedTexture.put("resume-dark", "resources/pauseMenu/resume-dark.png");
            intendedTexture.put("volumeUp", "resources/pauseMenu/volumeUp.png");
            intendedTexture.put("volumeDown", "resources/pauseMenu/volumeDown.png");
            intendedTexture.put("newGameBtn", "resources/pauseMenu/newGameBtn.png");
            intendedTexture.put("newGameBtn-dark", "resources/pauseMenu/newGameBtn-dark.png");
            intendedTexture.put("mainMenuBtn", "resources/pauseMenu/mainMenuBtn.png");
            intendedTexture.put("mainMenuBtn-dark", "resources/pauseMenu/mainMenuBtn-dark.png");
            intendedTexture.put("music", "resources/pauseMenu/music.png");


            intendedTexture.put("green-rupee0", "resources/items/GreenRupee/pixil-frame-0.png");
            intendedTexture.put("green-rupee2", "resources/items/GreenRupee/pixil-frame-2.png");
            intendedTexture.put("green-rupee4", "resources/items/GreenRupee/pixil-frame-4.png");
            intendedTexture.put("green-rupee6", "resources/items/GreenRupee/pixil-frame-6.png");
            intendedTexture.put("green-rupee8", "resources/items/GreenRupee/pixil-frame-8.png");
            intendedTexture.put("green-rupee10", "resources/items/GreenRupee/pixil-frame-10.png");
            intendedTexture.put("green-rupee12", "resources/items/GreenRupee/pixil-frame-12.png");
            intendedTexture.put("green-rupee14", "resources/items/GreenRupee/pixil-frame-14.png");
            intendedTexture.put("green-rupee16", "resources/items/GreenRupee/pixil-frame-16.png");
            intendedTexture.put("green-rupee18", "resources/items/GreenRupee/pixil-frame-18.png");
            intendedTexture.put("green-rupee1", "resources/items/GreenRupee/pixil-frame-1.png");
            intendedTexture.put("green-rupee3", "resources/items/GreenRupee/pixil-frame-3.png");
            intendedTexture.put("green-rupee5", "resources/items/GreenRupee/pixil-frame-5.png");
            intendedTexture.put("green-rupee7", "resources/items/GreenRupee/pixil-frame-7.png");
            intendedTexture.put("green-rupee9", "resources/items/GreenRupee/pixil-frame-9.png");
            intendedTexture.put("green-rupee11", "resources/items/GreenRupee/pixil-frame-11.png");
            intendedTexture.put("green-rupee13", "resources/items/GreenRupee/pixil-frame-13.png");
            intendedTexture.put("green-rupee15", "resources/items/GreenRupee/pixil-frame-15.png");
            intendedTexture.put("green-rupee17", "resources/items/GreenRupee/pixil-frame-17.png");

            intendedTexture.put("purple-rupee0", "resources/items/PurpleRupee/pixil-frame-0.png");
            intendedTexture.put("purple-rupee2", "resources/items/PurpleRupee/pixil-frame-2.png");
            intendedTexture.put("purple-rupee4", "resources/items/PurpleRupee/pixil-frame-4.png");
            intendedTexture.put("purple-rupee6", "resources/items/PurpleRupee/pixil-frame-6.png");
            intendedTexture.put("purple-rupee8", "resources/items/PurpleRupee/pixil-frame-8.png");
            intendedTexture.put("purple-rupee10", "resources/items/PurpleRupee/pixil-frame-10.png");
            intendedTexture.put("purple-rupee12", "resources/items/PurpleRupee/pixil-frame-12.png");
            intendedTexture.put("purple-rupee14", "resources/items/PurpleRupee/pixil-frame-14.png");
            intendedTexture.put("purple-rupee16", "resources/items/PurpleRupee/pixil-frame-16.png");
            intendedTexture.put("purple-rupee18", "resources/items/PurpleRupee/pixil-frame-18.png");
            intendedTexture.put("purple-rupee1", "resources/items/PurpleRupee/pixil-frame-1.png");
            intendedTexture.put("purple-rupee3", "resources/items/PurpleRupee/pixil-frame-3.png");
            intendedTexture.put("purple-rupee5", "resources/items/PurpleRupee/pixil-frame-5.png");
            intendedTexture.put("purple-rupee7", "resources/items/PurpleRupee/pixil-frame-7.png");
            intendedTexture.put("purple-rupee9", "resources/items/PurpleRupee/pixil-frame-9.png");
            intendedTexture.put("purple-rupee11", "resources/items/PurpleRupee/pixil-frame-11.png");
            intendedTexture.put("purple-rupee13", "resources/items/PurpleRupee/pixil-frame-13.png");
            intendedTexture.put("purple-rupee15", "resources/items/PurpleRupee/pixil-frame-15.png");
            intendedTexture.put("purple-rupee17", "resources/items/PurpleRupee/pixil-frame-17.png");

            intendedTexture.put("Healthpack-1", "resources/items/HealthPack/Healthpack-1.png");
            intendedTexture.put("Healthpack-2", "resources/items/HealthPack/Healthpack-2.png");
            intendedTexture.put("Healthpack-3", "resources/items/HealthPack/Healthpack-3.png");
            intendedTexture.put("Healthpack-4", "resources/items/HealthPack/Healthpack-4.png");
            intendedTexture.put("Healthpack-5", "resources/items/HealthPack/Healthpack-5.png");
            intendedTexture.put("Healthpack-6", "resources/items/HealthPack/Healthpack-6.png");
            intendedTexture.put("Healthpack-7", "resources/items/HealthPack/Healthpack-7.png");
            intendedTexture.put("Healthpack-8", "resources/items/HealthPack/Healthpack-8.png");
            intendedTexture.put("Healthpack-9", "resources/items/HealthPack/Healthpack-9.png");
            intendedTexture.put("Healthpack-10", "resources/items/HealthPack/Healthpack-10.png");
            intendedTexture.put("Healthpack-11", "resources/items/HealthPack/Healthpack-11.png");
            intendedTexture.put("Healthpack-12", "resources/items/HealthPack/Healthpack-12.png");
            intendedTexture.put("Healthpack-13", "resources/items/HealthPack/Healthpack-13.png");
            intendedTexture.put("Healthpack-14", "resources/items/HealthPack/Healthpack-14.png");
            intendedTexture.put("Healthpack-15", "resources/items/HealthPack/Healthpack-15.png");
            intendedTexture.put("Healthpack-16", "resources/items/HealthPack/Healthpack-16.png");
            intendedTexture.put("Healthpack-17", "resources/items/HealthPack/Healthpack-17.png");
            intendedTexture.put("Healthpack-18", "resources/items/HealthPack/Healthpack-18.png");
            intendedTexture.put("Healthpack-19", "resources/items/HealthPack/Healthpack-19.png");
            intendedTexture.put("Healthpack-20", "resources/items/HealthPack/Healthpack-20.png");
            intendedTexture.put("Healthpack-21", "resources/items/HealthPack/Healthpack-21.png");
            intendedTexture.put("Healthpack-22", "resources/items/HealthPack/Healthpack-22.png");
            intendedTexture.put("Healthpack-23", "resources/items/HealthPack/Healthpack-23.png");
            intendedTexture.put("Healthpack-24", "resources/items/HealthPack/Healthpack-24.png");
            intendedTexture.put("Healthpack-25", "resources/items/HealthPack/Healthpack-25.png");

            intendedTexture.put("coin-1", "resources/coin/gc 1.png");
            intendedTexture.put("coin-2", "resources/coin/gc2.png");
            intendedTexture.put("coin-3", "resources/coin/gc3.png");
            intendedTexture.put("coin-4", "resources/coin/gc4.png");
            intendedTexture.put("coin-5", "resources/coin/gc5.png");
            intendedTexture.put("coin-6", "resources/coin/gc6.png");
            intendedTexture.put("coin-7", "resources/coin/gc7.png");
            intendedTexture.put("coin-8", "resources/coin/gc8.png");
            intendedTexture.put("coin-9", "resources/coin/gc9.png");
            intendedTexture.put("coin-10", "resources/coin/gc10.png");
            intendedTexture.put("coin-11", "resources/coin/gc11.png");
            intendedTexture.put("coin-12", "resources/coin/gc12.png");
            intendedTexture.put("coin-13", "resources/coin/gc13.png");
            intendedTexture.put("coin-14", "resources/coin/gc14.png");
            intendedTexture.put("coin-15", "resources/coin/gc15.png");
            intendedTexture.put("coin-16", "resources/coin/gc16.png");
            intendedTexture.put("coin-17", "resources/coin/gc17.png");
            intendedTexture.put("coin-18", "resources/coin/gc18.png");
            intendedTexture.put("coin-19", "resources/coin/gc19.png");
            intendedTexture.put("coin-20", "resources/coin/gc20.png");
            intendedTexture.put("coin-21", "resources/coin/gc21.png");
            intendedTexture.put("coin-22", "resources/coin/gc22.png");
            intendedTexture.put("coin-23", "resources/coin/gc23.png");

            intendedTexture.put("closedPit", "resources/Floor and Traps/Alternative floors and traps7.png");
            intendedTexture.put("closedSand", "resources/Floor and Traps/Alternative floors and traps9.png");


            String downLeft = "resources/Mummy/DOWN/Mummy_bottom_left.png";
            String downRight = "resources/Mummy/DOWN/Mummy_bottom_right.png";
            String walkingSE = "resources/Mummy/SE/Mummy_SE_walking.png";
            String walkingSW = "resources/Mummy/SW/Mummy_SW_walking.png";
            String walkingNE = "resources/Mummy/NE/Mummy_NE_walking.png";
            String walkingNW = "resources/Mummy/NW/Mummy_NW_walking.png";
            String neutralUp = "resources/Mummy/TOP/Mummy_top.png";

            intendedTexture.put("mummy_standing","resources/Mummy/DOWN/Mummy_bottom.png");
            intendedTexture.put("mummy_neutral_up",neutralUp);
            intendedTexture.put("mummy_neutral_down",downLeft);
            intendedTexture.put("mummy_neutral_SouthW","resources/Mummy/SW/Mummy_SW.png");
            intendedTexture.put("mummy_neutral_NorthW","resources/Mummy/NW/Mummy_NW.png");
            intendedTexture.put("mummy_neutral_left","resources/Mummy/NW/Mummy_NW.png");
            intendedTexture.put("mummy_neutral_right","resources/Mummy/NE/Mummy_NE.png");
            intendedTexture.put("mummy_netural_NorthE","resources/Mummy/NE/Mummy_NE.png");
            intendedTexture.put("mummy_netural_SouthE","resources/Mummy/SE/Mummy_SE.png");


            intendedTexture.put("mummy_walking_down1",downRight);
            intendedTexture.put("mummy_walking_down2",downLeft);
            intendedTexture.put("mummy_walking_up1","resources/Mummy/TOP/Mummy_top_right.png");
            intendedTexture.put("mummy_walking_up2","resources/Mummy/TOP/Mummy_top_left.png");


            intendedTexture.put("mummy_walking_SouthE1",walkingSE);
            intendedTexture.put("mummy_walking_SouthE2","resources/Mummy/SE/Mummy_SE_walking1.png");



            intendedTexture.put("mummy_walking_SouthW1",walkingSW);
            intendedTexture.put("mummy_walking_SouthW2","resources/Mummy/SW/Mummy_SW_walking1.png");

            intendedTexture.put("mummy_walking_NorthE1",walkingNE);
            intendedTexture.put("mummy_walking_NorthE2","resources/Mummy/NE/Mummy_NE_walking1.png");



            intendedTexture.put("mummy_walking_NorthW1",walkingNW);
            intendedTexture.put("mummy_walking_NorthW2","resources/Mummy/NW/Mummy_NW_walking1.png");

            intendedTexture.put("max-health-low-speed", "resources/ui/character_selection/max-health-low-speed.png");
            intendedTexture.put("mid-health-mid-speed", "resources/ui/character_selection/mid-health-mid-speed.png");
            intendedTexture.put("low-health-max-speed", "resources/ui/character_selection/low-health-max-speed.png");



            intendedTexture.put("guard_standing", "resources/Guard/guard_standing.png");
            intendedTexture.put("guard_walking_up1", "resources/Guard/guard_up1.png");
            intendedTexture.put("guard_walking_up2", "resources/Guard/guard_up2.png");
            intendedTexture.put("guard_walking_ne1", "resources/Guard/guard_ne1.png");
            intendedTexture.put("guard_walking_ne2", "resources/Guard/guard_ne2.png");
            intendedTexture.put("guard_walking_nw1", "resources/Guard/guard_nw1.png");
            intendedTexture.put("guard_walking_nw2", "resources/Guard/guard_nw2.png");
            intendedTexture.put("guard_walking_se1", "resources/Guard/guard_se1.png");
            intendedTexture.put("guard_walking_se2", "resources/Guard/guard_se2.png");
            intendedTexture.put("guard_walking_sw1", "resources/Guard/guard_sw1.png");
            intendedTexture.put("guard_walking_sw2", "resources/Guard/guard_sw2.png");
            intendedTexture.put("guard_walking_down1", "resources/Guard/guard_down1.png");
            intendedTexture.put("guard_walking_down2", "resources/Guard/guard_down2.png");

            intendedTexture.put("blood_splat", "resources/blood_splat.png");



            for (Map.Entry<String, String> entry : intendedTexture.entrySet()) {
                    try {
                            String key = entry.getKey();
                            textureMap.put(key, new Texture(entry.getValue()));
                    } catch (Exception e) {
                            LOGGER.warn("Error thrown {}", e);
                    }
            }
    }
    /**
     * Gets a texture object for a given string id
     *
     * @param id Texture identifier
     * @return Texture for given id
     */
    public Texture getTexture(String id) {
        if (textureMap.containsKey(id)) {
            return textureMap.get(id);
        } else if(explosionTexture.containsKey(id)) {
                LOGGER.info("found explosion effect");
                return explosionTexture.get(id);
        } else {
            return textureMap.get("spacman_ded");
        }
    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     * @return If texture is available or not.
     */
    public boolean hasTexture(String id) {
        return textureMap.containsKey(id);

    }

    /**
     * Saves a texture with a given id
     *
     * @param id       Texture id
     * @param filename Filename within the assets folder
     */
    public void saveTexture(String id, String filename) {
        if (!textureMap.containsKey(id)) {
            textureMap.put(id, new Texture(filename));
        }
    }

    public Map<String, Texture> getTextureMap() {
        return textureMap;
    }
}
