package deco2800.spooky.entities.Items;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Peon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

public abstract class Medical extends Peon {

    private int healthGain;
    private String itemDesc;
    private double rarity;
    private boolean used;
    private int quantity;
    private String type;
    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Sets location and description
     * @param col int
     * @param row int
     * @param speed int
     */
    public Medical(float col, float row, float speed) {
        super(col, row, speed);

        this.itemDesc = "A medical item.";
        this.type = "medical";
        this.setPickable(true);

    }

    /**
     * Determines if a character can use the health pack,
     * If seccessful, health will change, if not, health will remain the same
     * @param character character to test
     * @return Return 1 on success
     */
    public int use(Character character) {
        if (used) {
            return 0;
        }

        //If Character Has a positive health, but below six, then health will be set to six, returns 1 for success
        if (character.getHealth() > 0 && character.getHealth() < 6) {
            character.setHealth(character.getHealth() + this.healthGain);
            if (character.getHealth() > 6) {
                character.setHealth(6);
            }
            this.used = true;   //is now used, can't use anymore
            return 1;
        }
        //Health is not within acceptable bounds, fail silently
        return 0;
    }

    /**
     * Retirns how much health was gained
     * @return int, healthGain
     */
    public int getHealthGain() {
        return healthGain;
    }

    /**
     * Sets Health Gain
     * @param healthGain - int
     */
    public void setHealthGain(int healthGain) {
        this.healthGain = healthGain;
        logger.warn("Health gain of {} has been changed to {}.", this.type, this.healthGain);

    }

    /**
     * Returns the item descrition
     * @return - String itemDesc
     */
    public String getItemDesc() {
        return itemDesc;

    }

    /**
     * Sets or changes the Item Description
     * @param itemDesc - String Item Description
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        logger.warn("Description of {} has been changed to {}.", this.type, this.itemDesc);

    }

    /**
     * Returns the items rarity
     * @return double, rarity
     */
    public double getRarity() {
        return rarity;
    }

    /**
     *Sets Rarity
     * @param rarity
     */
    public void setRarity(double rarity) {
        this.rarity = rarity;
        logger.warn("Rarity of {} has been changed to {}.", this.type, this.rarity);

    }

    /**
     * Has it been used, true or false?
     * @return used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Set if been used true or false
     * @param used  Boolean
     */
    public void setUsed(boolean used) {
        this.used = used;
        logger.warn("Used of {} has been changed to {}.", this.type, this.used);

    }

    /**
     * Return Quantity of health
     * @return int quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity in integer form
     * @param quantity int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        logger.warn("Quantity of {} has been changed to {}.", this.type, this.quantity);

    }

    public void setType(String type) {
        this.type = type;
        logger.warn("Type of {} has been changed to {}.", this.type, this.type);
    }

    public String getType() {return this.type;}


}