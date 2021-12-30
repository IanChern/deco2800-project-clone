package deco2800.spooky.entities;

import deco2800.spooky.Tickable;

public class Larry extends Character implements Tickable {

    public Larry(float col, float row, boolean playerControlled) {
        super(new Appearance("larry"),HEALTH_MID,SPEED_HIGH,VISUAL_SIGHT_DEFAULT, col, row, playerControlled);
        this.getAppearance().setDefaultAppearance("larry");
        this.setTexture(this.getAppearance().getDefaultAppearance());
    }
}
