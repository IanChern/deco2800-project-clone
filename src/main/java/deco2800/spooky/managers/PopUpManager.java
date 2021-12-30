package deco2800.spooky.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import java.util.Queue;

/**
 * This Manager is for Managing the different message boxes on the game
 */
public class PopUpManager implements AbstractManager {
    private static final String SKIN = "resources/uiskin.skin";
    private boolean promptResult;
    /**
     * display pop up message
     * @param type different status, including "indicative", "minor", "major" and "killing"
     * @param message the message to be displayed
     */
    public void displayPopUpMessage (String type, String message) {
        Label frame = newSkin();
        int displayingSeconds;

        switch (type) {
            case "Indicative":
                frame.setText(message);
                frame.setFontScale(1.5f);
                frame.setPosition(0, 720);
                frame.setAlignment(Align.topLeft);
                displayingSeconds = 1;
                break;
            case "Minor":
                frame.setText(message);
                frame.setFontScale(2.0f);
                frame.setPosition(1280/2, 720);
                displayingSeconds = 8;
                break;
            case "Major":
                frame.setText(message);
                frame.setFontScale(3.0f);
                frame.setPosition(1280/2, 720/2);
                displayingSeconds = 1;

                break;
            case "Killing":
                frame.setText(message);
                frame.setFontScale(1f);
                frame.setPosition(1280, 720);
                frame.setAlignment(Align.topRight);
                displayingSeconds = 15;
                break;
            default:
                return;
        }
        GameManager.get().getStage().addActor(frame);
        removeAfterSec(frame,displayingSeconds);
    }

    private Label newSkin() {
        Skin skin = new Skin(Gdx.files.internal(SKIN));
        //experimental coloured background
        NinePatch temp = new NinePatch(new Texture(Gdx.files.internal("resources/ui/game/pixil-frame-0-4.png")), 12, 12, 12, 12);


        skin.add("background", temp);

        Label frame = new Label("", skin);
        Drawable bg = new TextureRegionDrawable(new TextureRegion(GameManager.getManagerFromInstance(TextureManager.class).getTexture("bgOfLabel")));

        Label.LabelStyle style = new Label.LabelStyle();
        style.background = bg;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/fonts/SquadaOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18; // font size
        BitmapFont font18 = generator.generateFont(parameter);
        generator.dispose(); // avoid memory leaks, important
        style.font = font18;
        frame.setStyle(style);
        frame.setAlignment(Align.top);
        return(frame);
    }

    /**
     * display pop up message in a certain seconds
     * @param type different status, including "indicative", "minor", "major" and "killing"
     * @param message the message to be displayed
     * @param displayingSeconds the time that a message to be displayed
     */
    public void displayPopUpMessage (String type, String message, float displayingSeconds) {
        Label frame = newSkin();

        switch (type) {
            case "Indicative":
                frame.setText(message);
                frame.setFontScale(1.5f);
                frame.setPosition(0, 720);
                frame.setAlignment(Align.topLeft);
                break;
            case "Minor":
                frame.setText(message);
                frame.setFontScale(2.0f);
                frame.setPosition(1280/2, 720);
                break;
            case "Major":
                frame.setText(message);
                frame.setFontScale(3.0f);
                frame.setPosition(1280/2, 720/2);

                break;
            case "Killing":
                frame.setText(message);
                frame.setFontScale(1f);
                frame.setPosition(1280, 720);
                frame.setAlignment(Align.topRight);
                break;
            default:
                return;
        }
        GameManager.get().getStage().addActor(frame);
        if (displayingSeconds > 0) {
            removeAfterSec(frame,displayingSeconds);
        }
        
    }

    /**
     * FOR DEVELOPMENT ONLY.
     * Displays all the player's roles on the game
     * @param messages messages that shows who is the traitor for the game
     */
    public void displayPlayersRoles (Queue<String> messages){
        Skin skin = new Skin(Gdx.files.internal(SKIN));
        int totalMSG = messages.size();

        for (int i = 0; i < totalMSG; i++){
            Label frame = new Label("",skin);
            frame.setAlignment(Align.top);
            String message = messages.poll();
            frame.setText(message);
            frame.setFontScale(1.5f);
            frame.setPosition(0, 750 + (messages.size()*30));
            frame.setAlignment(Align.topLeft);
            GameManager.get().getStage().addActor(frame);
        }

    }

    /**
     * remove an actor in a certain seconds
     * @param actorToRemove the actor to be removed
     * @param secsToWait seconds to be wait, interval
     */
    private static void removeAfterSec(Actor actorToRemove,float secsToWait) {
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                actorToRemove.remove();
            }
        }, secsToWait,0,0);
    }

    public boolean endGamePrompt(){

        Skin skin = new Skin(Gdx.files.internal(SKIN));
        Dialog dialog = new Dialog("Warning", skin,"dialog"){
            public void result(Object obj){
                promptResult = (Boolean)obj;
            }
        };

        dialog.text("Do you want to leave the server?");
        dialog.button("Yes",true);
        dialog.button("No",false);
        dialog.show(GameManager.get().getStage());

        return promptResult;
    }

}
