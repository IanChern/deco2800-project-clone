package deco2800.spooky.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.managers.AnimationManager;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.InputManager;
import deco2800.spooky.observers.KeyDownObserver;
import deco2800.spooky.worlds.Tile;
import org.javatuples.Pair;

import java.util.Arrays;

/**
 * Pot class, user could pick a random when typing the B button
 */
public class Pot extends StaticEntity implements HasVariableTextures, KeyDownObserver {
    // the pot animation when user break the pot
    private String potActivationAnimation;

    // the initial texture
    private String potStillTexture;

    // the break texture
    private String potBreakTexture;

    private boolean turnOn;
    private float elapsedTime;
    private TextureAtlas.AtlasRegion currentTexture;

    // animation happened
    private boolean activationTransitioned;

    // character who break the pot
    private Character characterBreakPot;

    // pot type, 0 is yellow, 1 is blue, 2 is yellow
    private int potType;
    /**
     * Pot constructor
     * @param tile the tile of the pot
     */
    public Pot(Tile tile, int potType) {
        super(tile, 1, Arrays.asList("yellow_pot_still", "blue_pot_still", "red_pot_still").get(potType), false);
        this.potType = potType;
        this.potStillTexture = Arrays.asList("yellow_pot_still", "blue_pot_still", "red_pot_still").get(this.potType);
        this.potActivationAnimation = Arrays.asList("yellow_pot_activation", "blue_pot_activation",
                "red_pot_activation").get(this.potType);
        this.potBreakTexture = Arrays.asList("yellow_pot_break", "blue_pot_break", "red_pot_break").get(this.potType);
        this.activationTransitioned = false;
        this.turnOn = false;
        this.setTexture(this.potBreakTexture);
        try {
            this.setTexture(this.potStillTexture);
        } catch (Exception e) {
            this.setTexture("spacman_dead");
        }

        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
    }

    /**
     * create a break pot
     * @param tile the tile of the ot
     * @param breakTexture the break texture
     */
    public Pot(Tile tile, String breakTexture) {
        super(tile, 1, breakTexture, false);
        this.potBreakTexture = breakTexture;
        this.activationTransitioned = true;
        this.turnOn = true;
    }


    /**
     * check if the character is close enough to break the pot
     * @return
     */
    private boolean checkOkToSwitch() {
        Pair<Character,Float> closestCharacter = this.closestCharacter();
        if (closestCharacter.getValue1()<=2) {
            // record that character
            this.characterBreakPot = closestCharacter.getValue0();
            return true;
        }
        return false;
    }

    /**
     * break the pot and give the character a random gun, each pot only could be turn on once
     */
    public void turnOnPot() {

        if (this.checkOkToSwitch()) {
            this.turnOn = true;
            if (this.characterBreakPot != null) {
                if (this.potType == 0) {
                    // yellow, get a dagger
                    this.characterBreakPot.pickUpWeapon(new MeleeWeapon(getCol(), getRow(), MeleeType.DAGGER));
                } else if (this.potType == 1) {
                    // blue, get a Axe
                    this.characterBreakPot.pickUpWeapon(new MeleeWeapon(getCol(), getRow(), MeleeType.AXE));
                } else {
                    this.characterBreakPot.pickUpWeapon(new MeleeWeapon(getCol(), getRow(), MeleeType.SWORD));
                }
            }
            this.elapsedTime = 0;

        }
    }

    @Override
    public TextureAtlas.AtlasRegion getCurrentTexture() {
        return currentTexture;
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (this.turnOn) {
            this.elapsedTime += Gdx.graphics.getDeltaTime();
            Animation<TextureAtlas.AtlasRegion> activationAnimation = GameManager.getManagerFromInstance(
                    AnimationManager.class).getAnimation(this.potActivationAnimation);

            this.currentTexture = activationAnimation.getKeyFrame(elapsedTime, true);

            if (Arrays.asList(activationAnimation.getKeyFrames()).get(Arrays.asList(
                    activationAnimation.getKeyFrames()).size()-1).equals(currentTexture)|| activationTransitioned){

                // animation stops
                this.currentTexture = null;
                this.activationTransitioned = true;
                this.setTexture(this.potBreakTexture);

                // remove pot
                GameManager.get().getWorld().removeEntity(this);
            }
        } else {
            this.currentTexture = null;
        }

    }

    @Override
    public void notifyKeyDown(int keycode) {
        if (this.turnOn) {
            return;
        } else {
            if (keycode == Input.Keys.B) {
                this.turnOnPot();
            }
        }
    }
}
