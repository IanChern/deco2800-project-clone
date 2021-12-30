package deco2800.spooky.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.Tile;

public class BFSPathfinder implements Pathfinder {
	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(BFSPathfinder.class);


	@Override
	public List<Tile> pathfind(AbstractWorld world, HexVector origin, HexVector destination) {

		Tile originTile = getTileByHexVector(world, origin);
		Tile destinationTile = getTileByHexVector(world, destination);
		if (originTile == null || destinationTile == null) {
			return new ArrayList<>();
		}

		return pathfindBFS(originTile, destinationTile);

	}

	private List<Tile> pathfindBFS(Tile origin, Tile destination) {
		LinkedList<Tile> queue = new LinkedList<>();
		Set<Tile> closedSet = new HashSet<>();
		Map<Tile, Tile> path = new HashMap<>();
		queue.add(origin);
		
		Tile root;

		while (!queue.isEmpty()) {
			root = queue.remove();
			
			if (root.equals(destination)) {
				return reconstructPath(destination, path);
			}
			
			for (Tile child : root.getNeighbours().values()) {
				if (closedSet.contains(child) || queue.contains(child) || child.isObstructed()) {
					continue;
				}
				path.put(child, root);
				queue.add(child);
			}
			
			closedSet.add(root);
		}
		return new ArrayList<>();
	}
	
	private List<Tile> reconstructPath(Tile destination, Map<Tile, Tile> pathMap) {
		Tile root = destination;
		List<Tile> path = new ArrayList<>();
		path.add(root);
		while(pathMap.get(root) != null) {
			root = pathMap.get(root);
			path.add(0, root);
		}
		return path;
	}

	private Tile getTileByHexVector(AbstractWorld world, HexVector vector) {
		for (Tile tile : world.getTileMap()) {
			if ((int) tile.getCol() == (int) vector.getCol() && (int) tile.getRow() == (int) vector.getRow()) {
				return tile;
			}
		}
		return null;

	}

}
