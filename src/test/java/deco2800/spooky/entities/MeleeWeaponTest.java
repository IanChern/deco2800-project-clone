package deco2800.spooky.entities;

import deco2800.spooky.managers.GameManager;
import deco2800.spooky.worlds.TestWorld;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeleeWeaponTest {

    @Test
    public void setHolder() {
        MeleeWeapon weapon = new MeleeWeapon(0,0, MeleeType.AXE);
        assertEquals(null, weapon.getHolder());
        Character character = new Chris(0, 0, true);
        weapon.setHolder(character);
        assertEquals(character, weapon.getHolder());
    }

    @Test
    public void setPower() {
        MeleeWeapon weapon = new MeleeWeapon(0,0,MeleeType.SWORD);
        assertEquals(6, weapon.getPower());
        weapon.setPower(4);
        assertEquals(4, weapon.getPower());
    }

    @Test
    public void setRange() {
        MeleeWeapon weapon = new MeleeWeapon(0,0,MeleeType.AXE);
        assertEquals(0.5f, weapon.getRange(), 0);
        weapon.setRange(3.4f);
        assertEquals(3.4f, weapon.getRange(), 0);
    }

    @Test
    public void setFrequency() {
        MeleeWeapon weapon = new MeleeWeapon(0,0,MeleeType.AXE);
        assertEquals(100, weapon.getFrequency());
        weapon.setFrequency(300);
        assertEquals(300, weapon.getFrequency());
    }

    @Test
    public void getEntityDistance() {
        MeleeWeapon weapon1 = new MeleeWeapon(0,0,MeleeType.AXE);
        MeleeWeapon weapon2 = new MeleeWeapon(1,1, MeleeType.SWORD);
        assertEquals(1.4142, weapon1.getEntityDistance(weapon2), 0.0001);
    }

    @Test
    public void notMelee() {
        MeleeWeapon weapon1 = new MeleeWeapon(0,0,MeleeType.HANDBAG);
        assertEquals(0.3f, weapon1.getRange(), 0.0f);
        assertEquals(2, weapon1.getPower());
        assertEquals(250, weapon1.getFrequency());
    }

    @Test
    public void onTick() {
        GameManager.get().setWorld(new TestWorld());
        Character chris = new Chris(0f,0f,true);
        MeleeWeapon weapon = new MeleeWeapon(0,0, MeleeType.AXE);
        GameManager.get().getWorld().addEntity(chris);
        GameManager.get().getWorld().addEntity(weapon);
        weapon.onTick(0);
        assertEquals(1, chris.getWeapons().size());
    }
}