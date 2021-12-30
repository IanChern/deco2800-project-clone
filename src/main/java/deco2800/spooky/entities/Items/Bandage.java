package deco2800.spooky.entities.Items;

public class Bandage extends Medical {

    public Bandage(float col, float row) {
        super(col, row, 0);
        this.setItemDesc("Bandage");
        this.setTexture("medicine");
        this.setHeight(1);
        this.setType("BANDAGE");

        this.setHealthGain(1);
        this.setRarity(0.9);
        this.setUsed(false);

        this.setObjectName("bandage");
    }

}