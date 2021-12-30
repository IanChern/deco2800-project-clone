package deco2800.spooky.util;

import java.util.List;

import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.Tile;

public interface Pathfinder {
	
	List<Tile> pathfind(AbstractWorld world, HexVector origin, HexVector destination);

}
