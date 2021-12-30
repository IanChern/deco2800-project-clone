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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pillar extends StaticEntity implements HasVariableTextures, KeyDownObserver {

    private final ArrayList<String> pillarActivationAnimation;
    private final ArrayList<String> pillarDeactivationAnimation;
    private final ArrayList<String> pillarActiveAnimation;
    private int type;
    private boolean turnOn;
    private float elapsedTime;
    private TextureAtlas.AtlasRegion currentTexture;
    List<String> pillarTextures;

    private boolean activationTransitioned;
    private boolean deactivationTransitioned = true;
    /**
     *
     * @param tile the tile that this object displayed
     * @param type the type of a pillar
     * @implNote the type must within 0-3
     */
    public Pillar (Tile tile, int type) {
        super(tile, 1, Arrays.asList("ark_still","cross_still","chalice_still","ankh_still").get(type),false);
        this.type = type;
        pillarTextures = new ArrayList<>(Arrays.asList("ark_still","cross_still","chalice_still","ankh_still"));
        pillarActivationAnimation = new ArrayList<>(Arrays.asList("ark_activation","cross_activation","chalice_activation","ankh_activation"));
        pillarDeactivationAnimation = new ArrayList<>(Arrays.asList("ark_deactivation","cross_deactivation","chalice_deactivation","ankh_deactivation"));
        pillarActiveAnimation = new ArrayList<>(Arrays.asList("ark_active","cross_active","chalice_active","ankh_active"));
        try {
            this.setTexture(pillarTextures.get(type));
        } catch (Exception e) {
            this.setTexture("spacman_dead");

        }

        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
    }

    private boolean checkOkToSwitch() {
        Pair<Character,Float> closestCharacter = closestCharacter();
        return closestCharacter.getValue1() <= 2;
    }

    /**
     * turn On the pillar
     */
    public void turnOn() {
        if (checkOkToSwitch()) {

            turnOn = true;
            GameManager.get().getWorld().turnOnPillar(this);

            elapsedTime = 0;
        }

    }

    /**
     * turn off the pillar
     */
    public void turnOff() {
        if (checkOkToSwitch() && GameManager.get().getWorld().getRunningPillarList().size() < 4) {
            turnOn = false;
            GameManager.get().getWorld().turnOffPillar(this);
            elapsedTime = 0;
        }


    }

    @Override
    public TextureAtlas.AtlasRegion getCurrentTexture() {
        return currentTexture;
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (turnOn) {
            elapsedTime+= Gdx.graphics.getDeltaTime();
            Animation<TextureAtlas.AtlasRegion> activationAnimation = GameManager.getManagerFromInstance(
                    AnimationManager.class).getAnimation(pillarActivationAnimation.get(type));

            Animation<TextureAtlas.AtlasRegion> activeAnimation = GameManager.getManagerFromInstance(
                    AnimationManager.class).getAnimation(pillarActiveAnimation.get(type));

            this.currentTexture = activationAnimation.getKeyFrame(elapsedTime,true);

            if (Arrays.asList(activationAnimation.getKeyFrames()).get(Arrays.asList(
                    activationAnimation.getKeyFrames()).size()-1).equals(currentTexture)|| activationTransitioned){

                this.currentTexture = activeAnimation.getKeyFrame(elapsedTime,true);

                activationTransitioned = true;
                deactivationTransitioned = false;

            }
        } else {

            elapsedTime+= Gdx.graphics.getDeltaTime();
            Animation<TextureAtlas.AtlasRegion> deactivationAnimation = GameManager.getManagerFromInstance(
                    AnimationManager.class).getAnimation(pillarDeactivationAnimation.get(type));

            this.currentTexture = deactivationAnimation.getKeyFrame(elapsedTime,true);

            if (Arrays.asList(deactivationAnimation.getKeyFrames()).get(0).equals(currentTexture) ||
                    deactivationTransitioned){

                this.currentTexture = null;

                deactivationTransitioned = true;
                activationTransitioned = false;

            }
        }
    }

    @Override
    public void notifyKeyDown(int keycode) {
        if (keycode == Input.Keys.TAB) {
            if (turnOn) {
                turnOff();
            } else{
                turnOn();
            }
        }
    }
}
