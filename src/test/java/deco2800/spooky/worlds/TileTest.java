package deco2800.spooky.worlds;

import deco2800.spooky.entities.StaticEntity;
import deco2800.spooky.util.HexVector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TileTest {

    @Test
    public void createTile() {
        String tile1 = "grass_0";
        Tile tile = new Tile(tile1);
        assertEquals(tile.getTextureName(), tile1);
        assertEquals(tile.getCol(), 0, 0);
        assertEquals(tile.getRow(), 0, 0);
        tile.setCol(1);
        tile.setRow(1);
        assertEquals(tile.getCol(), 1, 0);
        assertEquals(tile.getRow(), 1, 0);
        assertEquals(tile.getNeighbour(1), null);
        assertEquals(tile.calculateIndex(), 0);
        tile.setIndex(1);
        assertEquals(tile.calculateIndex(), 1);
        assertNotNull(tile);
        tile.setTileID(0);
        assertEquals(tile.getTileID(), 0);


        StaticEntity entity = new StaticEntity();
        tile.setParent(entity);
        assertEquals(tile.hasParent(), true);

        String tileString = "[1, 1.0: 1]";
        assertEquals(tile.toString(), tileString);

        assertEquals(tile.getCoordinates(), new HexVector(1, 1));

        Tile.resetID();

        //gameManager = GameManager.get();
        //gameManager.addManager(new TextureManager());

        //Texture grass = new Texture("resources/grass_1.png");

        //assertEquals(tile.getTexture(), grass);

    }

    @Test
    public void addNeighbourTile() {
        String tile2String = "tile1";
        String tile3String = "tile2";
        Tile tile2 = new Tile(tile2String);
        Tile tile3 = new Tile(tile3String);
        tile2.addNeighbour(1, tile3);
        assertEquals(tile2.getNeighbour(1), tile3);
        assertEquals(Tile.opposite(1), 4);

        assertEquals(tile2.isObstructed(), false);
        tile2.setObstructed(true);
        assertEquals(tile2.isObstructed(), true);
    }

}