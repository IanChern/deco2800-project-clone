package deco2800.spooky.entities.Creep;


import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.Tickable;
import deco2800.spooky.entities.Appearance;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Pillar;
import deco2800.spooky.managers.CollisionManager;
import deco2800.spooky.tasks.PlayerMovement;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Pathfinder;
import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.RandomWorld;

public class Guard extends Character implements Tickable {

    private int health;
    private static ArrayList<Character> playerList;
    private static ArrayList<Pillar> pillarList;
    int tickCount = 0;
    float startCol;
    float startRow;
    HexVector startPosition;
    boolean triggered = false;
    boolean inRange = false;
    int hits = 0;
    private boolean canMove = false;
    private HexVector destinationTile = new HexVector(0f, 0f);
    private CollisionManager collisionManager = new CollisionManager();
    private Pathfinder pathFinder;

    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public Guard(float col, float row, boolean playerControlled) {
        super(new Appearance("guard"),200,0.5f,3, col, row, false);
        this.getAppearance().setDefaultAppearance("guard");
        this.setTexture(this.getAppearance().getDefaultAppearance());
        this.health = 200;
        this.setObjectName("Guard");
        this.startCol = col;
        this.startRow = row;
        this.startPosition = new HexVector(col, row);
    }

    /**
     * Function which handles what the guard will do on each game tick
     * Guard only starts to chase when a player has come in range
     * Once triggered, guard will chase player forever.
     */
    public void updateGuard() {
        playerList = (ArrayList<Character>) RandomWorld.getPlayerList();
        pillarList = (ArrayList<Pillar>) AbstractWorld.getPillarList();
        for (Character player : playerList) {
            if (player.isPlayerControlled()) {
                if ((player.getCol() - this.startPosition.getCol()) <= 2f && ((player.getRow() - (this.startPosition.getRow() + 1f)) <= 2f)) {
                    this.triggered = true;
                    logger.info("Guard {} was triggered by player {}", this.getEntityID(), player.getEntityID());
                }

                if (triggered && inRange(player)) {
                    logger.info("Guard {} chasing player {}", this.getEntityID(), player.getEntityID());
                    chasePlayer(player);        
                } else {
                    this.task = new PlayerMovement(this, this.startPosition, 1f);
                }
            }
        }
    }

    public boolean inRange(Character player) {
        if (player.getCol() <= this.startPosition.getCol() + 2f && player.getRow() <= this.startPosition.getRow() + 1f) {
            return(true);
        } else if (player.getCol() <= this.startPosition.getCol() - 2f && player.getRow() <= this.startPosition.getRow() - 1f) {
            return(true);
        } else {
            return(false);
        }
    }
    /**
     * Called when a player is in range of the guard
     * Will hit the player every 100 ticks
     *
     * @param player - the player to be hit
     */
    public void attackPlayer(Character player) {
        this.hits++;
        if (this.hits % 100 == 0) {
            player.setHealth(player.getHealth() - 5);
            logger.info("Guard {} hit player {}", this.getEntityID(), player.getEntityID());
        }

    }


    /**
     * Chase the selected player
     * Uses the moveTowards method to move towards a players position
     *
     * @param player - the selected player to be chased
     */
    public void chasePlayer(Character player) {
        guardMove(player);
        if (player.getCol() == this.getCol() && player.getRow() == this.getRow()){
            attackPlayer(player);
        }
    }

    public void guardMove(Character player) {
        this.destinationTile.setCol(player.getCol());
        this.destinationTile.setRow(player.getRow());
        this.task = new PlayerMovement(this, this.destinationTile, 0.5f);
        logger.info("Guard {} moving towards {},{}", this.getEntityID(), player.getCol(), player.getRow());
    }

    

    public float getStartCol() {
        return startCol;
    }

    public float getStartRow() {
        return startRow;
    }

    public HexVector getStartPosition() {
        return startPosition;
    }

    @Override
    public void onTick(long i) {

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

        updateGuard();

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Guard) {
            return ((Guard) o).position.getCol() == position.getCol() && ((Guard) o).position.getRow() == position.getRow();
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
