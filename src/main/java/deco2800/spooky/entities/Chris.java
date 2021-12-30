package deco2800.spooky.entities;

import deco2800.spooky.Tickable;

public class Chris extends Character implements Tickable {

    public Chris(float col, float row, boolean playerControlled) {
        super(new Appearance("chris"),HEALTH_MID,SPEED_HIGH,VISUAL_SIGHT_DEFAULT, col, row, playerControlled);
        this.getAppearance().setDefaultAppearance("chris");
        this.setTexture(this.getAppearance().getDefaultAppearance());
    }
}
