package deco2800.spooky.tasks;

import deco2800.spooky.entities.AgentEntity;
import deco2800.spooky.util.HexVector;

/**
 * Implements the player movement as a task
 */
public class PlayerMovement extends AbstractTask {

    //Boolean to check if the task is complete
    private boolean complete;

    //Boolean checks if the task is still alive
    private boolean taskAlive = true;

    //The effected entity
    private AgentEntity entity;

    //The final destination to move to
    private HexVector destination;

    //The next part of the tile for the player to move to
    private HexVector nextTile;

    //The speed the player will move at
     float speed;

    /**
     * Class constructor for a new task
     *
     * @param entity The entity to move
     * @param destination The destination to move to
     * @param speed The speed the player will move to the next tile
     */
    public PlayerMovement(AgentEntity entity, HexVector destination, float speed) {
        super(entity);

        this.speed = speed;
        this.entity = entity;
        this.nextTile = entity.getPosition();
        this.destination = destination;
        this.complete = false;
    }

    /**
     * Ticks along with the player and continues animation until finished
     * Moves the player to the next part of the tile with speed being the speed the
     * player moves to the next tile
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {

        if(Math.abs(nextTile.getRow() - destination.getRow()) > entity.getSpeed() * speed) {
            this.complete = false;

            if(destination.getRow() > nextTile.getRow()) {
                nextTile.setRow(nextTile.getRow() + entity.getSpeed());
            } else if(destination.getRow() < nextTile.getRow()) {
                nextTile.setRow(nextTile.getRow() - entity.getSpeed());
            }

            this.entity.setPosition(nextTile.getCol(), nextTile.getRow(), entity.getHeight());
        } else {
            this.complete = true;
        }

        if(Math.abs(nextTile.getCol() - destination.getCol()) > entity.getSpeed() * speed) {
            this.complete = false;

            if(destination.getCol() > nextTile.getCol()) {
                nextTile.setCol(nextTile.getCol() + entity.getSpeed());
            } else if(destination.getCol() < nextTile.getCol()) {
                nextTile.setCol(nextTile.getCol() - entity.getSpeed());
            }

            this.entity.setPosition(nextTile.getCol(), nextTile.getRow(), entity.getHeight());
        }
    }

    /**
     * Checks if the task is complete
     *
     * @return boolean if task is complete
     */
    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * Checks if the task is still alive
     *
     * @return boolean if task is alive
     */
    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
