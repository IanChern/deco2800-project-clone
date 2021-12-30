package deco2800.spooky.managers;

import com.badlogic.gdx.audio.Sound;
import deco2800.spooky.ThomasGame;
import deco2800.spooky.gameStatus.StatusType;
import deco2800.spooky.mainmenu.GameEndScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Manager is for making the game to different statuses
 */
public class StatusManager implements AbstractManager {

    private static final Logger logger = LoggerFactory.getLogger(StatusManager.class);
    ThomasGame game;
    public StatusManager(ThomasGame game) {
        this.game = game;
    }

    private StatusType currentStatus;


    /**
     * start up a new game, set status
     */
    public void newGame () {
        currentStatus = StatusType.PREPARATION;
        logger.info("{}", currentStatus);
    }

    /**
     * move to next status based on current status
     */
    public void nextStatus () {
        switch (currentStatus) {
            case PREPARATION:
                currentStatus = StatusType.START;
                break;
            case START:
                currentStatus = StatusType.MID;
                break;
            case MID:
                currentStatus = StatusType.FINAL;
                break;
            case FINAL:
                currentStatus = StatusType.END;
                break;
            default:
                break;
        }
        logger.info("{}", currentStatus);
    }

    /**
     * get current status
     * @return current status
     */
    public StatusType getCurrentStatus() {
        return currentStatus;
    }

    /**
     * set current status
     * @param newStatus new status to set
     */
    public void setCurrentStatus(StatusType newStatus) {
        currentStatus = newStatus;
    }

    /**
     * game end, need to be implemented in the future
     */
    public void endTheGame (Boolean youWin) {
        GameManager.get().getManager(SoundManager.class).muteSound();
        GameManager.get().getManager(SoundManager.class).playSoundLoop("GameOver Music.wav");
        game.setScreen(new GameEndScreen(game,false,youWin));
    }

}
