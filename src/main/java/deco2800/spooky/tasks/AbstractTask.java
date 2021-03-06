package deco2800.spooky.tasks;

import deco2800.spooky.Tickable;
import deco2800.spooky.entities.AgentEntity;

public abstract class AbstractTask implements Tickable {
	
	protected AgentEntity entity;
	
	
	public AbstractTask(AgentEntity entity) {
		this.entity = entity;
	}
	
	public abstract boolean isComplete();

	public abstract boolean isAlive();

}
