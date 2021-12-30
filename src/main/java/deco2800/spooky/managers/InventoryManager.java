package deco2800.spooky.managers;

public class InventoryManager extends TickableManager {

    private int stone = 0;

    @Override
    public void onTick(long i) {
        /* Nothing to do each tick */
    }

    public int getStone() {
        return stone;
    }

    public void addStone(int stone) {
        this.stone += stone;
    }
}
