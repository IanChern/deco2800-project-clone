package deco2800.spooky.managers;

import org.junit.Test;

public class PopUpManagerTest {

    PopUpManager manager;
    @Test(expected = NullPointerException.class)
    public void displayPopUpMessage() {
        manager.displayPopUpMessage("Indicative","AAA");
    }
}