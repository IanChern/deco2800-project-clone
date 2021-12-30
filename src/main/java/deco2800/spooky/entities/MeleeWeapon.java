package deco2800.spooky.entities;

import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.HexVector;

/**
 * A melee weapon
 * Melee types include sword, dagger, axe
 */
public class MeleeWeapon extends Peon {
    private int power;
    private float range;
    private Character holder;
    private int frequency;
    private MeleeType type;

    /**
     * Constructor of melee weapon
     * @param col the column of the melee weapon
     * @param row the row of the melee weapon
     * @param meleeType the type of the melee weapon
     */
    public MeleeWeapon(float col, float row, MeleeType meleeType) {
        super(col, row, 0);
        /*Change the texture, range, power, frequency
        based on the type of the melee weapon
         */
        this.type = meleeType;
        if (meleeType == MeleeType.DAGGER) {
            this.range = 0.7f;
            this.power = 5;
            this.frequency = 300;
            setTexture("daggerItem");
        } else if (meleeType == MeleeType.AXE) {
            this.range = 0.5f;
            this.power = 7;
            this.frequency = 100;
            setTexture("axeItem");
        } else if (meleeType == MeleeType.SWORD) {
            this.range = 1f;
            this.power = 6;
            this.frequency = 200;
            setTexture("swordItem");
        } else {
            this.range = 0.3f;
            this.power = 2;
            this.frequency = 250;
            setTexture("handbagItem");
        }
    }

    /**
     * Get the power of the melee weapon
     * @return the power of the weapon
     */
    public int getPower() {
        return power;
    }

    /**
     * Get the range of the melee weapon
     * @return the range of the weapon
     */
    public float getRange() {
        return range;
    }

    /**
     * The holder of this melee weapon
     * @return the holder
     */
    public Character getHolder() {
        return this.holder;
    }

    /**
     * Set the holder of this melee weapon
     * @param holder the new holder of the weapon
     */
    public void setHolder(Character holder) {
        this.holder = holder;
    }

    /**
     * Set the power of the melee weapon
     * @param power the power to be set to
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Set the attack range of this melee weapon
     * @param range the range to be set to
     */
    public void setRange(float range) {
        this.range = range;
    }

    /**
     * Set the frequency
     * @param frequency the new frequency to be set to
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Get the frequency of this melee weapon
     * @return the frequency of this melee weapon
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Work out the distance between this melee weapon and the entity
     * @param entity the entity to be measured
     * @return the distance
     */
    public float getEntityDistance(AbstractEntity entity) {
        HexVector pos1 = this.getPosition();
        HexVector pos2 = entity.getPosition();
        //Return the Euclidiean Distance
        return pos1.euclidieanDistance(pos2);
    }

    /**
     * Get the type of the melee weapon
     * @return the type of the melee weapon
     */
    public MeleeType getName() {
        return this.type;
    }

    /**
     * Constantly checks collision when the character collides with the melee weapon to pick it up
     * @param i tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof Character && ((Character) entity).overlaps(this, 0.2f * 72,
                    1.5f, 0.2f) >= 0) {
                //When collision occurs
                ((Character) entity).pickUpWeapon(this);
                GameManager.get().getWorld().removeEntity(this);
            }
        }
    }
}
