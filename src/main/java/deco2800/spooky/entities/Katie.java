package deco2800.spooky.entities;

import deco2800.spooky.Tickable;

public class Katie extends Character implements Tickable {

    public Katie(float col, float row, boolean playerControlled) {
        super(new Appearance("katie"),HEALTH_MID,SPEED_HIGH,VISUAL_SIGHT_DEFAULT, col, row, playerControlled);
        this.getAppearance().setDefaultAppearance("katie");
        this.setTexture(this.getAppearance().getDefaultAppearance());
    }
}
