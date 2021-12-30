package deco2800.spooky.entities;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import deco2800.spooky.worlds.JennaWorld;

public class WeaponTest {

        //    private Character player = new Assassin(0,0,true);
        Chris player1 = new Chris(0f, 0f, true);
        Character mockPlayer = mock(Character.class);
        JennaWorld mockWorld = mock(JennaWorld.class);

        private Character player = new Damien(0,0,true);
        private Character player2 = new Chris(0,3, false);
        private Character player3 = new Jane(4,5,false);
        private Character player4 = new Chris(7,5,false);

        private Character player5 = new Damien(4,6,false);
        private Character player6 = new Jane(-1,-1,false);
        private Character player7 = new Chris(-2,-1,false);
        private Character player8 = new Damien(-1,0,false);
        //Please note to use headless application with Character as onTick uses camera

        @Test
        public void createWeapon() {
                MeleeWeapon axe = new MeleeWeapon(0f, 1f, MeleeType.AXE);
                MeleeWeapon sword = new MeleeWeapon(0f, 2f, MeleeType.SWORD);
                MeleeWeapon dagger = new MeleeWeapon(0f, 2f, MeleeType.DAGGER);
                MeleeWeapon handbag = new MeleeWeapon(0f, 2f, MeleeType.HANDBAG);
                assertEquals(MeleeType.AXE, axe.getName());
                assertEquals(MeleeType.SWORD, sword.getName());
                assertEquals(MeleeType.DAGGER, dagger.getName());
                assertEquals(MeleeType.HANDBAG, handbag.getName());
        }

}
