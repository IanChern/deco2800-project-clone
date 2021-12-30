package deco2800.spooky.entities.Items;

import java.util.Timer;
import java.util.TimerTask;

public class MedKit extends Medical {

    /**
     * Sets paramiters for the medical kit
     * @param col collum number
     * @param row row number
     */
    public MedKit(float col, float row) {
        super(col, row, 0);
        this.setTexture("rupee5");
        this.setItemDesc("MedKit");
        this.setHeight(1);
        this.setHealthGain(2);
        this.setRarity(0.5);
        this.setUsed(false);
        this.setType("MEDKIT");

        this.setObjectName("medkit");
    }

    public void spin() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            Integer x = 0;

            @Override
            public void run() {
                MedKit.this.setTexture("Healthpack-" + x.toString());
                x++;
                if (x > 25){
                    x = 0;
                }
            }
        }, 0, 100);
    }
}
