package deco2800.spooky.entities;

import deco2800.spooky.Tickable;

public class Damien extends Character implements Tickable {

    public Damien(float col, float row, boolean playerControlled) {
        super(new Appearance("damien"),HEALTH_MID,SPEED_HIGH,VISUAL_SIGHT_DEFAULT, col, row, playerControlled);
        this.getAppearance().setDefaultAppearance("damien");
        this.setTexture(this.getAppearance().getDefaultAppearance());
    }
}
