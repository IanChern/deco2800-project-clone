package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.Tickable;
import deco2800.spooky.mainmenu.CharacterMenuScreen;

import java.util.Random;

public class FlashManager implements AbstractManager {


    private Stage stage;

    private ThomasGame game;

    private Table flash;

    private Skin skin;

    private int tickCount = 0;
    private int lastFlash = 0;
    private TextureManager textureManager;

    /**
     * Constructor
     *
     * @param stage the game stage
     */
    public FlashManager(final ThomasGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.flash = new Table();
        this.flash.setVisible(false);
        this.textureManager = new TextureManager();

        Image pauseMenuBackground = new Image(textureManager.getTexture("blood_splat"));
        this.flash.setBackground(pauseMenuBackground.getDrawable());
        // add background to it
        this.flash.pack();

        this.stage.addActor(this.flash);
        this.createBasicSkin();

    }

    /**
     * set the pause table to be visible
     */
    public void showFlash() {
        this.flash.setVisible(true);
    }

    public void removeFlash() {
        this.flash.setVisible(false);
    }

    /**
     * create basic skin for text buttons
     */
    private void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        this.skin = new Skin();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(250, 100, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));


    }

    public void setLastFlash(int lastFlash) {
        this.lastFlash = lastFlash;
    }

    public int getTickCount() {
        return this.tickCount;
    }

    public void onTick(long i) {

        long currentTime = System.currentTimeMillis();

        tickCount++;

        if ((tickCount - lastFlash) >= 10) {
            this.removeFlash();
        }
    }
}

