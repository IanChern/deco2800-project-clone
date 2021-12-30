package deco2800.spooky.tasks;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantFireTest {

    @Test
    public void start() {
        ConstantFire constantFire = new ConstantFire();
        assertFalse(constantFire.getFlag());
        constantFire.start();
        assertTrue(constantFire.getFlag());
    }

    @Test
    public void terminate() {
        ConstantFire constantFire = new ConstantFire();
        constantFire.start();
        assertTrue(constantFire.getFlag());
        constantFire.terminate();
        assertFalse(constantFire.getFlag());
    }
}