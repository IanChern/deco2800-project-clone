package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Screen;
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
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.networking.Lobby;
import deco2800.spooky.networking.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Screen for the lobby, handles all of the display for in game lobby
 */
public class LobbyScreen extends Menus implements Screen {
    private static final Logger logger = LoggerFactory.getLogger(LobbyScreen.class);
    private String[] slotsTexture = {"p1Label", "p2Label", "p3Label", "p4Label", "p5Label", "p6Label"};
    private Drawable mummyDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
            .getManager(TextureManager.class).getTexture("mummyLobby")));
    public final ImageButton mummy = new ImageButton(mummyDrawable);



    /**
     * Public constructor for a lobby screen
     * @param game game wrapper
     * @param isHost is this lobby screen a host's screen or a client's screen
     * @param lobby lobby data
     */
    public LobbyScreen(final ThomasGame game, boolean isHost, Lobby lobby) {
        super(game);
        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("backgroundLobby"));
        background.setFillParent(true);
        stage.addActor(background);

        // Use the mummy as a ready button
        mummy.setPosition(455, 10);
        mummy.setSize(370,700);
        stage.addActor(mummy);

        Image iconLobby = new Image(GameManager.get().getManager(TextureManager.class).getTexture("iconLobby"));
        iconLobby.setPosition(122, 560);
        iconLobby.setSize(200,60);
        stage.addActor(iconLobby);

        Image joined = new Image(GameManager.get().getManager(TextureManager.class).getTexture("joinedLobby"));
        joined.setPosition(880, 560);
        joined.setSize(370,60);
        stage.addActor(joined);



        Drawable bDrawable = new TextureRegionDrawable(new TextureRegion(GameManager.get()
                .getManager(TextureManager.class).getTexture("backButton")));
        ImageButton bButton = new ImageButton(bDrawable);
        bButton.setPosition(20, 678);
        bButton.setSize(180, 60);
        stage.addActor(bButton);

        bButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainMenuScreen().clientScreen);
            }
        });

        for (int i = lobby.getMaxPlayers() - 1; i >= 0; i--) {
            Image oCard = new Image(GameManager.get().getManager(TextureManager.class).getTexture("orangeCard"));
            oCard.setPosition(20+840* (i % 2), 30 + 180 * (i / 2) );
            oCard.setSize(400, 150);
            stage.addActor(oCard);
        }

        mummy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Image ready = new Image(GameManager.get().getManager(TextureManager.class).getTexture("readyLobby"));
                ready.setPosition(240, 400);
                ready.setSize(174,58);
                stage.addActor(ready);
                if (isHost) {
                    GameManager.get().getLobby().getPlayers().get(0).getReady(true);
                    logger.info("Player [" + lobby.getPlayers().get(0).getUsername() +"] is ready");
                }
            }
        });

        for (Player player : lobby.getPlayers()) {
            logger.info("Player " + player.getUsername() + (isHost ? " Host" : " Client") + " in lobby");
        }

        showPlayers(lobby);

    }

    /**
     * Show all connected players in lobby
     * @param lobby lobby data
     */
    public void showPlayers(Lobby lobby) {
        for (int i = 0; i < lobby.numOfPlayers(); i++) {
            Image index = new Image(GameManager.get().getManager(TextureManager.class).getTexture(slotsTexture[i]));
            index.setPosition(30 + 840* (i % 2), 430 - 180 * (i / 2) );
            index.setSize(70, 58);
            stage.addActor(index);
        }
        this.setUpReady(lobby);
    }

    public void setUpReady(Lobby lobby){
        ArrayList<Player> players = new ArrayList<>(lobby.getPlayers());
        for(Player p:players){
            p.changeId(players.indexOf(p));
            if (!p.isReadyGame()){
                mummy.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        p.getReady(true);
                        lobby.addReadyPlayer(p);
                        game.getMainMenuScreen().clientScreen.currentLobbyScreen.showReady(lobby);
                    }
                });
            }
        }
    }

    public void showReady(Lobby lobby){
        Image ready = new Image(GameManager.get().getManager(TextureManager.class).getTexture("readyLobby"));
        for (Player p:lobby.getReadyPlayers()){
            ready.setPosition(240 + 840* (p.getId() % 2), 430 - 180 * (p.getId() / 2));
            ready.setSize(174,58);
        }
        stage.addActor(ready);
    }
}
