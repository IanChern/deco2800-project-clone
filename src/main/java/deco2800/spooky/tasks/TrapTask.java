package deco2800.spooky.tasks;

import java.util.*;
import deco2800.spooky.entities.Character;
import deco2800.spooky.managers.CollisionManager;
import deco2800.spooky.worlds.*;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TrapManager;
import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.util.HexVector;

import static java.lang.Thread.sleep;

public class TrapTask {
    private RoomTrap trap;
    private Tile tile;
    private List<RoomTrap> roomTraps;
    private TrapManager trapManager = GameManager.get().getWorld().getTrapManager();
    private CollisionManager collisionManager = new CollisionManager();

    /**
     * Constructor of Trap task handling the activation of traps due to overlap with player
     * @param trap identifies the trap type
     */
    public TrapTask(RoomTrap trap) {
        this.trap = trap;
        this.tile = trap.getTile();
        roomTraps = new ArrayList<>();
        roomTraps.add(trap);
    }

    /**
     * As the player (victim) steps on a tile that seems to be a trap tile, it activates the tile trap
     * @param traps type to be activated
     * @param victim player that step on that tile turn out to be a trap tile
     */
    public void openTrap(RoomTrap traps, Character victim) {
        RoomTrap trapTile = trapManager.tileToTrap(this.tile);
        roomTraps.add(trapTile);
        switch (traps.trapTypeIs()) {
            case MUMMY:
                break;
            case QUICKSAND:
                quickSand(traps, victim);
                break;
            case PITFALL:
                pitFall(victim);
                break;
            default:
                pitFall(victim);
                break;
        }
        trapManager.trapOpened(traps);
    }

    /**
     * Activate quicksand trap due to player overlaps
     * @param victim player that step on that tile
     */
    private void quickSand(RoomTrap trap, Character victim) {
        victim.setSpeed(0f);
        if (victim.getKeyDown() - trap.getTemp() > 5) {
            victim.setSpeed(trap.getSpeed());
        }
    }

    /**
     * Activates pitfall trap due to player overlaps
     * @param victim player that activates the tile trap
     */
    private void pitFall(Character victim) {
        Random rand = new Random();
        int tileCount = 0;
        int randomTile = 0;
        Tile t = null;
        AbstractWorld world = GameManager.get().getWorld();
        while(true) {
            if (world instanceof RandomWorld) {
                tileCount = ((RandomWorld) world).getCurrentRoom(victim).getTileIDtoMap().size();
                randomTile = rand.nextInt(tileCount);
                HexVector coord = ((RandomWorld) world).getCurrentRoom(victim).getTileIDtoMap().get(randomTile);
                t = world.getTile(coord.getCol(), coord.getRow());
            }
            else if (world instanceof JennaWorld || world instanceof TestWorld) {
                tileCount = world.getTileMap().size();
                randomTile = rand.nextInt(tileCount);
                t = world.getTileMap().get(randomTile);
            }
            if(t == null) {
                return;
            }
            if (collisionManager.checkPlayerWallCollisions(t.getCoordinates())
                    && collisionManager.checkPlayerDoorCollisions(t.getCoordinates())) {
                break;
            }
        }
        victim.moveSet(t.getCol(), t.getRow());
    }
}
