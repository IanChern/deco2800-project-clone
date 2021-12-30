package deco2800.spooky.entities.Items;

import deco2800.spooky.entities.Peon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public abstract class Lootable extends Peon {

    private int value;
    private String itemDesc;
    private String type;
    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    /**
     * Sets Location, Value, and Description
     * @param col Collum Number
     * @param row Row Number
     */
    public Lootable(float col, float row) {
        super(col, row, 0);

        this.value = 0;
        this.itemDesc = "Lootable item";
        this.type = "lootable";
        this.setPickable(true);
    }

    /**
     * Returns The Value of the lootable
     * @return int, value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the Value of the Lootable
     * @param value an int
     */
    public void setValue(int value) {
        this.value = value;
        logger.warn("Value of {} has been changed to {}.", this.type, this.value);
    }

    /**
     * Returns the Lootable's description
     * @return intemDesc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * Changes / sets the description of the lootable
     * @param itemDesc - String
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        logger.warn("Description of {} has been changed to {}.", this.type, this.itemDesc);

    }

    public void setType(String type) {
        logger.warn("Type of {} has been changed to {}.", this.type, type);
        this.type = type;
    }

    public String getType() {return this.type;}
}
