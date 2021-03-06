package deco2800.spooky.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;
import deco2800.spooky.entities.StaticEntity;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.ServerManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.util.HexVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Tile {
    private static int nextID = 0;

    private static int getNextID() {
        return nextID++;
    }

    public static void resetID() {
        nextID = 0;
    }

    @Expose
    private String texture;
    private HexVector coords;


    private StaticEntity parent;

    @Expose
    private boolean obstructed = false;


    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int SOUTH_EAST = 2;
    public static final int SOUTH = 3;
    public static final int SOUTH_WEST = 4;
    public static final int NORTH_WEST = 5;

    static final int[] NORTHS = {NORTH_WEST, NORTH, NORTH_EAST};

    private Map<Integer, Tile> neighbours;

    @Expose
    private int index = -1;

    @Expose
    private int tileID = 0;

    public Tile(String texture) {
        this(texture, 0, 0);
    }

    public Tile(String texture, float col, float row) {
        this.texture = texture;
        coords = new HexVector(col, row);
        this.neighbours = new HashMap<>();
        this.tileID = Tile.getNextID();
    }

    public Tile() {
        this.neighbours = new HashMap<>();
    }

    public float getCol() {
        return coords.getCol();
    }

    public float getRow() {
        return coords.getRow();
    }

    public HexVector getCoordinates() {
        return new HexVector(coords);
    }

    public String getTextureName() {
        return this.texture;
    }

    public Texture getTexture() {
        return GameManager.get().getManager(TextureManager.class).getTexture(this.texture);
    }


    public void addNeighbour(int direction, Tile neighbour) {
        neighbours.put(direction, neighbour);
    }

    public static int opposite(int dir) {
        return (dir + 3) % 6;
    }

    public void removeReferanceFromNeighbours() {
        for (Entry<Integer, Tile> neighbourHash : neighbours.entrySet()) {
            neighbourHash.getValue().getNeighbours().remove(Tile.opposite(neighbourHash.getKey()));
        }
    }

    public Tile getNeighbour(int direction) {
        return neighbours.get(direction);
    }

    public Map<Integer, Tile> getNeighbours() {
        return neighbours;
    }

    public String toString() {
        return String.format("[%.0f, %.1f: %d]", coords.getCol(), coords.getRow(), index);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setParent(StaticEntity parent) {
        this.parent = parent;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void dispose() {
        if (this.hasParent() && this.parent != null) {
            for (HexVector childposn : parent.getChildrenPositions()) {
                Tile child = GameManager.get().getWorld().getTile(childposn);
                if (child != null) {
                    child.setParent(null);
                    child.dispose();
                }
            }
        }

        GameManager.get().getManager(ServerManager.class).deleteTile(this);

        this.removeReferanceFromNeighbours();
        GameManager.get().getWorld().getTileMap().remove(this);
    }

    public int calculateIndex() {
        if (index != -1) {
            return index;
        }

        int max = index;
        for (int north : NORTHS) {
            if (neighbours.containsKey(north)) {
                max = Math.max(max, neighbours.get(north).calculateIndex());
            }
        }
        this.index = max + 1;
        return index;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public void setIndex(Integer indexValue) {
        this.index = indexValue;
    }

    public boolean isObstructed() {
        return obstructed;
    }

    public void setObstructed(boolean b) {
        obstructed = b;

    }

    public void setCol(float col) {
        this.coords.setCol(col);
    }

    public void setRow(float row) {
        this.coords.setRow(row);
    }
}
