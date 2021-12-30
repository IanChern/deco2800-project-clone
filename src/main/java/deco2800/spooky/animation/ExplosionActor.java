package deco2800.spooky.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ScreenManager;

/**
 * creating an animated actor that can be added to the stage
 */
public class ExplosionActor extends Actor {
    private TextureRegion[][] animations; //sprite matrix
    private TextureRegion[] regions;
    private float currentFrameTime;  // the amount of seconds spent in the state (texture)
    private float maxFrameTime; //max spent on a certain frame
    private int frame; //current frame
    private int frameNo; //frame number
    private boolean flag; //flag of whether the animation has been finished
    private Stage stage; //game stage

    public ExplosionActor(TextureRegion[][] anims, float col, float row) {
        this.animations = anims;
        currentFrameTime = 0f;
        this.maxFrameTime = 0.05f;//total frame time 0.25f
        this.regions = new TextureRegion[5];
        this.flag = true;
        this.stage = GameManager.get().getStage();
        setFrame(0);
    }

    /**
     * updating animation by changing texture region and add the image to the stage
     * @param delta delta time between the times when render() is called
     */
    public void update(float delta) {
        float time = getCurrentFrameTime() + delta;
        if(time > maxFrameTime) {
            this.frame++;
            currentFrameTime = 0;
            addActor();
        }

        if(frame > frameNo) {
            frame = 0;
            setFlag(false);
        }
    }

    /**
     * set the flag when the animation is finished
     * @param flag false when the animation has finished
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * adding the animation to the stage
     */
    public void addActor() {
        regions[frame] = animations[0][frame];
        stage.addActor(new Image(regions[frame]));
    }

    /**
     * render the animation based on the current time
     */
    public void render() {
        while (flag) {
            update(GameManager.get().getManager(ScreenManager.class).
                    getCurrentScreen().getDeltaTime(this.getCurrentTime()));
        }
    }

    /**
     * get current Time
     */
    public long getCurrentTime() {
        return GameManager.get().getManager(ScreenManager.class).
                getCurrentScreen().getCurrentTime();
    }

    /**
     * get current animation frame
     */
    public int getFrame() {
        return this.frame;
    }

    /**
     * set the current animation frame
     * @param frame the current frame to be set
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * get the max frame time of the animation
     * @return max frame time for an animation
     */
    public float getMaxFrameTime() {
        return maxFrameTime;
    }

    /**
     * set the max frame time for the animation
     * @param maxTime max frame time spent on the animation
     */
    public void setMaxFrameTime(float maxTime) {
        this.maxFrameTime = maxTime;
    }

    /**
     * set the current frame time
     * @param time the current frame time to be set
     */
    public void setCurrentFrameTime(float time) {
        this.currentFrameTime = time;
    }

    /**
     * get the current Frame time
     * @return currentFrametime
     */
    public float getCurrentFrameTime() {
        return this.currentFrameTime;
    }

}
