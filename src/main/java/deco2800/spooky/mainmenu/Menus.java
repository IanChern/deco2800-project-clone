package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.spooky.ThomasGame;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.SoundManager;
import deco2800.spooky.managers.TextureManager;

public abstract class Menus {
    Stage stage;
    final ThomasGame game;

    Menus(final ThomasGame game) {
        this.game = game;
    }

    void draw(int x, int y, int width, int height) {
        Drawable bDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        ImageButton bButton = new ImageButton(bDrawable);
        bButton.setPosition(x, y);
        bButton.setSize(width, height);
        bButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        bButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-dark")));
        bButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-small")));
        stage.addActor(bButton);

        bButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(game.getMainMenuScreen());
            }
        });
    }

    void drawGameOverBtn(int x, int y, int width, int height) {
        Drawable bDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        ImageButton bButton = new ImageButton(bDrawable);
        bButton.setPosition(x, y);
        bButton.setSize(width, height);
        bButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        bButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-dark")));
        bButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("back-small")));
        stage.addActor(bButton);

        bButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().getManager(SoundManager.class).muteSound();
                GameManager.get().getManager(SoundManager.class).playSoundLoop("Temple Traitors.wav");
                game.setScreen(game.getMainMenuScreen());
            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Pauses the screen
     */
    public void pause() {
        //do nothing
    }

    /**
     * Resumes the screen
     */
    public void resume() {
        //do nothing
    }

    /**
     * Hides the screen
     */
    public void hide() {
        //do nothing
    }

    /**
     * Resizes the main menu stage to a new width and height
     * @param width the new width for the menu stage
     * @param height the new width for the menu stage
     */
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Renders the menu
     * @param delta
     */
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();


    }

    /**
     * Disposes of the stage that the menu is on
     */
    public void dispose() {
        stage.dispose();
    }
}
