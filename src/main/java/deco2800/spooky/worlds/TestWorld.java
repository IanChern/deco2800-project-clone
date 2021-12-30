package deco2800.spooky.worlds;

import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.entities.Rock;
import deco2800.spooky.entities.StaticEntity;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.Cube;
import deco2800.spooky.worlds.rooms.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class TestWorld extends AbstractWorld {
    boolean notGenerated = true;

    private static int radius = 25;

    public TestWorld() {
        super();
    }

    //reading from room file and render room data to world
    private StaticEntity createBuilding4() throws InterruptedException {
        String path = "resources/testrooms";
        List<List<String>> roomsFromFile = ReadSerialisation.getRoomsToSend(path);

        List<Room> roomList = new ArrayList<>();
        for (List<String> readFile : roomsFromFile) {
            roomList.add(new Room(readFile));
        }

        Room2World room2World = new Room2World(roomList.get(0));
        return new StaticEntity(0, 0, 1, room2World.getTextures());

    }

    //this get ran on first game tick so the world tiles exist.
    public void createBuildings() throws InterruptedException {

        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTileMap().size();
        // Generate some rocks to mine later
        for (int i = 0; i < 200; i++) {
            Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
            if (t != null) {
                entities.add(new Rock(t, true));
            }
        }
        entities.add(createBuilding4());

    }

    @Override
    protected void generateWorld() {
        Random random = new Random();
        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= radius) {

                    int col = q;

                    float oddCol = (col % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);

                    String type = "grass_" + elevation;

                    tiles.add(new Tile(type, q, r + oddCol));
                }
            }
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            if (e instanceof deco2800.spooky.entities.Character) {
                continue;
            }
            e.onTick(0);
        }

        if (notGenerated) {
            try {
                createBuildings();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            notGenerated = false;
        }
    }
}

