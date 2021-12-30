package deco2800.spooky.tasks;

import deco2800.spooky.entities.Peon;
import deco2800.spooky.entities.Pill;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.Cube;

import java.util.Random;

/**
 * ItemGeneration class generates items and randomly puts them on the map
 *
 * Author: Yunke Qu
 */
public class ItemGeneration extends Peon {
    private int count = 0;
    private boolean flag = false;

    /**
     * Constructor
     */
    public ItemGeneration() {
        super(25, 25, 0);

    }

    /**
     * Start item generation
     */
    public void start() {
        flag = true;
    }

    /**
     * Terminate item generation
     */
    public void terminate() {
        flag = false;
    }

    /**
     * Generate a random integer within a given range
     * @param min the lower bound
     * @param max the upper bound
     * @return a random integer within a given range
     */
    public int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    /**
     * Get the flag of the item generation task
     * @return the flag
     */
    public boolean getFlag() {
        return flag;
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (flag) {
            count++;
            int q = randInt(-15, 15);
            int r = randInt(-15, 15);
            while (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) > 13) {
                q = randInt(-15, 15);
                r = randInt(-15, 15);
            }
            float oddCol = (q%2 !=0? 0.5f : 0);
            if (count > 200) {
                Pill pill = new Pill(q, r+oddCol);
                GameManager.get().getWorld().addEntity(pill);
                count = 0;
            }
        }
    }
}
