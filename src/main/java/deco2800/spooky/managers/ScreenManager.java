package deco2800.spooky.managers;

import deco2800.spooky.GameScreen;

public class ScreenManager implements AbstractManager {

    /* Represents the current screen displayed in the game */
    private GameScreen currentScreen;

    /**
     * @return the current screen
     */
    public GameScreen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Sets the current screen
     * @param screen to set
     */
    public void setCurrentScreen(GameScreen screen) {
        currentScreen = screen;
    }
}
