package deco2800.spooky.mainmenu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.SoundManager;
import deco2800.spooky.managers.TextureManager;

public class GameEndScreen extends Menus implements Screen{

    public GameEndScreen(final ThomasGame game, boolean isHost, Boolean youWin){
        super(game);
        game.setBatch(new SpriteBatch());

        stage = new Stage(new ExtendViewport(1280, 720), game.getBatch());

        Image gameOverBackground = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_background"));
        gameOverBackground.setFillParent(true);
        stage.addActor(gameOverBackground);

        if (youWin) {
            Image gameOverVictoryIcon = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_victory_icon"));
            gameOverVictoryIcon.setPosition(450, 670);
            gameOverVictoryIcon.setSize(350, 70);
            stage.addActor(gameOverVictoryIcon);

            Image gameOverWinIcon = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_win_icon"));
            gameOverWinIcon.setPosition(1060, 670);
            gameOverWinIcon.setSize(170, 70);
            stage.addActor(gameOverWinIcon);
        } else {
            Image gameOverGameOverIcon = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_game_over_icon"));
            gameOverGameOverIcon.setPosition(450, 670);
            gameOverGameOverIcon.setSize(350, 70);
            stage.addActor(gameOverGameOverIcon);

            Image gameOverLoseIcon = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_lose_icon"));
            gameOverLoseIcon.setPosition(1060, 670);
            gameOverLoseIcon.setSize(170, 70);
            stage.addActor(gameOverLoseIcon);
        }
        
        Image gameOverCharacterFrame = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_character_frame"));
        gameOverCharacterFrame.setPosition(250, 260);
        gameOverCharacterFrame.setSize(720, 350);
        stage.addActor(gameOverCharacterFrame);

        Image gameOverPerformanceIcon = new Image(GameManager.get().getManager(TextureManager.class).getTexture("gameover_performance_icon"));
        gameOverPerformanceIcon.setPosition(670, 520);
        gameOverPerformanceIcon.setSize(240, 30);
        stage.addActor(gameOverPerformanceIcon);

        Image chris = new Image(GameManager.get().getManager(TextureManager.class).getTexture("chris_neutral_down"));
        chris.setPosition(250, 270);
        chris.setSize(384, 384);
        stage.addActor(chris);

        Image cName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("chrisName"));
        cName.setPosition(345, 240);
        cName.setSize(190, 100);
        stage.addActor(cName);

        // Character containers

        String gameOverOtherFrame = "gameover_other_player_frame";
        Image otherCharacter1Frame = new Image(GameManager.get().getManager(TextureManager.class).getTexture(gameOverOtherFrame));
        otherCharacter1Frame.setPosition(40, 30);
        otherCharacter1Frame.setSize(210, 190);
        stage.addActor(otherCharacter1Frame);

        Image otherCharacter2Frame = new Image(GameManager.get().getManager(TextureManager.class).getTexture(gameOverOtherFrame));
        otherCharacter2Frame.setPosition(290, 30);
        otherCharacter2Frame.setSize(210, 190);
        stage.addActor(otherCharacter2Frame);

        Image otherCharacter3Frame = new Image(GameManager.get().getManager(TextureManager.class).getTexture(gameOverOtherFrame));
        otherCharacter3Frame.setPosition(540, 30);
        otherCharacter3Frame.setSize(210, 190);
        stage.addActor(otherCharacter3Frame);

        Image otherCharacter4Frame = new Image(GameManager.get().getManager(TextureManager.class).getTexture(gameOverOtherFrame));
        otherCharacter4Frame.setPosition(790, 30);
        otherCharacter4Frame.setSize(210, 190);
        stage.addActor(otherCharacter4Frame);

        Image otherCharacter5Frame = new Image(GameManager.get().getManager(TextureManager.class).getTexture(gameOverOtherFrame));
        otherCharacter5Frame.setPosition(1040, 30);
        otherCharacter5Frame.setSize(210, 190);
        stage.addActor(otherCharacter5Frame);

        
        
        
        //Other Character images
        Image damien = new Image(GameManager.get().getManager(TextureManager.class).getTexture("damien_neutral_down"));
        damien.setPosition(70, 70);
        damien.setSize(150, 150);
        stage.addActor(damien);

        Image jane = new Image(GameManager.get().getManager(TextureManager.class).getTexture("jane_neutral_down"));
        jane.setPosition(320, 70);
        jane.setSize(150, 150);
        stage.addActor(jane);

        Image larry = new Image(GameManager.get().getManager(TextureManager.class).getTexture("larry_neutral_down"));
        larry.setPosition(570, 70);
        larry.setSize(150, 150);
        stage.addActor(larry);

        Image titus = new Image(GameManager.get().getManager(TextureManager.class).getTexture("titus_neutral_down"));
        titus.setPosition(820, 70);
        titus.setSize(150, 150);
        stage.addActor(titus);

        Image katie = new Image(GameManager.get().getManager(TextureManager.class).getTexture("katie_neutral_down"));
        katie.setPosition(1070, 70);
        katie.setSize(150, 150);
        stage.addActor(katie);

        //character names
        Image dName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("deanName"));
        dName.setPosition(70, 30);
        dName.setSize(150, 65);
        stage.addActor(dName);

        

        Image jName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("janeName"));
        jName.setPosition(320, 30);
        jName.setSize(150, 65);
        stage.addActor(jName);

        Image lName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("larryName"));
        lName.setPosition(570, 30);
        lName.setSize(150, 65);
        stage.addActor(lName);

        Image tName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("titusName"));
        tName.setPosition(820, 30);
        tName.setSize(150, 65);
        stage.addActor(tName);

        Image kName = new Image(GameManager.get().getManager(TextureManager.class).getTexture("katieName"));
        kName.setPosition(1070, 30);
        kName.setSize(150, 65);
        stage.addActor(kName);

        //Draw buttons
        drawGameOverBtn(50,678,180,60);
    }
}