package deco2800.spooky.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.managers.AnimationManager;
import deco2800.spooky.managers.GameManager;

import java.util.Arrays;

/**
 * The character's objectAppearance
 */
public class Appearance {

    private String hair;
    private String skin;
    private String cloth;
    private String objectAppearance;
    private static String[] defaultCharacters = {"assassin", "hunter", "ninja", "robot", "robot", "chris","mummy"};
    private static final String[] DIRECTION = {"up","down","left","right", "NorthW", "NorthE", "SouthW", "SouthE"};
    private static final String DEFAULTAPPEARANCE = "damien_standing";


    /**
     * Custom a character
     * @param hair The character's hair
     * @param skin The character's skin
     * @param cloth The character's cloth
     */
    public Appearance(String hair, String skin, String cloth) {
        this.hair = hair;
        this.skin = skin;
        this.cloth = cloth;
    }

    /**
     * use default character name, no custom, default is spacman_blue
     * @param characterName defaultCharacter name
     */
    public Appearance(String characterName) {
        if (characterName == null) {
            this.objectAppearance = "spacman_blue";
        }
        if (Arrays.asList(defaultCharacters).contains(characterName)) {
            this.objectAppearance = characterName;
        } else {
            this.objectAppearance = "spacman_blue";
        }

    }

    /**
     * get default objectAppearance
     * @return objectAppearance DEFAULTAPPEARANCE
     */
    public String getDefaultAppearance() {
        return DEFAULTAPPEARANCE;
    }

    /**
     * set default Appearance
     * @param defaultAppearance default objectAppearance of character
     */
    public void setDefaultAppearance(String defaultAppearance) {
        this.objectAppearance = defaultAppearance;
    }

    /**
     * get character's moving Appearance
     * @param direction the direction that character is facing to
     * @return the default objectAppearance with facing direction
     */
    public String getNeutralDirectionAppearance(String direction) {
        if (Arrays.asList(DIRECTION).contains(direction)){
            return "damien_neutral_"+direction;
        }
        return getDefaultAppearance();
    }

    /**
     * get character's walking objectAppearance
     * @param direction the direction that character is moving at
     * @return the default objectAppearance with moving direction
     */
    public Animation<TextureAtlas.AtlasRegion> getWalkingAppearance(String direction) {
        if (Arrays.asList(DIRECTION).contains(direction)){
            return GameManager.getManagerFromInstance(AnimationManager.class).getAnimation("damien"+"_walking_"+direction);
        }
        return GameManager.getManagerFromInstance(AnimationManager.class).getAnimation("rainbow_spacman");
    }

    /**
     * Get character's walking appearance when it is holding a melee weapon
     * @param direction the direction of the character
     * @param type the type of the melee weapon
     * @return the Appearance with moving direction and melee weapons
     */
    public Animation<TextureAtlas.AtlasRegion> getWalkingAppearanceWithMelee(String direction, MeleeType type) {
        if (Arrays.asList(DIRECTION).contains(direction)){
            String typeStr = type.toString().toLowerCase();
            String animationID = "damien_" + direction + "_" + typeStr;
            return GameManager.getManagerFromInstance(AnimationManager.class).getAnimation(animationID);
        }
        return GameManager.getManagerFromInstance(AnimationManager.class).getAnimation("rainbow_spacman");
    }

    /**
     * Get character's melee attacking appearance
     * @param meleeType what melee weapon the character holds
     * @param direction the direction the character is facing to
     * @param movement whether the character is standing or walking
     * @return the Appearance with moving direction and melee weapons
     */
    public Animation<TextureAtlas.AtlasRegion> getMeleeAttackApprearance(MeleeType meleeType, String direction, String movement) {
        if (direction.equals("SouthW") || direction.equals("NorthW")) {
            direction = "left";
        } else if (direction.equals("SouthE") || direction.equals("NorthE")) {
            direction = "right";
        }
        String weaponName = meleeType.toString().toLowerCase();
        String animationID = "damien_" + movement + "_" + direction + "_" + weaponName;
        return GameManager.getManagerFromInstance(AnimationManager.class).getAnimation(animationID);
    }

    /**
     * get character's hair
     * @return character's hair
     */
    public String getHair() {
        return this.hair;
    }

    /**
     * get character's skin
     * @return character's skin
     */
    public String getSkin() {
        return this.skin;
    }

    /**
     * get character's cloth
     * @return character's cloth
     */
    public String getCloth() {
        return this.cloth;
    }

    /**
     * get character's name
     * @return character's name
     */
    public String getName(){return this.objectAppearance;}

    /**
     * set character's cloth
     * @param cloth newCloth to be set
     */
    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    /**
     * set character's hair
     * @param hair newHair to be set
     */
    public void setHair(String hair) {
        this.hair = hair;
    }

    /**
     * set character's skin
     * @param skin new skin to be set
     */
    public void setSkin(String skin) {
        this.skin = skin;
    }

}
