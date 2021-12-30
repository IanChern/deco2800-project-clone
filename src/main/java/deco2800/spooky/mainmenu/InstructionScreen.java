package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.spooky.ThomasGame;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;


public class InstructionScreen extends Menus implements Screen{
    private Drawable nDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("nextButton")));
    ImageButton nButton = new ImageButton(nDrawable);


    public InstructionScreen(final ThomasGame game) {

        super(game);
        game.setBatch(new SpriteBatch());

        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());

        Image uiBackground = new Image(GameManager.get().getManager(TextureManager.class).getTexture("bgIns"));
        uiBackground.setFillParent(true);
        stage.addActor(uiBackground);
        draw(860,640,147,55);
        
        nButton.setPosition(1070, 640);
        nButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("nextButton")));
        nButton.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("next-dark")));
        nButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("next-small")));
        //nButton setSize(390, 130)
        stage.addActor(nButton);

        Image title = new Image(GameManager.get().getManager(TextureManager.class).getTexture("titleIns"));
        //title setSize(1200, 600)
        title.setPosition(40, 630);
        stage.addActor(title);

        Image bigBox = new Image(GameManager.get().getManager(TextureManager.class).getTexture("boxIns"));
        bigBox.setSize(1200, 600);
        bigBox.setPosition(30, 20);
        stage.addActor(bigBox);

        Image smallBox = new Image(GameManager.get().getManager(TextureManager.class).getTexture("smallIns"));
        smallBox.setSize(1100, 400);
        smallBox.setPosition(80, 40);
        stage.addActor(smallBox);

        Image story = new Image(GameManager.get().getManager(TextureManager.class).getTexture("storyIns"));
        story.setSize(1180, 150);
        story.setPosition(40,450);
        stage.addActor(story);

        Image role = new Image(GameManager.get().getManager(TextureManager.class).getTexture("roleIns"));
        role.setSize(475, 360);
        role.setPosition(80, 70);
        stage.addActor(role);

        Image control = new Image(GameManager.get().getManager(TextureManager.class).getTexture("controlIns"));
        control.setSize(550, 380);
        control.setPosition(572, 55);
        stage.addActor(control);

        Image or = new Image(GameManager.get().getManager(TextureManager.class).getTexture("orIns"));
        or.setSize(300, 200);
        or.setPosition(172, 135);
        stage.addActor(or);
    }
}
