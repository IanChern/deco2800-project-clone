package deco2800.spooky.entities.Creep;


import deco2800.spooky.Tickable;
import deco2800.spooky.entities.Appearance;
import deco2800.spooky.entities.Character;
import deco2800.spooky.managers.CollisionManager;
import deco2800.spooky.tasks.PlayerMovement;
import deco2800.spooky.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Random;

public class Mummy extends Character implements Tickable {

    private HexVector destinationTile = new HexVector(0f, 0f);
    private CollisionManager collisionManager = new CollisionManager();
    private int health;
    private long moveCoolDownTime = 2000;
    private long movedTime;
    int tickCount = 0;
    private boolean canMove = false;

    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    public Mummy(float col, float row , boolean playerControlled) {
        super(new Appearance("mummy"),50,0.02f,6, col, row, false);
        this.getAppearance().setDefaultAppearance("mummy");
        this.setTexture(this.getAppearance().getDefaultAppearance());
        this.health = 50;
        this.setObjectName("Mummy");
    }

    /**
     * Moves this mummy to a given location
     * Uses PlayerMovement to move
     *
     * @param colMove - column to move to
     * @param rowMove - row to move to
     */
    public void mummyMove(float colMove, float rowMove) {
        HexVector destinationChecker = new HexVector(destinationTile.getCol() + colMove,
                destinationTile.getRow() + rowMove);
        this.canMove = collisionManager.checkCreepWallCollisions(destinationChecker);
        if (canMove == true) {
            this.destinationTile.setCol(destinationTile.getCol() + colMove);
            this.destinationTile.setRow(destinationTile.getRow() + rowMove);
            this.task = new PlayerMovement(this, this.destinationTile, 0.5f);
            logger.info("Mummy {} moving towards {},{}", this.getEntityID(), destinationTile.getCol(),
                    destinationTile.getRow());
        }
        logger.warn("Mummy {} was unable to move due to a collision", this.getEntityID());
    }

    /**
     * Handles mummy movement up and down
     *
     * @param limit the limit for the row
     * @param rowChange the changed value for row
     */
    private void verticalMovement(float limit, float rowChange) {
        if (destinationTile.getRow() < limit) {
            mummyMove(0f, rowChange);
        }
    }

    /**
     * Given a random integer, determines which direction for the mummy to move.
     * Mummy can move in any of the six directions.
     *
     * @param direction - int (0 - 5), direction interger for mummy to move in
     */
    public void randomMove(int direction) {
        switch (direction) {
            case 0 : //move up
                verticalMovement(4f, 1f);
                break;
                
            case 1 : //move down
                verticalMovement(-5f, -1f);
                break;
            case 2 : //move SE
                if(destinationTile.getCol() < 10f && destinationTile.getRow() > -5f) {
                    mummyMove(1f, -0.5f);
                }
                break;
            case 3 : //move SW
                if(destinationTile.getCol() > -11f && destinationTile.getRow() > -5f) {
                    mummyMove(-1f, -0.5f);
                }
                break;
            case 4 : //move NE
                if(destinationTile.getCol() < 10f && destinationTile.getRow() < 4f) {
                    mummyMove(1f, 0.5f);
                }
                break;
            case 5 : //move NW
                if(destinationTile.getCol() > -11f && destinationTile.getRow() < 4f) {
                    mummyMove(-1f, 0.5f);  
                }
                break;

            default:
                mummyMove(0, 0);
        }
    }

    @Override
    public void onTick(long i) {

        long currentTime = System.currentTimeMillis();
        Random r = new Random();

        tickCount++;

        if (health == 0) {
            die();
        }
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }

        /**
         * Uses game time to ensure mummy doesn't move too quickly
         * Random direction chosen using a Random().nextInt
         */
        if ((currentTime - movedTime) >= moveCoolDownTime) {
            int direction = r.nextInt(6);
            randomMove(direction);
            movedTime = currentTime;
        }
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof Mummy) {
            return ((Mummy) o).position.getCol() == position.getCol() && ((Mummy) o).position.getRow() == position.getRow();
        }
        return(false);
    }

    /**
     * Used to make SonarQube happy but returns pos * 100 as int
     *
     * @return pos * 100 as int
     */
    @Override
    public int hashCode() {
        return((int)(position.getRow() * position.getRow() * 100));
    }

}
