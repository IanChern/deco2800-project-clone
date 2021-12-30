package deco2800.spooky.worlds;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Creep.CreepFactory;
import deco2800.spooky.entities.Items.ItemFactory;
import deco2800.spooky.tasks.ItemGeneration;
import deco2800.spooky.util.Cube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientWorld extends AbstractWorld {
    boolean notGenerated = true;

    private static int mapRadius = 25; // Used to determine size of map

    private static List<Character> playerList = new ArrayList<>();
    private static CreepFactory creepFactory;
    private static ItemFactory itemFactory;
    private ItemGeneration itemGeneration;
    private long moveCoolDownTime = 1000;
    private long movedTime;

    private static final float BULLET_BOX_LENGTH = 0.1f;

    @Override
    protected void generateWorld() {
        Random random = new Random();
        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= mapRadius) {

                    int col = q;

                    float oddCol = (col % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);

                    String type = "grass_" + elevation;

                    tiles.add(new Tile(type, q, r + oddCol));
                }
            }
        }
    }

}
