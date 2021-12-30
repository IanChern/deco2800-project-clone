package deco2800.spooky.entities;

import deco2800.spooky.managers.GameManager;

/**
 * A pill that can refill the health of a character
 */
public class Pill extends Peon{

    /**
     * Constructor
     * @param col column of the pill
     * @param row row of the pill
     */
    public Pill(float col, float row) {
        super(col, row, 0);
        setTexture("medicine");
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof Character && ((Character) entity).overlaps(this, 0.2f * 72,
                    1.5f, 0.2f) >= 0) {
                //When collision occurs
                GameManager.get().getWorld().removeEntity(this);
                ((Character) entity).updateHealth(15);
            }
        }
    }
}
