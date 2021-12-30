package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.GameScreen;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.managers.ClientManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.networking.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Server connect/host screen
 */
public class MultiplayerScreen extends Menus implements Screen {
    private static final Logger logger = LoggerFactory.getLogger(MultiplayerScreen.class);
    private Skin skin;
    private String ip;
    private String username;
    LobbyScreen currentLobbyScreen;
    private Lobby currentLobby;

    /**
     * Create the multiplayer screen
     * @param game game warpper
     */
    public MultiplayerScreen(final ThomasGame game) {
        super(game);
        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());
        skin = GameManager.get().getSkin();

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("startBackground"));
        background.setFillParent(true);
        stage.addActor(background);

        // IP address textbox
        TextField hostIp = new TextField("Host IP", skin);
        hostIp.setPosition(82, 105);
        hostIp.setSize(575, 52);
        stage.addActor(hostIp);

        // username textbox
        TextField usernameBox = new TextField("Username or Host name", skin);
        usernameBox.setPosition(82, 175);
        usernameBox.setSize(575, 52);
        stage.addActor(usernameBox);

        // connect button
        Drawable connect = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("connectButton")));
        ImageButton connectB = new ImageButton(connect);
        connectB.setPosition(80, 30);
        connectB.setSize(180, 60);
        connectB.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("connectButton")));
        connectB.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("connect-dark")));
        connectB.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("connect-small")));
        stage.addActor(connectB);

        connectB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ip = hostIp.getText();
                username = usernameBox.getText();
                logger.info("Clients username: {}", username);
                logger.info("Entered IP: " + ip);

                // connect to server with name and IP
                if (GameManager.get().getManager(ClientManager.class).connectToHost(ip, username)) {
                    //lobby screen as client
                    ClientManager client = GameManager.get().getManager(ClientManager.class);
                    CharacterMultiplayer mpScreen = new CharacterMultiplayer(game, username, client);

                    while (currentLobby == null) {
                        currentLobby = GameManager.get().getManager(ClientManager.class).getClientLobby();
                    }
                    game.setScreen(mpScreen);
                }
            }
        });

        Drawable create = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("createButton")));
        ImageButton createB = new ImageButton(create);
        createB.setPosition(280, 30);
        createB.setSize(180, 60);
        createB.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("createButton")));
        createB.getStyle().imageOver = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("create-dark")));
        createB.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("create-small")));
        stage.addActor(createB);

        createB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ip = hostIp.getText();
                username = usernameBox.getText();

                logger.info("Host username: {}", username);
                logger.info("Creating lobby at: " + ip);

                //lobby screen as host
                try {
                    game.setScreen(new GameScreen(game, true, true, username, ip));
                    currentLobby = GameManager.get().getManager(ServerManager.class).gameLobby("localhost", username);
                } catch (UnknownHostException uhe) {
                    logger.warn("Host does not exist!");
                }
            }
        });

        draw(480,30,180,60);
    }

    public Lobby getCurrentLobby(){
        return this.currentLobby;
    }
}
