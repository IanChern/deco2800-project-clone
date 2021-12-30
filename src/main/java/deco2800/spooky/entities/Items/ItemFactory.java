package deco2800.spooky.entities.Items;

import deco2800.spooky.entities.Peon;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemFactory {
    private List items;
    private List coins;
    private List bandages;
    private List medkits;
    private List rupees;
    private int numItems;

    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ItemFactory(){
        this.numItems = 0;
        this.items = new ArrayList();
        this.medkits = new ArrayList();
        this.bandages = new ArrayList();
        this.coins = new ArrayList();
        this.rupees = new ArrayList();
    }

    public Peon createItem (String itemType, float col, float row) {
        switch (itemType) {

            case("Coin"):
                Coin coin = new Coin(col, row);
                items.add(coin);
                numItems++;
                coins.add(coin);
                logger.info("A coin has been created in the item factory.");
                return coin;

            case("Bandage"):
                Bandage bandage = new Bandage(col,row);
                items.add(bandage);
                numItems++;
                bandages.add(bandage);
                logger.info("A bandage has been created in the item factory.");
                return bandage;

            case("Medkit"):
                MedKit medkit = new MedKit(col, row);
                items.add(medkit);
                numItems++;
                medkits.add(medkit);
                logger.info("A medkit has been created in the item factory.");
                return medkit;

            case("Rupee"):
                Rupee rupee = new Rupee(col, row);
                items.add(rupee);
                numItems++;
                rupees.add(rupee);
                logger.info("A rupee has been created in the item factory.");
                return rupee;

            default:
                return null;
        }
    }

    public List getAll(){
        return this.items;
    }

    public List getCoins() {
        return coins;
    }

    public List getBandages() {
        return bandages;
    }

    public List getMedkits() {
        return medkits;
    }

    public List getRupees() {
        return rupees;
    }

    public int getNumItems() {
        return numItems;
    }
}





