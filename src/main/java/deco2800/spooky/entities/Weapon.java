package deco2800.spooky.entities;

import com.badlogic.gdx.graphics.Texture;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.AbstractWorld;


public class Weapon extends Peon {
    private float col;
    private float row;
    private int atkRange; // the attack range of the dagger
    private WeaponType type; //the type of weapon
    private String direction;
    private boolean collision;
    private AbstractWorld world;
    private Texture weaponTexture;
    private HexVector attackDestination;
    private float range; //the attack range of the weapon
    private boolean isUsed; //whether the weapon is being used
    private static final float DAGGERRANGE = 5;
    private static final float LONGSWORDRANGE = 10;

    /**
     * A A weapon class that represent a dagger/long sword in the game
     * @param col the col of the weapon/character
     * @param row the row of the weapon/character
     * @param type the type of the weapon
     */

    public Weapon(float col, float row, WeaponType type) {
        this.col = col;
        this.row = row;
        world = GameManager.get().getWorld();
        this.type = type;
        this.weaponTexture = null;
        this.range = 0;
        this.attackDestination = new HexVector(col,row);
        this.isUsed = false;
    }

    /**
     * initialising the weapon properties once the weapon types have been determined
     * @param type ENUM - the type of the weapon
     * @param direction String - direction of the weapon, the same as the character's direction
     */
    public void setWeaponPropety(WeaponType type, String direction) {
        switch (type) {
            case DAGGER:
                setRange(DAGGERRANGE);
                weaponTexture = GameManager.get().getManager(TextureManager.class).
                        getTexture("dagger01");
                break;
            case LONGSWORD:
                setRange(LONGSWORDRANGE);
                weaponTexture = GameManager.get().getManager(TextureManager.class).
                        getTexture("axe01"); //will replace later to long sword
                break;
            }
    }

    /** updating the position of the weapon as the character moves
     * @param row the row where the character is in
     * @param col the column where the character is in
     */
    public void setPosition(float row, float col) {
        this.col = col;
        this.row = row;
        this.attackDestination = new HexVector(col, row);
    }

    /**
     * set the direction of the weapon which in consistence with the character's direction
     * @param direction the direction of the character
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * set the attack range of the weapon
     * @param weaponRange the attack range of the weapon
     */
    public void setRange(float weaponRange) {
        this.range = weaponRange;
    }

    /**
     * Return the attack destination of the weapon in Hex Vector
     * @return the attack destination of the weapon in Hex Vector
     */
    public HexVector getAttackDestination() {
        switch (this.direction) {
            case "A":
                this.attackDestination = new HexVector(col - this.range, row);
                break;
            case "W":
                this.attackDestination = new HexVector(col, this.range + row);
                break;
            case "S":
                this.attackDestination = new HexVector(col, this.range - row);
                break;
            case "D":
                this.attackDestination = new HexVector(col + range, this.row);
                break;
            default:
                this.attackDestination = new HexVector(col, row);
        }
        //add Logger
        return new HexVector(this.attackDestination);
    }

    /**
     * set isUsed to a desired boolean to turn on/off the collision detection
     */
    public void setIsUsed(boolean usage) {
        this.isUsed = usage;
    }

    /**
     * return True if the character tried to use the weapon
     * @return whether the character is using the weapon
     */
    public boolean isUsed() {
        return this.isUsed;
    }

    /**
     * Getting the GUI texture of the weapon
     * @return the texture (GUI) of the weapon
     */
    public Texture getWeaponTexture() {
        return this.weaponTexture;
    }

    /**
     * Return the type of weapon used
     * @return the weapon type of the weapon currently in use
     */
    public WeaponType getWeaponType() {
        return this.type;
    }

}
