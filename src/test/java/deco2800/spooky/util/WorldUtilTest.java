package deco2800.spooky.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class WorldUtilTest {

    @Test
    public void worldCoordinatesToColRow() {
        float[] coord = WorldUtil.colRowToWorldCords(1.0f, 2.0f);
        float[] test = {72f, 166.8f};
        assertArrayEquals(test, coord, 0.0f);
    }

    @Test
    public void validColRowNotValid() {
        assertFalse(WorldUtil.validColRow(new HexVector(0.1f, 1f)));
    }

    @Test
    public void isWalkableTrue() {
        GameManager.get().setWorld(new TestWorld());
        Tile tile = new Tile("Grass");
        tile.setRow(0);
        tile.setCol(0);
        GameManager.get().getWorld().updateTile(tile);
        assertTrue(WorldUtil.isWalkable(0,0));
    }

    @Test
    public void isWalkableFalse() {
        GameManager.get().setWorld(null);
        assertFalse(WorldUtil.isWalkable(0,0));
    }

    @Test
    public void notWalkable() {
        GameManager.get().setWorld(new TestWorld());
        GameManager.get().getWorld().setTileMap(new CopyOnWriteArrayList<>());
        assertFalse(WorldUtil.isWalkable(0,0));
    }

    @Test
    public void closestWalkable() {
        GameManager.get().setWorld(new TestWorld());
        Tile tile = new Tile("Grass");
        tile.setRow(0);
        tile.setCol(0);
        Tile tile2 = new Tile("Grass");
        tile2.setRow(1);
        tile2.setCol(0);
        CopyOnWriteArrayList<Tile> tiles = new CopyOnWriteArrayList<>();
        tiles.add(tile);
        tiles.add(tile2);
        GameManager.get().getWorld().setTileMap(tiles);
        GameManager.get().getWorld().generateNeighbours();
        assertEquals(tile2.getCoordinates(), WorldUtil.closestWalkable(new HexVector(0, 1)));
    }

    @Test
    public void closestWalkableFalse() {
        GameManager.get().setWorld(new TestWorld());
        Tile tile = new Tile("Grass");
        tile.setRow(0);
        tile.setCol(0);
        CopyOnWriteArrayList<Tile> tiles = new CopyOnWriteArrayList<>();
        tiles.add(tile);
        GameManager.get().getWorld().setTileMap(tiles);
        GameManager.get().getWorld().generateNeighbours();
        assertNull(WorldUtil.closestWalkable(new HexVector(0, 100)));
    }
}