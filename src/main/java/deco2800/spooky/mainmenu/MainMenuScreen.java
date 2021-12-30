package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.ThomasGame;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.SoundManager;
import deco2800.spooky.managers.TextureManager;

public class MainMenuScreen extends Menus implements Screen {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuScreen.class);
    // Create public variable charScreen
    private CharacterMenuScreen charScreen;
    InstructionScreen insScreen;
    MultiplayerScreen clientScreen;

    /** create a loading screen **/
    boolean isLoading = true;
    /** start time for starting load **/
    long startTime = System.currentTimeMillis();
    private static final String CHRIS = "chris_walking_right1";
    private static final String JANE = "jane_walking_right1";
    private static final String DAMIEN = "damien_walking_right1";

    // setup sprites for loading
    String[] loadingTextures = {"loading5","loading25","loading50","loading75","loading100"};
    String[] chrisTextures = {CHRIS,"chris_walking_right2",CHRIS,"chris_walking_right2",CHRIS};
    String[] janeTextures = {JANE,"jane_walking_right2",JANE,"jane_walking_right2",JANE};
    String[] damienTextures = {DAMIEN,"damien_walking_right2",DAMIEN,"damien_walking_right2",DAMIEN};

    /**
     * The constructor of the MainMenuScreen
     * @param game the Iguana Chase Game to run
     */
    public MainMenuScreen(final ThomasGame game) {
        super(game);

        charScreen =  new CharacterMenuScreen(game);
        clientScreen = new MultiplayerScreen(game);
        insScreen = new InstructionScreen(game);

        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
        background.setFillParent(true);
        stage.addActor(background);

        Image title = new Image(GameManager.get().getManager(TextureManager.class).getTexture("mainTitle"));
        title.setPosition(500, 500);
        title.setSize(600, 50);
        stage.addActor(title);

        GameManager.get().getManager(SoundManager.class).playSoundLoop("Temple Traitors.wav");

        Drawable singleDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("singleButton")));
        ImageButton newGameBtn = new ImageButton(singleDrawable);
        newGameBtn.setPosition(10, 120);
        newGameBtn.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("singleButton")));
        newGameBtn.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("single-dark")));
        newGameBtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("single-small")));
        stage.addActor(newGameBtn);

        Drawable serverDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("serverButton")));
        ImageButton connectToServerButton = new ImageButton(serverDrawable);
        connectToServerButton.setPosition(10, 15);
        connectToServerButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("serverButton")));
        connectToServerButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("server-dark")));
        connectToServerButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("server-small")));
        stage.addActor(connectToServerButton);

        connectToServerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(insScreen);
                game.getMainMenuScreen().insScreen.nButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(game.getMainMenuScreen().clientScreen);
                        logger.info("Client Screen.");
                    }
                });
            }
        });

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(insScreen);
                game.getMainMenuScreen().insScreen.nButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameManager.get().getManager(SoundManager.class).muteSound();
                        GameManager.get().getManager(SoundManager.class).playSoundLoop("UIMenuSelectionMusic.wav");
                        game.setScreen(game.getMainMenuScreen().charScreen);
                    }
                });
            }
        });
    }

    public CharacterMenuScreen getCharScreen() {
        return(charScreen);
    }

    /**
     * Renders the menu
     * @param delta
     */
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isLoading) {
            if (System.currentTimeMillis() - startTime >= 2000) {
                isLoading = false;
            }


            Stage loadingStage = new Stage(new ExtendViewport(1280, 720), game.getBatch());


            int loadingIndex = (int) (System.currentTimeMillis() - startTime) / (2000 / loadingTextures.length);
            try {
                Image loadingImage = new Image(GameManager.get().
                        getManager(TextureManager.class).getTexture(loadingTextures[loadingIndex]));
                loadingStage.addActor(loadingImage);
                Image chrisWalking = new Image(GameManager.get().getManager(TextureManager.class).getTexture(chrisTextures[loadingIndex]));
                chrisWalking.setPosition(0, 100);
                loadingStage.addActor(chrisWalking);
                Image janeWalking = new Image(GameManager.get().getManager(TextureManager.class).getTexture(janeTextures[loadingIndex]));
                janeWalking.setPosition(150, 100);
                loadingStage.addActor(janeWalking);
                Image damienWalking = new Image(GameManager.get().getManager(TextureManager.class).getTexture(damienTextures[loadingIndex]));
                damienWalking.setPosition(300, 100);
                loadingStage.addActor(damienWalking);
            } catch (ArrayIndexOutOfBoundsException e) {
                loadingStage.clear();
            }
            loadingStage.draw();



        } else {
            stage.act(delta);
            stage.draw();
        }

    }
}
