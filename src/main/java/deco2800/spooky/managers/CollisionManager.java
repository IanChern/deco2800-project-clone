package deco2800.spooky.managers;

import deco2800.spooky.Inventory.InventoryBox;
import deco2800.spooky.entities.*;
import deco2800.spooky.entities.Character;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Rectangle;
import deco2800.spooky.util.WorldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Collision manager currently only implements player collisions
 * but can be used later for other collisions
 */
public class CollisionManager implements AbstractManager {

    /**
     * Constructor to use the collision manager
     */
    public CollisionManager() {
        /* Nothing to do on construction */
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CollisionManager.class);
    /**
     * Checks if the walls are being collided into by the player
     *
     * @param destination The destination of the player
     * @return boolean values to determine if the player can move to the next tile or not
     */
    public boolean checkPlayerWallCollisions(HexVector destination) {
        List<AbstractEntity> entities = GameManager.get().getWorld().getSortedEntities();

        for(AbstractEntity entity : entities) {
            //Checks for collisions with either wall or rock
            if((entity instanceof Rock || entity instanceof StaticWall || entity instanceof InventoryBox)
                && checkBounds(entity, destination)) {
                    return(false);
                }
            }

        return(true);
    }

    /**
     * Checks if the walls are being collided into by the AI Creeps
     *
     * @param destination The destination of the player
     * @return boolean values to determine if the player can move to the next tile or not
     */
    public boolean checkCreepWallCollisions(HexVector destination) {
        List<AbstractEntity> entities = GameManager.get().getWorld().getSortedEntities();

        for(AbstractEntity entity : entities) {

            //Checks for collisions with either wall or rock
            if((entity instanceof Rock || entity instanceof StaticWall || entity instanceof InventoryBox||
                    entity instanceof Chris || entity instanceof Damien || entity instanceof Jane ||
                    entity instanceof Katie || entity instanceof Larry || entity instanceof Titus)
                && checkBounds(entity, destination)) {
                    return(false);
                }
            }
        LOGGER.info("The creep is colliding with the wall");
        return(true);
    }

    /**
     *
     * @param destination
     * @return
     */
    // checking the collision between the characters and the creeps

    /**
     * Checks if the Doors are being collided into by the player
     *
     * @param destination The destination of the player
     * @return boolean values to determine if the player can move to the next tile or not
     */
    public boolean checkPlayerDoorCollisions(HexVector destination) {
        List<AbstractEntity> entities = GameManager.get().getWorld().getSortedEntities();

        for(AbstractEntity entity : entities) {
            //Checks for collisions door
            if((entity instanceof Door)
                    && checkBounds(entity, destination)) {
                return(false);
            }
        }
        return(true);
    }

    /**
     * Checks the coordinates of the players destination to the walls coordinates
     * This can be implemented with a bounding box later if tile based movement is disagreed upon
     *
     * @param entity The wall entity coordinates
     * @param destination The HexVector of the players destination
     * @return boolean value if the wall collides with the player
     */
    private boolean checkBounds(AbstractEntity entity, HexVector destination) {
        return entity.getRow() == destination.getRow() && entity.getCol() == destination.getCol();
    }
}
