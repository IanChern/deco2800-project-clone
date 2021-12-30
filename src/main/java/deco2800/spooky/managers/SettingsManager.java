package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.managers.TextureManager;


/**
 * Creating an AbstractManager that manages the setting page including the setting of sound
 */
public class SettingsManager implements AbstractManager {

    private Stage stage; //the stage of the game
    private ImageButton soundButton; //button that controls sound

    public SettingsManager(final ThomasGame game) {
        Drawable singleDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("soundOption")));
        stage = GameManager.get().getStage();
        soundButton = new ImageButton(singleDrawable);
        soundButton.setPosition(1100, 690);
        soundButton.setPosition(120,40);
        stage.addActor(soundButton);

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().getManager(SoundManager.class).muteSound();
            }
        });
    }

}