package deco2800.spooky.entities;

import deco2800.spooky.managers.AnimationManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppearanceTest {

    private Appearance chrisApp;
    private Appearance invalidApp;

    @Before
    public void setUp() {
        this.chrisApp = new Appearance("damien");
        this.invalidApp = new Appearance(null);
    }

    @Test
    public void testConstructor() {
        Appearance damainApp = new Appearance("Hair", "Skin", "Cloth");
        assertEquals("Hair", damainApp.getHair());
        assertEquals("Skin", damainApp.getSkin());
        assertEquals("Cloth", damainApp.getCloth());
        assertEquals("damien_standing", this.chrisApp.getDefaultAppearance());
        assertEquals("spacman_blue", chrisApp.getName());
        assertEquals("damien_standing", this.invalidApp.getDefaultAppearance());
    }

    @Test
    public void testSetDefaultAppearance() {
        this.chrisApp.setDefaultAppearance("damien");
        this.invalidApp.setDefaultAppearance("damien");
        assertEquals("damien_standing", this.chrisApp.getDefaultAppearance());
        assertEquals("damien_standing", this.invalidApp.getDefaultAppearance());
    }

    @Test
    public void testGetNeutralDirectionAppearance() {
        assertEquals("damien_neutral_up", this.chrisApp.getNeutralDirectionAppearance("up"));
        assertEquals("damien_standing", this.chrisApp.getNeutralDirectionAppearance("upup"));
    }

    @Test
    public void setters() {
        Appearance damainApp = new Appearance("Hair", "Skin", "Cloth");
        damainApp.setHair("NULL");
        damainApp.setSkin("FAILED");
        damainApp.setCloth("NAKED");
        assertEquals("NULL", damainApp.getHair());
        assertEquals("FAILED", damainApp.getSkin());
        assertEquals("NAKED", damainApp.getCloth());
    }
}
