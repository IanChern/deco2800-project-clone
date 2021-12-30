package deco2800.spooky.entities;

import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;
import org.javatuples.Pair;
import org.junit.Test;
import org.lwjgl.Sys;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class StaticEntityTest {

    @Test
    public void setup() {
        GameManager.get().setWorld(new TestWorld());
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(1f, 1f), "Grass");
        StaticEntity se = new StaticEntity(0f, 0f, 5, texture);
        Map<HexVector, String> texture2 = new HashMap<>();
        texture2.put(new HexVector(0f, 0f), "Grass");
        StaticEntity se2 = new StaticEntity(0f, 0f, 5, texture2);
        Map<HexVector, String> texture3 = new HashMap<>();
        se.setup();
        se.onTick(0);
    }

    @Test
    public void getChildrenPositions() {
        GameManager.get().setWorld(new TestWorld());
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(1f, 1f), "Grass");
        StaticEntity se = new StaticEntity(0f, 0f, 5, texture);
        assertTrue(se.getChildrenPositions().isEmpty());
    }

    @Test
    public void getTexture() {
        GameManager.get().setWorld(new TestWorld());
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(1f, 1f), "Grass");
        StaticEntity se = new StaticEntity(0f, 0f, 5, texture);
        assertEquals("error_box", se.getTexture());
    }

    @Test
    public void setChildren() {
        GameManager.get().setWorld(new TestWorld());
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(1f, 1f), "Grass");
        StaticEntity se = new StaticEntity(0f, 0f, 5, texture);
        Map<HexVector, String> texture2 = new HashMap<>();
        HexVector hv = new HexVector(0f, 1f);
        texture2.put(new HexVector(0f, 1f), "Null");
        se.setChildren(texture2);
        assertTrue(se.getChildrenPositions().contains(hv));
    }

    @Test
    public void closestCharacter() {
        CopyOnWriteArrayList<Tile> tiles = new CopyOnWriteArrayList<>();
        tiles.add(new Tile("Grass", 0f, 0f));
        tiles.add(new Tile("Grass", 120f, 0f));
        tiles.add(new Tile("Grass", 1f, 1f));
        TestWorld tw = new TestWorld();
        tw.setTileMap(tiles);
        Character chris = new Chris(0f,0f,true);
        Character damien = new Damien(120f,0f,true);
        GameManager.get().setWorld(tw);
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(1f, 1f), "Grass");
        Tile tile = GameManager.get().getWorld().getTile(0f, 0f);
        StaticEntity se = new StaticEntity(0f, 0f, 5, texture);
        GameManager.get().getWorld().addEntity(chris);
        GameManager.get().getWorld().addEntity(damien);
        GameManager.get().getWorld().addEntity(se);
        List<Character> chara = new ArrayList<>();
        chara.add(chris);
        chara.add(damien);
        GameManager.get().getWorld().initializeRemainingCharacter(chara);
        Pair<Character, Float> pair;
        pair = se.closestCharacter();
        Pair<Character, Float> pair2 = new Pair<>(chris, 0f);
        assertEquals(pair2, se.closestCharacter());
    }
}