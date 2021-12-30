package deco2800.spooky.tasks;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Peon;
import deco2800.spooky.worlds.JennaWorld;

/**
 * ConstantFire class that allows the character to make multiple attacks when the space key is holden
 *
 * Author: Yunke Qu
 */
public class ConstantFire extends Peon {
    private boolean flag;
    private int count = 0;

    /**
     * Constructor
     */
    public ConstantFire() {
        super(25, 24, 0);
        flag = false;
    }

    /**
     * Start making multiple attacks
     */
    public void start() {
        flag = true;
    }

    /**
     * Stop making attacks
     */
    public void terminate() {
        flag = false;
    }

    /**
     * Get the flag of the item generation task
     * @return the flag
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Set the counter to start an attack and reset the counter after each attack
     * @param i tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);
        count++;
        if (flag && count >= 50 && JennaWorld.getPlayerList() != null && JennaWorld.getPlayerList().size() != 0) {
                Character character = JennaWorld.getPlayerList().get(0);
                character.meleeAttack();
                count = 0;
        }
    }
}
