package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.spooky.GameScreen;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.*;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.SoundManager;
import deco2800.spooky.managers.TextureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Random;

public class CharacterMenuScreen extends Menus implements Screen {
    private static final Logger logger = LoggerFactory.getLogger(CharacterMenuScreen.class);
    // Set up default player
    private Character player = new Chris(0f, 0f, true);
    // set up the character choosing arrow
    private Image p1 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("p1"));

    private static final String DAMIEN = "damien";
    String selectedCharacter = DAMIEN;

    private static final String SELECTION  = "CharacterSelection.wav";

    public CharacterMenuScreen(final ThomasGame game) {
        super(game);
        game.setBatch(new SpriteBatch());

        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());

        Image uiBackground = new Image(GameManager.get().getManager(TextureManager.class).getTexture("uiBackground"));
        uiBackground.setFillParent(true);
        stage.addActor(uiBackground);

        Image uiTitle = new Image(GameManager.get().getManager(TextureManager.class).getTexture("uiTitle"));
        uiTitle.setPosition(70, 670);
        uiTitle.setSize(600, 75);
        stage.addActor(uiTitle);

        //p1 arrow
        p1.setPosition(65, 570);
        p1.setSize(100,100);
        stage.addActor(p1);

        // Character containers
        String[] cards = {"yellowCard", "purpleCard", "blueCard",
                "greenCard", "brownCard", "orangeCard"};

        for (int i = 0; i < 6; i++) {
            Image card = new Image(GameManager.get().getManager(TextureManager.class).getTexture(cards[i]));
            card.setPosition(10 + 212 * i, 10);
            card.setSize(200, 550);
            stage.addActor(card);
        }

        //Character images, changed to buttons for selecting characters
        String[] characters = {DAMIEN, "chris", "jane", "larry", "titus", "katie"};

        String[] characterTextures = {"damien_neutral_down", "chris_neutral_down",
                "jane_neutral_down", "larry_neutral_down", "titus_neutral_down",
                "katie_neutral_down"
        };

        String[] characterDown = {"damien-dark", "christopher-dark",
                "jane-dark", "larry-dark", "titus-dark",
                "katie-dark"
        };

        for (int i = 0; i < 6; i++) {
            Drawable characterDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                    .getManager(TextureManager.class).getTexture(characterTextures[i])));
            ImageButton character = new ImageButton(characterDrawable);
            character.setPosition(-75 + 212 * i, 180);
            character.setSize(384, 384);
            character.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                    .getManager(TextureManager.class).getTexture(characterTextures[i])));
            character.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                    .getManager(TextureManager.class).getTexture(characterDown[i])));
            stage.addActor(character);

            int finalI = i;
            character.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    p1.setPosition(character.getX() + 140, 570);
                    selectedCharacter = characters[finalI];
                    logger.info("Selected: " + selectedCharacter);
                    if(selectedCharacter.equals(DAMIEN)){
                        String[] sound = {"newAdventure.wav","haveDrink.wav"};
                        Random r = new Random();
                        int index = r.nextInt(sound.length);
                        GameManager.get().getManager(SoundManager.class).playSound(sound[index],0.8f);
                    }
                    if(selectedCharacter.equals("larry")){
                        String[] sound = {"dnd.wav","basement.wav"};
                        Random r = new Random();
                        int index = r.nextInt(sound.length);
                        GameManager.get().getManager(SoundManager.class).playSound(sound[index],0.6f);
                    }
                    if(selectedCharacter.equals("titus")){
                        String[] sound = {"byYourSide.wav","tryMe.wav"};
                        Random r = new Random();
                        int index = r.nextInt(sound.length);
                        GameManager.get().getManager(SoundManager.class).playSound(sound[index],0.5f);
                    }
                    if(selectedCharacter.equals("chris")){
                        String[] sound = {"goFast.wav","hereWeGo.wav"};
                        Random r = new Random();
                        int index = r.nextInt(sound.length);
                        GameManager.get().getManager(SoundManager.class).playSound(sound[index],0.7f);
                    }
                }
            });
        }

        //character names
        String[] names = {"deanName", "chrisName", "janeName",
                "larryName", "titusName", "katieName"};
        for (int i = 0; i < 6; i++) {
            Image name = new Image(GameManager.get().getManager(TextureManager.class).getTexture(names[i]));
            name.setPosition(20 + 212 * i, 160);
            name.setSize(180, 100);
            stage.addActor(name);
        }

        //back and start buttons
        backButton();
        startButton();

        //Katie stats
        Image LOWHEALTH_MAXSPEED2 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("low-health-max-speed"));
        LOWHEALTH_MAXSPEED2.setPosition(1115 , 50);
        LOWHEALTH_MAXSPEED2.setSize(100,100);
        stage.addActor(LOWHEALTH_MAXSPEED2);

        //Titus stats
        Image MAXHEALTH_LOWSPEED2 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("max-health-low-speed"));
        MAXHEALTH_LOWSPEED2.setPosition(903 , 50);
        MAXHEALTH_LOWSPEED2.setSize(100,100);
        stage.addActor(MAXHEALTH_LOWSPEED2);

        //Larry stats
        Image MIDHEALTH_MIDSPEED2 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("mid-health-mid-speed"));
        MIDHEALTH_MIDSPEED2.setPosition(691 , 50);
        MIDHEALTH_MIDSPEED2.setSize(85,100);
        stage.addActor(MIDHEALTH_MIDSPEED2);

        //Jane stats
        Image MAXHEALTH_LOWSPEED1 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("max-health-low-speed"));
        MAXHEALTH_LOWSPEED1.setPosition(479 , 50);
        MAXHEALTH_LOWSPEED1.setSize(100,100);
        stage.addActor(MAXHEALTH_LOWSPEED1);

        //Christopher stats
        Image LOWHEALTH_MAXSPEED1 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("low-health-max-speed"));
        LOWHEALTH_MAXSPEED1.setPosition(267 , 50);
        LOWHEALTH_MAXSPEED1.setSize(100,100);
        stage.addActor(LOWHEALTH_MAXSPEED1);

        //Desmond stats
        Image MIDHEALTH_MIDSPEED1 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("mid-health-mid-speed"));
        MIDHEALTH_MIDSPEED1.setPosition(55 , 50);
        MIDHEALTH_MIDSPEED1.setSize(85,100);
        stage.addActor(MIDHEALTH_MIDSPEED1);
    }

    private void backButton() {
        Drawable bDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        ImageButton bButton = new ImageButton(bDrawable);
        bButton.setPosition(820, 678);
        bButton.setSize(180, 60);
        bButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        bButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-dark")));
        bButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-small")));

        bButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().getManager(SoundManager.class).muteSound();
                GameManager.get().getManager(SoundManager.class).playSoundLoop("Temple Traitors.wav");
                game.setScreen(game.getMainMenuScreen().insScreen);
            }
        });
        stage.addActor(bButton);
    }

    private void startButton() {
        Drawable sDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("startButton")));
        ImageButton sButton = new ImageButton(sDrawable);
        sButton.setPosition(1030, 678);
        sButton.setSize(180, 60);
        sButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("startButton")));
        sButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("start-dark")));
        sButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("start-small")));
        stage.addActor(sButton);

        sButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().getManager(SoundManager.class).muteSound();
                GameManager.get().getManager(SoundManager.class).playSoundLoop("GamePlay Music.wav");
                // Select character based on the x position of p1
                switch (selectedCharacter) {
                    case "katie":
                        player = new Katie(0f, 0f, true);
                        logger.info("1");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    case "chris":
                        player = new Chris(0f, 0f, true);
                        logger.info("2");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    case "jane":
                        player = new Jane(0f, 0f, true);
                        logger.info("3");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    case "larry":
                        player = new Larry(0f, 0f, true);
                        logger.info("4");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    case "titus":
                        player = new Titus(0f, 0f, true);
                        logger.info("5");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    case DAMIEN:
                        player = new Damien(0f, 0f, true);
                        logger.info("6");
                        GameManager.get().getManager(SoundManager.class).playSound(SELECTION,1);
                        break;
                    default:
                        break;
                }
                try {
                    game.setScreen(new GameScreen(game, true, false, "host", "localhost"));
                } catch (UnknownHostException e) {
                    logger.error("Error: {}", e.toString());
                }
            }
        });
    }
    /**
     * Return this.player
     */
    public Character getPlayer(){
        return this.player;
    }
}
