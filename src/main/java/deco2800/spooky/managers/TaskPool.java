package deco2800.spooky.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deco2800.spooky.entities.AgentEntity;
import deco2800.spooky.tasks.AbstractTask;
import deco2800.spooky.tasks.MovementTask;
import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.Tile;

public class TaskPool implements AbstractManager {

	private List<AbstractTask> taskCollections;
	private AbstractWorld world;
	private Random random;

	public TaskPool() {
		taskCollections = new ArrayList<>();
		world = GameManager.get().getWorld();
		random = new Random();
	}
	
	public AbstractTask getTask(AgentEntity entity) {
		if (taskCollections.isEmpty()) {
			List<Tile> tiles = world.getTileMap();
			if (tiles.isEmpty()) {
				// There are no tiles
				return null;
			}
			Tile destination = tiles.get(random.nextInt(tiles.size()));
			return new MovementTask(entity, destination.getCoordinates());
		}
		return taskCollections.remove(0);
	}
}
