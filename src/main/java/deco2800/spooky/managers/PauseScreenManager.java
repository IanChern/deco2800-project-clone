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

/**
 * Game pause manage
 */
public class PauseScreenManager implements AbstractManager {

    private Stage stage;

    private Table pauseMenu;

    private TextureManager textureManager;

    private static final String DEF = "default";

    private static final String BG = "background";

    private SoundManager sound;

    /**
     * Constructor
     * @param stage the game stage
     */
    public PauseScreenManager(final ThomasGame game, Stage stage){
        this.stage = stage;
        this.pauseMenu = new Table();
        this.pauseMenu.setVisible(false);
        this.textureManager = new TextureManager();
        sound = GameManager.get().getManager(SoundManager.class);

        Image pauseMenuBackground = new Image(textureManager.getTexture("pauseMenuBackground"));
        this.pauseMenu.setBackground(pauseMenuBackground.getDrawable());
        // add background to it
        this.pauseMenu.pack();

        this.stage.addActor(this.pauseMenu);
        this.createBasicSkin();

        //make images for buttons
        Image resumeImage = new Image(textureManager.getTexture("resume"));
        Image newGameImage = new Image(textureManager.getTexture("newGameBtn"));
        Image mainMenuImage = new Image(textureManager.getTexture("mainMenuBtn"));
        Image musicLessImage = new Image(textureManager.getTexture("volumeUp"));
        Image musicMoreImage = new Image(textureManager.getTexture("volumeDown"));
        Image musicImage = new Image(textureManager.getTexture("music"));

        //make buttons
        ImageButton resumeBtn = new ImageButton(resumeImage.getDrawable());
        ImageButton newGameBtn = new ImageButton(newGameImage.getDrawable());
        ImageButton mainMenuBtn = new ImageButton(mainMenuImage.getDrawable());
        ImageButton musicSmallerBtn = new ImageButton(musicLessImage.getDrawable());
        ImageButton musicLouderBtn = new ImageButton(musicMoreImage.getDrawable());
        ImageButton musicBtn = new ImageButton(musicImage.getDrawable());

        resumeBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("resume")));
        resumeBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("resume-dark")));

        newGameBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("newGameBtn")));
        newGameBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("newGameBtn-dark")));

        mainMenuBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("mainMenuBtn")));
        mainMenuBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("mainMenuBtn-dark")));

        //Listeners for buttons clicked
        mainMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                game.getScreen().hide();
                GameManager.get().getManager(SoundManager.class).muteSound();
                GameManager.get().getManager(SoundManager.class).playSoundLoop("Temple Traitors.wav");
                game.setScreen(game.getMainMenuScreen());
            }
        });

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                game.getScreen().hide();
                GameManager.get().getManager(SoundManager.class).muteSound();
                GameManager.get().getManager(SoundManager.class).playSoundLoop("Temple Traitors.wav");
                game.setScreen(game.getMainMenuScreen()); //change this to connect to server
            }
        });

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(false);
                game.pause();
            }
        });

        musicLouderBtn.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               sound.louderSound();
           }
        });

        musicSmallerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sound.smallerSound();
            }
        });

        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(resumeBtn).fillX().uniformX();
        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(newGameBtn).fillX().uniformX();
        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(mainMenuBtn).fillX().uniformX();
        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(musicLouderBtn).fillX().uniformX();
        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(musicBtn).fillX().uniformX();
        this.pauseMenu.row().pad(5, 0, 5, 0);
        this.pauseMenu.add(musicSmallerBtn).fillX().uniformX();

        this.pauseMenu.setPosition(stage.getWidth() / 2 - 225, stage.getHeight() / 2 - 150);

        // set pause button
        this.createPauseBtn();
    }

    /**
     * position pause button
     */
    private void createPauseBtn() {
        Drawable pDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("game_pause_button")));
        ImageButton pauseButton = new ImageButton(pDrawable);
        pauseButton.setPosition(1100, 640);
        pauseButton.setSize(120, 40);
        pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("game_pause_button")));
        pauseButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("game_pause_button-dark")));
        stage.addActor(pauseButton);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.setVisible(true);
            }
        });

    }

    /**
     * set the pause table to be visible
     */
    public void setPauseTableVisible() {
        this.pauseMenu.setVisible(true);
    }

    /**
     * create basic skin for text buttons
     */
    private void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add(DEF, font);

        // the button height and width
        Pixmap pixmap = new Pixmap(100, 50, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(BG, new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable(BG, Color.GRAY);
        textButtonStyle.down = skin.newDrawable(BG, Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable(BG, Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable(BG, Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont(DEF);
        skin.add(DEF, textButtonStyle);
    }
}
