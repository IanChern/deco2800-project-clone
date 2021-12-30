package deco2800.spooky.entities;

import deco2800.spooky.Tickable;
import deco2800.spooky.tasks.AbstractTask;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for base weapon of every player
 */
public abstract class BaseWeapon extends Peon implements Tickable {
    protected transient AbstractTask task;
    int baseDamage = 10;
    protected Character player = null;
    float critChance = 0;
    String weaponDesc = null;
    String soundEffect = null;
    protected int ammo;

    private List<Peon> bullets = new ArrayList<>();


    /**
     * Constructor of BaseWeapon that contains the base stats of a weapon
     */
    BaseWeapon(float col, float row, float speed) {
        super(col, row, speed);
        this.weaponDesc = "Pistol";
        this.soundEffect = "swing_sword.mp3";
        this.ammo = 10;

        for (int i = 0; i < this.ammo+1; i++) {
            Peon singleBullet = new Peon(0,0,0.2f);
            singleBullet.setTexture("bullet");
            singleBullet.setHeight(1);
            this.bullets.add(singleBullet); //initiate more bullets
        }
    }

    /**
     * return the critical chance of a weapon in use
     * @return value of critical chance of weapon
     */
    float getCrit() { return this.critChance; }

    /**
     * to identify the enemy nearby that compare the player's position
     * and the enemy
     * @param x enemy x coordinates
     * @param y enemy y coordinates
     * @return true iff the enemy is close to player's current position otherwise false
     */
    public boolean isEnemy(float x, float y) { return false; }

    /**
     * Inflict damage to the enemy that is identified around the player
     * @param x enemy x coordinates
     * @param y enemy y coordinates
     * @return damage inflicted value
     */
    public int damageDeals(float x, float y) { return this.baseDamage; }

    /**
     * get the weapon name that is currently in used
     * @return name of the weapon (e.g Weapon, Axe)
     */
    public String getWeapon() { return this.weaponDesc; }

    public String getSound() { return this.soundEffect; }

    public int getAmmo() { return this.ammo; }

    public void setAmmo(int ammo) { this.ammo = ammo; }


}
