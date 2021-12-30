package deco2800.spooky.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creating an actor class with an event listener (in this case a keypad)
 * representing the explosion effect
 */

public class WeaponEffect {
    private String name; //the name of the effect

    private static final Logger logger = LoggerFactory.getLogger(WeaponEffect.class);


    //for explosion texture region setting
    private Animation[] animArray;  //animation array
    private Texture sprite; //the sprite sheet of the animation

    public WeaponEffect() {
        animArray = new Animation[5];
    }

    /**
     * render different effects depending on the weapon type using switch
     *
     * @param name the name of the weapon
     */
    public Texture getTexture(String name) {
        if (name == null) {
            return null;
        }

        switch (name) {
            case "explosion":
                sprite = GameManager.get().getManager(TextureManager.class).
                        getTexture("explosionSprite");
                logger.info("adding weapon, success");
                //code
                break;
            case "bullet":
                //code

                break;
                default:
                    sprite = null;
                    break;
        }
        return sprite;
    }

    /**
     * return the animation
     * @return the animation
     */
    public Animation[] getAnimationArray() {
        return animArray;
    }

    public TextureRegion[][] getTextureMatrix() {
        //textureRegion Array
        return TextureRegion.split(getTexture(name), 160, 160);
    }
}







