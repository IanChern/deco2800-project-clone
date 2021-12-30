package deco2800.spooky.entities;

import deco2800.spooky.entities.Creep.Guard;
import deco2800.spooky.entities.Creep.Mummy;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MummyTest {

    Mummy mummy;

    @Before
    public void setUp() {
        mummy = new Mummy(0, 0, false);
    }

    @Test
    public void testConstructor(){
        assertEquals(mummy.getCol(), 0,0.000001f);
        assertEquals(mummy.getRow(), 0,0.000001f);
        assertEquals(mummy.getHealth(), 50,0.000001f);
    }

    @Test
    public void testRandomDirection() {
        Mummy mummy1 = mock(Mummy.class);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int direction = random.nextInt(6);
            assertTrue(direction < 6);
            assertTrue(direction > -1);
            mummy1.randomMove(direction);
        }

        verify(mummy1, never()).mummyMove(0f,0f);
    }
}
