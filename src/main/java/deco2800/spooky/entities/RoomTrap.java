package deco2800.spooky.entities;

import deco2800.spooky.Tickable;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.tasks.TrapTask;
import deco2800.spooky.worlds.AbstractWorld;
import deco2800.spooky.worlds.Tile;

public class RoomTrap extends Peon implements Tickable {
    private TrapTask trapTask;
    private TrapType trapType;
    private static final String ENTITY_ID_STRING = "roomtrap";
    private Tile tile;
    private boolean stepped;
    private AbstractWorld world = GameManager.get().getWorld();
    private float speed;
    private int temp;
    public RoomTrap() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Constructor of RoomTrap to be placed in RandomWorld
     * @param col of the roomTrap located at a tile
     * @param row of roomTrap located at a tile
     * @param tile where the roomTrap can be found
     * @param trapType type of trap
     */
    public RoomTrap(float col, float row, Tile tile, TrapType trapType) {
        super(col, row, 0);
        this.trapType = trapType;
        this.setObjectName(ENTITY_ID_STRING);
        this.tile = tile;
        this.stepped = false;
        this.trapTask = new TrapTask(this);
        this.speed = 0;
        if (trapType == TrapType.QUICKSAND) {
            this.setTexture("closedSand");
        }
        if (trapType == TrapType.PITFALL) {
            this.setTexture("closedPit");
        }
        this.temp = 0;
    }

    /**
     * Getting the tile where the trap is located at
     * @return tile where the trap is located at
     */
    public Tile getTile() { return this.tile; }

    /**
     * Checks if the tile trap is already stepped or has activated
     * @return true iff the tile trap has been stepped by a player
     */
    public boolean isStep() { return this.stepped; }

    /**
     * Set a step boolean variable
     * @param step true or false
     */
    public void setStep(boolean step) { this.stepped = step; }

    /**
     * The type of trap
     * @return the type of trap
     */
    public TrapType trapTypeIs() { return this.trapType; }

    /**
     * get the original speed of the player that step on the trap
     * @return original speed of the player
     */
    public float getSpeed() { return this.speed; }

    public int getTemp() { return this.temp; }

    /**
     * On tick is called only when player stepped on a tile
     * @param i Current game tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (Character player: world.getRemainingPlayerList()) {
            if (player.isPlayerControlled() && player.getPosition().equals(tile.getCoordinates())) {
                if(this.temp == 0) {
                    this.temp = player.getKeyDown();
                }
                if (this.speed == 0) {
                    this.speed = player.getSpeed();
                }
                trapTask.openTrap(this, player);
            }
        }
    }

}
