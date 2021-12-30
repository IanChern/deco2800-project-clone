package deco2800.spooky.entities;

import deco2800.spooky.entities.Creep.CreepFactory;
import deco2800.spooky.entities.Creep.Guard;
import deco2800.spooky.entities.Creep.Mummy;
import deco2800.spooky.entities.Items.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreepFactoryTest {

    private CreepFactory creepFactory;

    @Before
    public void setUp() {
        this.creepFactory = new CreepFactory();
    }

    @Test
    public void testConstructor(){
        List emptyList = new ArrayList();
        assertEquals(0, creepFactory.getNumCreeps());
        assertEquals(emptyList, creepFactory.getCreeps());
        assertEquals(emptyList, creepFactory.getMummies());
        assertEquals(emptyList, creepFactory.getGuards());
    }

    @Test
    public void testCreateCreeps() {
        //Check the factory returns the correct item
        Mummy mummy = new Mummy(0,0,false);
        Mummy factoryMummy = (Mummy)creepFactory.createCreep("Mummy", 0,0);

        assertEquals(mummy.getClass(), factoryMummy.getClass());
        assertEquals(mummy.getHealth(), factoryMummy.getHealth());
        assertTrue(mummy.getCol() == factoryMummy.getCol());
        assertTrue(mummy.getRow() == factoryMummy.getRow());

        Guard guard = new Guard(0,0, false);
        Guard factoryGuard = (Guard)creepFactory.createCreep("Guard", 0,0);

        assertEquals(guard.getClass(), factoryGuard.getClass());
        assertEquals(guard.getHealth(), factoryGuard.getHealth());
        assertTrue(guard.getStartCol() == factoryGuard.getStartCol());
        assertTrue(guard.getStartRow() == factoryGuard.getStartRow());
        assertTrue(guard.getCol() == factoryGuard.getCol());
        assertTrue(guard.getRow() == factoryGuard.getRow());

        assertEquals(null, creepFactory.createCreep("NotAValidItem",0,0));
    }
}
