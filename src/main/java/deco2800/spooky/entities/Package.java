package deco2800.spooky.entities;

import deco2800.spooky.worlds.JennaWorld;

import java.util.List;

public class Package extends Peon {
    private List<Character> playerList = JennaWorld.getPlayerList();

    public Package(float col, float row) {
        super(col, row, 0);
        this.setTexture("packetPlus");
    }

    @Override
    public void onTick(long i) {
        for (Character player : playerList) {
            if (player.overlaps(this, 0.1f * 72, 1.5f, 0.2f) >=0 ) {
                //Placeholder for SonarQube
                setPosition(getCol(), getRow(), 0);
            }
        }
    }
}
