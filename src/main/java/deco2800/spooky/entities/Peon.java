package deco2800.spooky.entities;

import deco2800.spooky.Tickable;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TaskPool;
import deco2800.spooky.tasks.AbstractTask;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Point;
import deco2800.spooky.util.Rectangle;

import static deco2800.spooky.util.WorldUtil.colRowToWorldCords;

public class Peon extends AgentEntity implements Tickable {
	protected transient AbstractTask task;
	private boolean pickable = false;
	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
	}

	/**
	 * Peon constructor
	 */
	public Peon(float col, float row, float speed) { //wrapped row and col
		super(col, row, 3, speed);
		this.setTexture("spacman_ded");
	}

	public boolean isPickable() {
		return pickable;
	}

	public void setPickable(boolean pickable) {
		this.pickable = pickable;
	}

	@Override
	public void onTick(long i) {
		if(task != null && task.isAlive()) {
			if(task.isComplete()) {
				this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
			}
			task.onTick(i);
		} else {
			this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
		}
	}

	public AbstractTask getTask() {
		return task;
	}

	public void setTask(AbstractTask task) {
		this.task = task;
	}


	/**
	 * Gives each peon a bounding box that approximates peon
	 * @param length the length of the bounding cube
	 * @param height the width of the bounding cube
	 * @return the bounding cube that approximates the peon
	 */
	public Rectangle[] getBoundingBox(float length, float height) {
		HexVector position = this.getPosition();
		//the position of the peon in world coordinates
		float[] worldPos = colRowToWorldCords(position.getCol(), position.getRow());
		//the bottom-left and top-right
		Point bottomLeft = new Point(worldPos[0] - length/2, worldPos[1]);
		Point topRight = new Point(worldPos[0] + length/2, worldPos[1] + height);
		Rectangle box = new Rectangle(bottomLeft, topRight);
		return new Rectangle[]{box};
	}

}
