package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * animation manager acts as a cache between the file system and the renderers.
 * This allows all animations to be read into memory at the start of the game saving
 * file reads from being completed during rendering.
 * @Author Haoyuan Ma
 */
public class AnimationManager implements AbstractManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationManager.class);

    /**
	 * The height of the tile to use when positioning the tile.
	 */
	public static final int TILE_HEIGHT = 278;

    /**
     * A HashMap of all animations with string keys
     */
    private Map<String, Animation<TextureAtlas.AtlasRegion>> animations = new HashMap<>();

    /**
     * A HashMap of all animation textures with string keys
     */
    private Map<String, TextureAtlas> animationTextures = new HashMap<>();

    private String rainbowSpac =  "rainbow_spacmans";

    private String rainbowAtlas = "resources/rainbow_spacmans/rainbow_spacman.atlas";

    private Map<String, String> intendedAtlas;

    /**
     * Constructor
     * Currently loads up all the textures and animations
     */
    public AnimationManager() {
        String yellowRup = "yellow_rupee";
        String blueRup = "blue_rupee";
        String greenRup = "green_rupee";
        String purpleRup = "purple_rupee";
        String arkActivation = "ark_activation";
        String arkActive = "ark_active";
        String crossActivation = "cross_activation";
        String crossActive = "cross_active";
        String chaliceActivation = "chalice_activation";
        String chaliceActive = "chalice_active";
        String ankhActivation = "ankh_activation";
        String ankhActive = "ankh_active";
        String bluepotActivation = "blue_pot_activation";
        String redpotActivation = "red_pot_activation";
        String yellowpotActivation = "yellow_pot_activation";
        String atlasFormat = ".atlas";

        //default
        Map<String, String> intendedAtlas = new HashMap<>();

        intendedAtlas.put(rainbowSpac, rainbowAtlas);

        //rupees
        intendedAtlas.put(yellowRup, "resources/items/YellowRupee/yellow_rupee.atlas");
        intendedAtlas.put(greenRup, "resources/items/GreenRupee/green_rupee.atlas");
        intendedAtlas.put(blueRup, "resources/items/BlueRupee/blue_rupee.atlas");
        intendedAtlas.put(purpleRup, "resources/items/PurpleRupee/purple_rupee.atlas");

        //coin
        intendedAtlas.put("coin", "resources/coin/coin.atlas");

        //chris
        //Removed commented code for reference

        intendedAtlas.put("chris_neutral_down_attack", "resources/characters/christopher/384x384/neutral_down_attack_frames/neutral_down_attack.atlas");

        intendedAtlas.put("chris_walk_down", "resources/characters/Christopher/384x384/walking_down_frames/chris_walk_down.atlas");
        intendedAtlas.put("chris_walk_down_e", "resources/characters/Christopher/384x384/walking_right_frames/chris_walk_right.atlas");
        intendedAtlas.put("chris_walk_down_w", "resources/characters/Christopher/384x384/walking_left_frames/chris_walk_left.atlas");
        intendedAtlas.put("chris_walk_up", "resources/characters/Christopher/384x384/walking_up_frames/chris_walk_up.atlas");
        intendedAtlas.put("chris_walk_up_e", "resources/characters/Christopher/384x384/walking_right_frames/chris_walk_right.atlas");
        intendedAtlas.put("chris_walk_up_w", "resources/characters/Christopher/384x384/walking_left_frames/chris_walk_left.atlas");


        //jane
        intendedAtlas.put("jane_walk_down", "resources/characters/Jane/384x384/walking_down_frames/jane_walk_down.atlas");
        intendedAtlas.put("jane_walk_down_e", "resources/characters/Jane/384x384/walking_right_frames/jane_walk_right.atlas");
        intendedAtlas.put("jane_walk_down_w", "resources/characters/Jane/384x384/walking_left_frames/jane_walk_left.atlas");
        intendedAtlas.put("jane_walk_up", "resources/characters/Jane/384x384/walking_up_frames/jane_walk_up.atlas");
        intendedAtlas.put("jane_walk_up_e", "resources/characters/Jane/384x384/walking_right_frames/jane_walk_right.atlas");
        intendedAtlas.put("jane_walk_up_w", "resources/characters/Jane/384x384/walking_left_frames/jane_walk_left.atlas");

        //damien
        intendedAtlas.put("damien_walk_down", "resources/characters/damien/384x384/walking_down_frames/damien_walk_down.atlas");
        intendedAtlas.put("damien_walk_down_e","resources/characters/damien/384x384/walking_right_frames/damien_walk_right.atlas");
        intendedAtlas.put("damien_walk_down_w", "resources/characters/damien/384x384/walking_left_frames/damien_walk_left.atlas");
        intendedAtlas.put("damien_walk_up", "resources/characters/damien/384x384/walking_up_frames/damien_walk_up.atlas");
        intendedAtlas.put("damien_walk_up_e", "resources/characters/damien/384x384/walking_right_frames/damien_walk_right.atlas");
        intendedAtlas.put("damien_walk_up_w", "resources/characters/damien/384x384/walking_left_frames/damien_walk_left.atlas");
        //if with weapon
        intendedAtlas.put("damien_down_sword", "resources/characters/damien/384x384/walking_down_frames/damien_sword_down.atlas");
        intendedAtlas.put("damien_down_e_sword","resources/characters/damien/384x384/walking_right_frames/damien_sword_right.atlas");
        intendedAtlas.put("damien_down_w_sword", "resources/characters/damien/384x384/walking_left_frames/damien_sword_left.atlas");
        intendedAtlas.put("damien_up_sword", "resources/characters/damien/384x384/walking_up_frames/damien_sword_up.atlas");
        intendedAtlas.put("damien_up_e_sword", "resources/characters/damien/384x384/walking_right_frames/damien_sword_right.atlas");
        intendedAtlas.put("damien_up_w_sword", "resources/characters/damien/384x384/walking_left_frames/damien_sword_left.atlas");

        intendedAtlas.put("damien_walk_down_axe", "resources/characters/damien/384x384/melee_down_frames/damien_walking_down_melee.atlas");
        intendedAtlas.put("damien_walk_left_axe", "resources/characters/damien/384x384/melee_left_frames/damien_walking_left_melee.atlas");
        intendedAtlas.put("damien_walk_right_axe", "resources/characters/damien/384x384/melee_right_frames/damien_walking_right_melee.atlas");
        intendedAtlas.put("damien_walk_up_axe", "resources/characters/damien/384x384/melee_up_frames/damien_walking_up_melee.atlas");

        intendedAtlas.put("damien_stand_down_axe", "resources/characters/damien/384x384/stand_down_melee_frames/damien_neutral_down_melee.atlas");
        intendedAtlas.put("damien_stand_up_axe", "resources/characters/damien/384x384/stand_up_melee_frames/damien_neutral_up_melee.atlas");
        intendedAtlas.put("damien_stand_left_axe", "resources/characters/damien/384x384/stand_left_melee_frames/damien_neutral_left_melee.atlas");
        intendedAtlas.put("damien_stand_right_axe", "resources/characters/damien/384x384/stand_right_melee_frames/damien_neutral_right_melee.atlas");

        //larry
        intendedAtlas.put("larry_walk_down", "resources/characters/larry/384x384/walking_down_frames/larry_walk_down.atlas");
        intendedAtlas.put("larry_walk_down_e", "resources/characters/larry/384x384/walking_right_frames/larry_walk_right.atlas");
        intendedAtlas.put("larry_walk_down_w", "resources/characters/larry/384x384/walking_left_frames/larry_walk_left.atlas");
        intendedAtlas.put("larry_walk_up", "resources/characters/larry/384x384/walking_up_frames/larry_walk_up.atlas");
        intendedAtlas.put("larry_walk_up_e", "resources/characters/larry/384x384/walking_right_frames/larry_walk_right.atlas");
        intendedAtlas.put("larry_walk_up_w", "resources/characters/larry/384x384/walking_left_frames/larry_walk_left.atlas");

        //katie
        intendedAtlas.put("katie_walk_down", "resources/characters/katie/384x384/walking_down_frames/katie_walk_down.atlas");
        intendedAtlas.put("katie_walk_down_e", "resources/characters/katie/384x384/walking_right_frames/katie_walk_right.atlas");
        intendedAtlas.put("katie_walk_down_w", "resources/characters/katie/384x384/walking_left_frames/katie_walk_left.atlas");
        intendedAtlas.put("katie_walk_up", "resources/characters/katie/384x384/walking_up_frames/katie_walk_up.atlas");
        intendedAtlas.put("katie_walk_up_e", "resources/characters/katie/384x384/walking_right_frames/katie_walk_right.atlas");
        intendedAtlas.put("katie_walk_up_w", "resources/characters/katie/384x384/walking_left_frames/katie_walk_left.atlas");

        //titus
        intendedAtlas.put("titus_walk_down", "resources/characters/titus/384x384/walking_down_frames/titus_walk_down.atlas");
        intendedAtlas.put("titus_walk_down_e", "resources/characters/titus/384x384/walking_right_frames/titus_walk_right.atlas");
        intendedAtlas.put("titus_walk_down_w", "resources/characters/titus/384x384/walking_left_frames/titus_walk_left.atlas");
        intendedAtlas.put("titus_walk_up", "resources/characters/titus/384x384/walking_up_frames/titus_walk_up.atlas");
        intendedAtlas.put("titus_walk_up_e", "resources/characters/titus/384x384/walking_right_frames/titus_walk_right.atlas");
        intendedAtlas.put("titus_walk_up_w", "resources/characters/titus/384x384/walking_left_frames/titus_walk_left.atlas");

        //shrine
        intendedAtlas.put(arkActivation,"resources/shrines/ark_shrine/activation_frames/ark_activation.atlas");
        intendedAtlas.put(arkActive,"resources/shrines/ark_shrine/active_frames/ark_active.atlas");
        intendedAtlas.put(crossActivation,"resources/shrines/cross_shrine/activation_frames/cross_activation.atlas");
        intendedAtlas.put(crossActive,"resources/shrines/cross_shrine/active_frames/cross_active.atlas");
        intendedAtlas.put(chaliceActivation,"resources/shrines/chalice_shrine/activation_frames/chalice_activation.atlas");
        intendedAtlas.put(chaliceActive,"resources/shrines/chalice_shrine/active_frames/chalice_active.atlas");
        intendedAtlas.put(ankhActivation,"resources/shrines/ankh_shrine/activation_frames/ankh_activation.atlas");
        intendedAtlas.put(ankhActive,"resources/shrines/ankh_shrine/active_frames/ankh_active.atlas");

        // pot
        intendedAtlas.put(bluepotActivation,"resources/map_extras/pots/large_pot/blue/frames/blue_activation" +
                atlasFormat);
        intendedAtlas.put(redpotActivation,"resources/map_extras/pots/large_pot/red/frames/red_activation" +
                atlasFormat);
        intendedAtlas.put(yellowpotActivation,"resources/map_extras/pots/large_pot/yellow/frames/yellow_activation" +
                atlasFormat);


        for (Map.Entry<String, String> entry : intendedAtlas.entrySet()) {
            try {
                String key = entry.getKey();
                animationTextures.put(key, new TextureAtlas(entry.getValue()));
            } catch (Exception e) {
                LOGGER.warn("Error thrown {}", e);
            }
        }

        for (Map.Entry<String, String> entry : intendedAtlas.entrySet()) {
            try {
                String key = entry.getKey();
                animationTextures.put(key, new TextureAtlas(entry.getValue()));
            } catch (Exception e) {
                LOGGER.warn("Error thrown {}", e);
            }
        }

        //default
        addNewAnimation(rainbowSpac,rainbowSpac, 1f/4f);

        //pillars
        addNewAnimation("pillar_running",rainbowSpac, 1f/4f);

        //rupees
        addNewAnimation(yellowRup,yellowRup, 1f/5f);
        addNewAnimation(greenRup,greenRup, 1f/5f);
        addNewAnimation(blueRup,blueRup, 1f/5f);
        addNewAnimation(purpleRup,purpleRup, 1f/5f);

        //coin
        addNewAnimation("coin","coin", 1f/10f);

        //chris
        addNewAnimation("chris_walking_down","chris_walk_down", 1f/4f);
        addNewAnimation("chris_walking_SouthE","chris_walk_down_e", 1f/4f);
        addNewAnimation("chris_walking_SouthW","chris_walk_down_w", 1f/4f);
        addNewAnimation("chris_walking_up","chris_walk_up", 1f/4f);
        addNewAnimation("chris_walking_NorthE","chris_walk_up_e", 1f/4f);
        addNewAnimation("chris_walking_NorthW","chris_walk_up_w", 1f/4f);



        //damien
        addNewAnimation("damien_walking_down","damien_walk_down", 1f/4f);
        addNewAnimation("damien_walking_SouthE","damien_walk_down_e", 1f/4f);
        addNewAnimation("damien_walking_SouthW","damien_walk_down_w", 1f/4f);
        addNewAnimation("damien_walking_up","damien_walk_up", 1f/4f);
        addNewAnimation("damien_walking_NorthE","damien_walk_up_e", 1f/4f);
        addNewAnimation("damien_walking_NorthW","damien_walk_up_w", 1f/4f);
        //damien with weapon
        addNewAnimation("damien_walking_down_sword","damien_walk_down_sword", 1f/4f);
        addNewAnimation("damien_walking_SouthE_sword","damien_walk_down_e_sword", 1f/4f);
        addNewAnimation("damien_walking_SouthW_sword","damien_walk_down_w_sword", 1f/4f);
        addNewAnimation("damien_walking_up_sword","damien_walk_up_sword", 1f/4f);
        addNewAnimation("damien_walking_NorthE_sword","damien_walk_up_e_sword", 1f/4f);
        addNewAnimation("damien_walking_NorthW_sword","damien_walk_up_w_sword", 1f/4f);

        addNewAnimation("damien_walking_down_axe", "damien_walk_down_axe", 0.1f);
        addNewAnimation("damien_walking_left_axe", "damien_walk_left_axe", 0.1f);
        addNewAnimation("damien_walking_right_axe", "damien_walk_right_axe", 0.1f);
        addNewAnimation("damien_walking_up_axe", "damien_walk_up_axe", 0.1f);
        addNewAnimation("damien_standing_down_axe","damien_stand_down_axe", 0.1f);
        addNewAnimation("damien_standing_up_axe","damien_stand_up_axe", 0.1f);
        addNewAnimation("damien_standing_left_axe","damien_stand_left_axe", 0.1f);
        addNewAnimation("damien_standing_right_axe","damien_stand_right_axe", 0.1f);

        //jane
        addNewAnimation("jane_walking_down","jane_walk_down", 1f/4f);
        addNewAnimation("jane_walking_SouthE","jane_walk_down_e", 1f/4f);
        addNewAnimation("jane_walking_SouthW","jane_walk_down_w", 1f/4f);
        addNewAnimation("jane_walking_up","jane_walk_up", 1f/4f);
        addNewAnimation("jane_walking_NorthE","jane_walk_up_e", 1f/4f);
        addNewAnimation("jane_walking_NorthW","jane_walk_up_w", 1f/4f);

        //titus
        addNewAnimation("titus_walking_down","titus_walk_down", 1f/4f);
        addNewAnimation("titus_walking_SouthE","titus_walk_down_e", 1f/4f);
        addNewAnimation("titus_walking_SouthW","titus_walk_down_w", 1f/4f);
        addNewAnimation("titus_walking_up","titus_walk_up", 1f/4f);
        addNewAnimation("titus_walking_NorthE","titus_walk_up_e", 1f/4f);
        addNewAnimation("titus_walking_NorthW","titus_walk_up_w", 1f/4f);

        //katie
        addNewAnimation("katie_walking_down","katie_walk_down", 1f/4f);
        addNewAnimation("katie_walking_SouthE","katie_walk_down_e", 1f/4f);
        addNewAnimation("katie_walking_SouthW","katie_walk_down_w", 1f/4f);
        addNewAnimation("katie_walking_up","katie_walk_up", 1f/4f);
        addNewAnimation("katie_walking_NorthE","katie_walk_up_e", 1f/4f);
        addNewAnimation("katie_walking_NorthW","katie_walk_up_w", 1f/4f);

        //larry
        addNewAnimation("larry_walking_down","larry_walk_down", 1f/4f);
        addNewAnimation("larry_walking_SouthE","larry_walk_down_e", 1f/4f);
        addNewAnimation("larry_walking_SouthW","larry_walk_down_w", 1f/4f);
        addNewAnimation("larry_walking_up","larry_walk_up", 1f/4f);
        addNewAnimation("larry_walking_NorthE","larry_walk_up_e", 1f/4f);
        addNewAnimation("larry_walking_NorthW","larry_walk_up_w", 1f/4f);

        //shrines
        addNewAnimation(arkActivation,arkActivation,1f/4f);
        addNewAnimation("ark_deactivation",arkActivation,1f/4f);
        addNewAnimation(arkActive, arkActive,1f/3f);
        addNewAnimation(crossActivation,crossActivation,1f/4f);
        addNewAnimation("cross_deactivation",crossActivation,1f/4f);
        addNewAnimation(crossActive,crossActive,1f/4f);
        addNewAnimation(ankhActivation,ankhActivation,1f/4f);
        addNewAnimation("ankh_deactivation",ankhActivation,1f/4f);
        addNewAnimation(ankhActive,ankhActive,1f/4f);
        addNewAnimation(chaliceActivation,chaliceActivation,1f/5f);
        addNewAnimation("chalice_deactivation",chaliceActivation,1f/5f);
        addNewAnimation(chaliceActive,chaliceActive,1f/3f);

        // pots
        addNewAnimation(bluepotActivation, bluepotActivation, 1f/5f);
        addNewAnimation(yellowpotActivation, yellowpotActivation, 1f/5f);
        addNewAnimation(redpotActivation, redpotActivation, 1f/5f);

        // set the animations with reverse playmode.
        animations.get("ark_deactivation").setPlayMode(Animation.PlayMode.REVERSED);
        animations.get("cross_deactivation").setPlayMode(Animation.PlayMode.REVERSED);
        animations.get("ankh_deactivation").setPlayMode(Animation.PlayMode.REVERSED);
        animations.get("chalice_deactivation").setPlayMode(Animation.PlayMode.REVERSED);
    }


    /**
     * addNewAnimations into the manager
     * @param animationName the name of animation
     * @param animationTextureName the texture name stored in the animationTexture
     * @param frameDuration how long time does a frame last.
     */
    private void addNewAnimation(String animationName, String animationTextureName, float frameDuration) {
        try {
            animations.put(animationName, new Animation<>(frameDuration, animationTextures
                    .get(animationTextureName).getRegions()));
        } catch (Exception e) {
            LOGGER.warn("Error thrown {}", e);
        }
    }


    /**
     * Gets a texture object for a given string id
     *
     * @param id Texture identifier
     * @return Texture for given id
     */
    public Animation<TextureAtlas.AtlasRegion> getAnimation(String id) {
        if (animations.containsKey(id)) {
            return animations.get(id);
        } else {
            LOGGER.info("Animation ID {}", id);
            return animations.get("rainbow_spacman");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     * @return If texture is available or not.
     */
    public boolean hasAnimation(String id) {
        return animations.containsKey(id);

    }

    /**
     * Saves a texture with a given id
     *
     * @param id       Texture id
     * @param filename Filename within the assets folder
     */
    public void saveAnimationTexture(String id, String filename) {
        if (!animations.containsKey(id)) {
                animationTextures.put(id, new TextureAtlas(filename));
        }
    }

    public void saveAnimation(String id, float secondsPerFrame,String filenameInAnimationTexture) {
        if (!animations.containsKey(id)) {
                animations.put(id, new Animation<>(secondsPerFrame, animationTextures.get(filenameInAnimationTexture).getRegions()));
        }
    }

    /**
     * get all animation textures
     * @return all animation textures
     */
    public Map<String, TextureAtlas> getTextureMap() {
        return animationTextures;
    }

    /**
     * get all animations
     * @return all animations
     */
    public Map<String, Animation<TextureAtlas.AtlasRegion>> getAnimations() {
        return animations;
    }
}
