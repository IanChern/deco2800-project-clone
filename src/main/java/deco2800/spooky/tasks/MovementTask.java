package deco2800.spooky.tasks;

import java.util.List;

import deco2800.spooky.entities.*;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.PathFindingService;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.worlds.Tile;

public class MovementTask extends AbstractTask {

	private boolean complete;

	private boolean computingPath = false;
	private boolean taskAlive = true;

	AgentEntity entity;
	HexVector destination;

	private List<Tile> path;

	public MovementTask(AgentEntity entity, HexVector destination) {
		super(entity);
		this.entity = entity;
		this.destination = destination;
		this.complete = false;
	}

	@Override
	public void onTick(long tick) {
		if(path != null) {
			//we have a path
			if(path.isEmpty()) {
				complete = true;
			} else {
				entity.moveTowards(path.get(0).getCoordinates());
				//This is a bit of a hack.
				if(entity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
					path.remove(0);
				}
			}
		} else if (computingPath) {
			// change sprite to show waiting??


		} else {
			//ask for a path
			computingPath = true;
			GameManager.get().getManager(PathFindingService.class).addPath(this);
		}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	public void setPath(List<Tile> path) {
		if (path == null) {
			taskAlive = false;
		}
		this.path = path;
		computingPath = false;
	}

	public List<Tile> getPath() {
		return path;
	}

	@Override
	public boolean isAlive() {
		return taskAlive;
	}

}
