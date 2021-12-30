package deco2800.spooky.managers;

import deco2800.spooky.worlds.Tile;
import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.entities.TrapType;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class TrapManager {
    private List<RoomTrap> tileTraps = new ArrayList<>();
    private Set<Tile> openTraps = new HashSet<>();
    private List<Tile> inactiveTraps = new ArrayList<>();

    /**
     * Set specific tile to a trap tile
     * @param tile The tile to become a trap
     * @param type Trap type planted
     */
    public RoomTrap setTrap(Tile tile, TrapType type) {
        if (tile != null) {
            RoomTrap trap = new RoomTrap(tile.getCol(), tile.getRow(), tile, type);
            tileTraps.add(trap);
            inactiveTraps.add(tile);
            return trap;
        }
        return null;
    }

    /**
     * checks if a tile trap has been actived or still inactive
     * @param trap tile to check if it is active
     * @return true iff it is open tile
     */
    public boolean isTrapOpen(Tile trap) { return openTraps.contains(trap); }

    /**
     * checks if a tile is a trap tile or normal tile
     * @param trap tile to be checked if it is trap tile or normal
     * @return true iff it is a trap tile
     */
    public boolean isTrap (Tile trap) {
        return openTraps.contains(trap) || inactiveTraps.contains(trap);
    }

    /**
     * convert a closed trap to an opened trap but still activate
     * @param trap The tile to be changed to open trap
     */
    public void trapOpened(RoomTrap trap) {
        RoomTrap checkTrap = tileToTrap(trap.getTile());
        if (checkTrap != null) {
            openTraps.add(trap.getTile());
            trap.setStep(true);
            switch (trap.trapTypeIs()) {
                case PITFALL :
                    trap.setTexture("pitfall");
                    break;
                case QUICKSAND :
                    trap.setTexture("quicksand");
                    break;
                case MUMMY :
                    trap.setTexture("mummy");
                    break;
                default:
                    trap.setTexture("pitfall");
            }
        }
    }

    /**
     * Converting tile to a roomtrap
     * @param tile The tile to be converted
     * @return room trap to be planted into the randomWorld
     */
    public RoomTrap tileToTrap(Tile tile) {
        if (tile != null) {
            for (RoomTrap trap : tileTraps) {
                if(trap.getCol() == tile.getCol() && trap.getRow() == tile.getRow()) {
                    return trap;
                }
            }
        }
        return null;
    }
}
