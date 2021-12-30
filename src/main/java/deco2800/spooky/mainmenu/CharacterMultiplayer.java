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
import deco2800.spooky.ThomasGame;
import deco2800.spooky.GameScreen;
import deco2800.spooky.managers.ClientManager;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.networking.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class CharacterMultiplayer extends Menus implements Screen {

    private static final Logger logger = LoggerFactory.getLogger(CharacterMultiplayer.class);

    // Set up default player
    private String playerCharacter = "damien";
    private int charIndex;
    private ClientManager currentClient;
    // set up the character choosing arrow
    private float p1X;
    private Image p1 = new Image(GameManager.get().getManager(TextureManager.class).getTexture("p1"));

    public CharacterMultiplayer(final ThomasGame game, String username, ClientManager client) {
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
        String[] characters = { "damien", "chris", "jane", "larry", "titus", "katie"};

        String[] characterTextures = {"damien_neutral_down", "chris_neutral_down",
                "jane_neutral_down", "larry_neutral_down", "titus_neutral_down",
                "katie_neutral_down"
        };

        for (int i = 0; i < 6; i++) {
            Drawable characterDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                    .getManager(TextureManager.class).getTexture(characterTextures[i])));
            ImageButton character = new ImageButton(characterDrawable);
            character.setPosition(-75 + 212 * i, 180);
            character.setSize(384, 384);
            stage.addActor(character);

            int finalI = i;
            character.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    p1.setPosition(character.getX() + 140, 570);
                    playerCharacter = characters[finalI];
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
        draw(820, 678, 180, 60);

        Drawable ready = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("startButton")));
        ImageButton rButton = new ImageButton(ready);
        rButton.setPosition(1030, 678);
        rButton.setSize(180, 60);
        stage.addActor(rButton);

        rButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Select character based on the x position of p1
                logger.info("Selected: " + playerCharacter);
                client.selectCharacter(playerCharacter);
                client.sendReadyMessage(true);
                //logger.info("Player " + username + " ready");

                // disconnect and reconnect
                //client.disconnect();
                try {
                    game.setScreen(new GameScreen(game, false, true, username, "localhost"));
                } catch (UnknownHostException uhe) {
                    // squish
                }
            }
        });

    }
}