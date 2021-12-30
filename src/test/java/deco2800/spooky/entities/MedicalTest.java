package deco2800.spooky.entities;

import deco2800.spooky.entities.Items.Bandage;
import deco2800.spooky.entities.Items.MedKit;
import deco2800.spooky.entities.Items.Medical;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class MedicalTest {

    private MedKit medKit;
    private Bandage bandage;
    private Chris chris;

    @Before
    public void setUp() {
        this.medKit = new MedKit(0,0);
        this.bandage = new Bandage(0,0);
        this.chris = new Chris(0,0,true);
    }

    @Test
    public void testConstructor(){
        assertEquals("MedKit", this.medKit.getItemDesc());
        assertEquals("Bandage", this.bandage.getItemDesc());
    }

    @Test
    public void testUseMedical(){
        //Test if health is correctly added to player
        this.chris.setHealth(2);
        this.bandage.use(chris);
        assertEquals(3, chris.getHealth());

        this.chris.setHealth(2);
        this.medKit.use(chris);
        assertEquals(4, chris.getHealth());

        //Test if item is "used"
        assertTrue(bandage.isUsed());
        assertTrue(medKit.isUsed());

        //Test for silent fail if already used
        this.chris.setHealth(3);
        this.bandage.use(chris);
        this.medKit.use(chris);
        assertEquals(3, chris.getHealth());
    }

    @Test
    public void use() {
        Character chris = new Chris(0f, 0f, true);
        Medical med = new MedKit(0f, 0f);

        med.setHealthGain(100);
        med.setUsed(false);
        chris.setHealth(8);
        med.use(chris);
        assertEquals(8, chris.getHealth());

        med.setHealthGain(100);
        med.setUsed(false);
        chris.setHealth(5);
        med.use(chris);
        assertEquals(6, chris.getHealth());

        chris.setHealth(2);
        med.setUsed(false);
        med.setHealthGain(2);
        med.use(chris);
        assertEquals(4, chris.getHealth());

        chris.setHealth(-100);
        med.setUsed(false);
        med.use(chris);
        assertEquals(-100, chris.getHealth());
    }

    @Test
    public void rarity() {
        Medical med = new MedKit(0f, 0f);
        med.setRarity(1.0f);
        assertEquals(1.0f, med.getRarity(), 0.0f);
    }

    @Test
    public void quantity() {
        Medical med = new MedKit(0f, 0f);
        med.setQuantity(100);
        assertEquals(100, med.getQuantity());
        med.setType("med");
        assertEquals("med", med.getType());
    }

    @Test
    public void spin() {
        MedKit med = new MedKit(0f, 0f);
        med.spin();
    }
}
