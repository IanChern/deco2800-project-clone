package deco2800.spooky.entities;

import deco2800.spooky.entities.Creep.Guard;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.tasks.PlayerMovement;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.JennaWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuardTest {

    Guard guard;

    @Before
    public void setUp() {
        guard = new Guard(0, 0, false);
    }

    @Test
    public void testConstructor(){
        assertEquals(guard.getStartCol(), 0,0.000001f);
        assertEquals(guard.getStartRow(), 0,0.000001f);
        assertEquals(guard.getCol(), 0,0.000001f);
        assertEquals(guard.getRow(), 0,0.000001f);
        assertEquals(guard.getHealth(), 200.0,0.000001f);
    }


    @Test
    public void testAttack() {
        Character chris = new Chris(0f,0f,true);
        chris.setHealth(100);

        for (int i = 0; i < 1000; i++) {
            guard.attackPlayer(chris);
        }

        assertTrue(chris.getHealth() < 100);
    }


}
