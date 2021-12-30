package deco2800.spooky.entities;

import com.badlogic.gdx.Gdx;
import deco2800.spooky.entities.Items.*;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.worlds.TestWorld;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.InstanceOf;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class ItemFactoryTest {
    private ItemFactory itemFactory;


    @Before
    public void setUp() {
        this.itemFactory = new ItemFactory();
    }

    @Test
    public void testConstructor(){
        List emptyList = new ArrayList();
        assertEquals(0, itemFactory.getNumItems());
        assertEquals(emptyList, itemFactory.getAll());
        assertEquals(emptyList, itemFactory.getBandages());
        assertEquals(emptyList, itemFactory.getCoins());
        assertEquals(emptyList, itemFactory.getMedkits());
        assertEquals(emptyList, itemFactory.getRupees());
    }

    @Test
    public void testCreateItems() {
        GameManager.get().setWorld(new TestWorld());
        //Check the factory returns the correct item
        MedKit medkit = new MedKit(0,0);
        MedKit factoryMedkit = (MedKit)itemFactory.createItem("Medkit", 0,0);

        assertEquals(medkit.getClass(), factoryMedkit.getClass());
        assertEquals(medkit.getItemDesc(), factoryMedkit.getItemDesc());
        assertEquals(medkit.getHealthGain(), factoryMedkit.getHealthGain());
        assertEquals(medkit.getQuantity(), factoryMedkit.getQuantity());

        Bandage bandage = new Bandage(0,0);
        Bandage factoryBandage = (Bandage)itemFactory.createItem("Bandage", 0,0);
        bandage.onTick(0);

        assertEquals(bandage.getClass(), factoryBandage.getClass());
        assertEquals(bandage.getItemDesc(), factoryBandage.getItemDesc());
        assertEquals(bandage.getHealthGain(), factoryBandage.getHealthGain());
        assertEquals(bandage.getQuantity(), factoryBandage.getQuantity());

        Coin coin = new Coin(0,0);
        Coin factoryCoin = (Coin)itemFactory.createItem("Coin", 0,0);

        assertEquals(coin.getClass(), factoryCoin.getClass());
        assertEquals(coin.getItemDesc(), factoryCoin.getItemDesc());

        Rupee rupee = new Rupee(0,0);
        Rupee factoryRupee = (Rupee)itemFactory.createItem("Rupee", 0,0);

        assertEquals(rupee.getClass(), factoryRupee.getClass());
        assertEquals(rupee.getItemDesc(), factoryRupee.getItemDesc());

        assertEquals(null, itemFactory.createItem("NotAValidItem",0,0));
    }

    @Test
    public void testLootables() {
        Coin coin  = new Coin(0f, 0f);
        coin.setType("coin");
        coin.setValue(10);
        assertEquals("coin", coin.getType());
        assertEquals(10, coin.getValue());
    }
}
