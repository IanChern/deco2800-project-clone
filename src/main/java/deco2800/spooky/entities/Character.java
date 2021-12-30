package deco2800.spooky.entities;

import static deco2800.spooky.util.WorldUtil.colRowToWorldCords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.Tickable;
import deco2800.spooky.managers.*;
import deco2800.spooky.networking.Player;
import deco2800.spooky.observers.KeyDownObserver;
import deco2800.spooky.observers.KeyUpObserver;
import deco2800.spooky.observers.TouchDownObserver;
import deco2800.spooky.tasks.AbstractTask;
import deco2800.spooky.tasks.PlayerMovement;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Point;
import deco2800.spooky.util.Rectangle;
import deco2800.spooky.util.WorldUtil;
import deco2800.spooky.worlds.RandomWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import deco2800.spooky.entities.Creep.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static deco2800.spooky.util.WorldUtil.colRowToWorldCords;

/**
 * This Class consist general attributes and methods for characters. This class
 * is an abstract class.
 */
public abstract class Character extends Peon
        implements Tickable, TouchDownObserver, KeyDownObserver, KeyUpObserver, HasHealth, Cloneable, HasVariableTextures {
    protected transient AbstractTask task;

    private long tick = 0;
    private boolean killed = false; //Used for creeps to know whether their death has been dealt with

    static final float SPEED_HIGH = 0.100f;

    static final int HEALTH_MID = 75;

    static final int VISUAL_SIGHT_DEFAULT = 6;

    private boolean traitor; // true if it is a traitor

    // a character's health and armor
    private int health;

    // the moving speed of a character
    private float speed;

    // the visual sight of a character
    private int visualSight;

    // the character appearance
    private Appearance appearance;


    private MeleeWeapon currentWeapon;
    private ArrayList<MeleeWeapon> weapons = new ArrayList<>();
    private boolean mainCharacter;
    private boolean keyCodeNW;
    private boolean keyCodeUp;
    private boolean keyCodeNE;
    private boolean keyCodeSW;
    private boolean keyCodeDown;
    private boolean keyCodeSE;
    private boolean keyCodeSPACE = false;
    private static final String BUTTONW = "up";
    private static final String BUTTONS = "down";
    private static final String BUTTONA = "SouthW";
    private static final String BUTTOND = "SouthE";
    private static final String BUTTONE = "NorthE";
    private static final String BUTTONQ = "NorthW";
    private HexVector destinationTile = new HexVector(0f, 0f);
    private boolean canMove = false;
    private int movementCounter = 0;
    private boolean movementAlreadySet = false;
    private String direction = BUTTONS;
    private char lastPressed = 'S';
    private CollisionManager collisionManager = new CollisionManager();
    private static CreepFactory creepFactory;
    private int keyDown = 0;

    private static final float BOX_LENGTH = 0.7f;

    private static List<Character> playerList = new ArrayList<>();

    private boolean playerControlled;

    private final Logger logger = LoggerFactory.getLogger(Character.class);

    int tickCount = 0;

    private TextureAtlas.AtlasRegion currentTexture;
    private float elapsedTime;


    /**
     * Initialization of a character, for sub-classes using. This Class cannot be
     * instantiable.
     *
     * @param appearance       the appearance of the Character.
     * @param health           the health of the character.
     * @param speed            the speed of the character.
     * @param visualSight      the visual sight of the character.
     * @param col              the column located of the character.
     * @param row              the row located of the character.
     * @param playerControlled Is player Controlling this Character?
     */
    public Character(Appearance appearance, int health, float speed, int visualSight, float col, float row, boolean playerControlled) {
        super(col, row, speed);

        this.health = health;
        this.speed = speed;
        this.mainCharacter = false;
        this.visualSight = visualSight;
        this.appearance = appearance;
        this.appearance = new Appearance(null);
        this.setObjectName("playerCharacter");
        this.playerControlled = playerControlled;

        if (this.playerControlled) {
            GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
            GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
            GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
        }


    }

    /**
     * Get the key being pressed
     * @return the keycode of the key that is being pressed
     */
    public int getKeyDown() {
        return this.keyDown;
    }

    /**
     * Check whether the character is deado
     * @return true iff the character is killed or dead
     */
    public boolean isKilled() {
        return(killed);
    }

    /**
     * Set the character to dead
     * @param dead whether the character is dead
     */
    public void setKilled(boolean dead) {
        killed = dead;
    }

    /**
     * Check if the character is controlled by the player
     * @return true iff the character is controlled by the player
     */
    public boolean isPlayerControlled() {
        return playerControlled;
    }

    /**
     * Returns the direction that the player is facing
     *
     * @return String direction that the player is facing
     */
    public String getDirection() {
        return(direction);
    }

    /**
     * Check if this character is the main character
     * @return true iff it is a main character
     */
    public boolean isMainCharacter() {
        return this.mainCharacter;
    }


    /**
     * Set the character to be a main character
     * @param mainCharacter the boolean value to be set to
     */
    public void setMainCharacter(boolean mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Set the character is or is not a traitor
     *
     * @param traitor true if this character is a traitor
     */
    public void setTraitor(boolean traitor) {
        this.traitor = traitor;
    }

    /**
     * @return true, if this character is a traitor. false, if this character is not
     *         a traitor.
     */
    public boolean isTraitor() {
        return traitor;
    }

    /**
     * @return the health of the character
     */
    public int getHealth() {
        return health;
    }

    /**
     * set new health amount of the character if the new health is below or equal to
     * 0, the character die.
     * @param newHealth new health amount of the character
     */
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    /**
     * set new speed of the character
     * 
     * @param newSpeed new speed of the character
     */
    public void setSpeed(float newSpeed) {
        speed = newSpeed;
    }

    /**
     * @return speed of the character
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * set visual sight of the character
     * 
     * @param visualSight visual sight of the character
     */
    public void setVisualSight(int visualSight) {
        this.visualSight = visualSight;
    }

    /**
     * @return visual sight of the character
     */
    public int getVisualSight() {
        return visualSight;
    }

    /**
     * set character's new appearance
     * 
     * @param appearance the appearance of the character to be set
     */
    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    /**
     * get current character's appearance
     * 
     * @return character's appearance
     */
    public Appearance getAppearance() {
        return appearance;
    }

    /**
     * Set the character to died
     */
    public void die() {
        GameManager.get().characterDied(this);
        if (this.playerControlled) {
            GameManager.get().getManager(PopUpManager.class).displayPopUpMessage("Minor", "You are dead",0);
        }
        
    }

    /**
     * Set the destination of the character
     * @param newPos the new destination of the character
     */
    public void setDestination(HexVector newPos) {
        this.destinationTile = newPos;
    }

    @Override
    public void onTick(long i) {
        tickCount++;
        if (health <= 0) {
            die();
        }
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }

        //Get walking animation
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (new ArrayList<String>(Arrays.asList(BUTTONW, BUTTONS,BUTTONE,BUTTONQ,BUTTOND,BUTTONA)).contains(direction) && movementCounter != 0) {
            try {
                elapsedTime += Gdx.graphics.getDeltaTime();
                this.currentTexture = appearance.getWalkingAppearance(direction).getKeyFrame(elapsedTime,true);
            } catch (Exception e) {
                logger.error("Thrown exception e");
            }
        }

        //Get attacking animation
        if (keyCodeSPACE) {
            String movement = getMovement();
            this.currentTexture = appearance.getMeleeAttackApprearance(currentWeapon.getName(),
            direction, movement).getKeyFrame(elapsedTime, true);
        }
//      check for character movements and move if conditions met
        move();

        tick++;
        if (tick > 20 && keyCodeSPACE) {
            tick = 0;

            meleeAttack();
        }

    }

    /**
     * Check the key pressed to get the movement status of the character
     * @return "walking" if any of the movement key are being pressed. "standing" otherwise.
     */
    public String getMovement() {
        String movement;
        if (!keyCodeDown && !keyCodeUp && !keyCodeNE && !keyCodeNW && !keyCodeSW && !keyCodeSE) {
            movement = "standing";
        } else {
            movement = "walking";
        }
        return movement;
    }

    /**
     * Moves the player in the tick method
     * Checks if the player is able to move then starts the animation to move them
     * If players is not moving then set the player to neutral position of the last moved direction
     */
    private void move() {
        //check for character movements and move if conditions met
        GameManager.get().getCamera().position.x = WorldUtil.colRowToWorldCords(getCol(), getRow())[0];
        GameManager.get().getCamera().position.y = WorldUtil.colRowToWorldCords(getCol(), getRow())[1];

        if(task != null && !task.isComplete() && canMove) {
            this.task = new PlayerMovement(this, this.destinationTile, speed);
        } else if((keyCodeDown || keyCodeNE || keyCodeNW || keyCodeSE || keyCodeSW || keyCodeUp) &&
                movementCounter < 7 && !movementAlreadySet) {
            //Sets the player to a particular direction before moving them
            directionSet(keyCodeUp);
        } else if(keyCodeNW) { //North West
            moveSet(-1f, 0.5f);
        } else if(keyCodeNE) { //North East
            moveSet(1f, 0.5f);
        } else if(keyCodeUp) { //UP
            moveSet(0f, 1f);
        } else if(keyCodeDown) { //DOWN
            moveSet(0f, -1f);
        } else if(keyCodeSW) { //South West
            moveSet(-1f, -0.5f);
        } else if(keyCodeSE) { //South East
            moveSet(1f, -0.5f);
        } else if (keyCodeSPACE) {
            moveSet(0, 0);
        } else {
            resetMove();
        }
    }

    private void resetMove() {
        movementCounter = 0;
        stopWalk(direction);
        this.currentTexture = null;
    }

    /**
     * Checks if the last key press is the same as the current key press
     *
     * @param keyPress The key that was pressed
     * @return boolean if keyPress and lastPressed are equal
     */
    private boolean moveCheckDirection(char keyPress) {
        return (keyPress == lastPressed);
    }

    /**
     * Sets the direction the player is facing when a key is pressed
     */
    public void directionSet(boolean keyCodeUp) {
        if(keyCodeUp) {
            this.direction = BUTTONW;
            this.lastPressed = 'W';
        } else if(keyCodeSW) {
            this.direction = BUTTONA;
            this.lastPressed = 'A';
        } else if(keyCodeSE) {
            this.direction = BUTTOND;
            this.lastPressed = 'D';
        } else if(keyCodeDown) {
            this.direction = BUTTONS;
            this.lastPressed = 'S';
        } else if(keyCodeNW) {
            this.direction = BUTTONQ;
            this.lastPressed = 'Q';
        } else if(keyCodeNE) {
            this.direction = BUTTONE;
            this.lastPressed = 'E';
        }
        this.movementCounter++;
    }

    /**
     * Sets the movement for the move method as well as checks if the collisions are allowed
     *
     * @param colMove The movement along the x axis
     * @param rowMove The movement along the y axis
     */
    public void moveSet(float colMove, float rowMove) {
        HexVector destinationChecker = new HexVector(destinationTile.getCol() + colMove, destinationTile.getRow() + rowMove);
        this.canMove = collisionManager.checkPlayerWallCollisions(destinationChecker);

        //if a player is located where a door is change room
        if(!collisionManager.checkPlayerDoorCollisions(destinationChecker)) {
            //move character to one of the random rooms
            changeRooms();
            GameManager.get().getManager(SoundManager.class).playSound("door.wav",1);
            setPosition(destinationTile.getCol(), destinationTile.getRow(), this.getHeight());
            return;
        }

        if(canMove) {
            this.destinationTile.setCol(destinationTile.getCol() + colMove);
            directionSet(keyCodeUp);
            destinationTile.setRow(destinationTile.getRow() + rowMove);
            this.task = new PlayerMovement(this, this.destinationTile, speed);
        }
    }


    /**
     * Helper function to move player between rooms
     */
    private void changeRooms(){
        Random rand =  new Random();
        int coordsToMove = rand.nextInt(6);
        float x = 0;
        float y = 0;

        switch (coordsToMove) {
            case 0:
                x = 0;
                y = 0;
                break;
            case 1:
                x = 0;
                y = 20;
                break;
            case 2:
                x = 20;
                y = 0;
                break;
            case 3:
                x = 20;
                y = 20;
                break;
            case 4:
                x = -20;
                y = 0;
                break;
            case 5:
                x = 0;
                y = -20;
                break;
            case 6:
                x = 0;
                y = 40;
                break;
            case 7:
                x = -20;
                y = -20;
                break;
            default:
                logger.warn("Room Does Not Exist");
        }

        destinationTile.setRow(y);
        destinationTile.setCol(x);
    }

    /**
     * Create bounding rectangles that approximate the head, chest, feet of the peon
     *
     * @param length the length of the bounding cube
     * @param height the width of the bounding cube
     * @return an array of bounding rectangles
     */
    public Rectangle[] getBoundingBox(float length, float height) {
        Rectangle headBox;
        Rectangle chestBox;
        Rectangle feetBox;

        // the HexVector position of the player
        HexVector position = this.getPosition();
        // the position in world coordinates
        float[] worldPos = colRowToWorldCords(position.getCol(), position.getRow());
        // the bottom-left and top-right point of the rectangle bounding the head of the
        // character
        Point bottomLeftHead = new Point(worldPos[0] - length / 2, worldPos[1] + 0.8f * height);
        Point topRightHead = new Point(worldPos[0] + length / 2, worldPos[1] + height);
        headBox = new Rectangle(bottomLeftHead, topRightHead);
        // the bottom-left and top-right point of the rectangle bounding the chest of
        // the character
        Point bottomLeftChest = new Point(worldPos[0] - length / 2, worldPos[1] + 0.4f * height);
        Point topRightChest = new Point(worldPos[0] + length / 2, worldPos[1] + 0.8f * height);
        chestBox = new Rectangle(bottomLeftChest, topRightChest);
        // the bottom-left and top-right point of the rectangle bounding the feet of the
        // character
        Point bottomLeftFeet = new Point(worldPos[0] - length / 2, worldPos[1]);
        Point topRightFeet = new Point(worldPos[0] + length / 2, worldPos[1] + 0.4f * height);
        feetBox = new Rectangle(bottomLeftFeet, topRightFeet);

        return new Rectangle[] { headBox, chestBox, feetBox };
    }

    /**
     * Detect how two peons overlap with each other.
     *
     * @param peon         the other peon that potentially collide with this peon
     * @param length       the length of the bounding rectangle that approximates
     *                     the peon
     * @param playerHeight the height of the bounding rectangle that approximates
     *                     the player
     * @param peonHeight   the height of the bounding rectangle that approximates
     *                     the player
     **/
    public int overlaps(Peon peon, float length, float playerHeight, float peonHeight) {

        Rectangle peonBox = peon.getBoundingBox(length, peonHeight * WorldUtil.TILE_HEIGHT)[0];
        Rectangle[] playerBoxes = this.getBoundingBox(BOX_LENGTH * 0.75f * WorldUtil.TILE_WIDTH,
                playerHeight * WorldUtil.TILE_HEIGHT);
        Rectangle superBox = super.getBoundingBox(BOX_LENGTH * 0.75f * WorldUtil.TILE_WIDTH,
                playerHeight * WorldUtil.TILE_HEIGHT)[0];
        if (!superBox.overlaps(peonBox)) {
            return -1; // no collision
        } else if (playerBoxes[0].overlaps(peonBox)) {
            return 0; // peon collides with the head of the character
        } else if (playerBoxes[1].overlaps(peonBox)) {
            return 1; // peon collides with the chest of the character
        } else {
            return 2; // peon collides with the feet of the character
        }
    }

    /**
     * move character to the position where mouse is touching down
     *
     * @param screenX the x position the mouse was pressed at
     * @param screenY the y position the mouse was pressed at
     * @param pointer
     * @param button  the button which was pressed
     */
    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {

    }

    /**
     * Set the neutral texture to the character
     * 
     * @param facingDirection the facing direction
     */
    public void stopWalk(String facingDirection) {
        setTexture(this.appearance.getNeutralDirectionAppearance(facingDirection));
    }

    private void checkDiagonalMovement(int keycode) {
        if (keycode == Input.Keys.Q ) {// NW side
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection( 'Q');
            }
            this.keyCodeNW = true;
        }
        if (keycode == Input.Keys.E ) {// NE side
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection('E');
            }
            this.keyCodeNE = true;
        }
    }

    /**
     * Checks for if the user has pressed a movement based key
     *
     * @param keycode the code for the key pressed
     */
    private void moveKeyDown(int keycode) {
        checkDiagonalMovement(keycode);
        if (keycode == Input.Keys.W ) {// UP
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection('W');
            }
            this.keyCodeUp = true;
        }
        if (keycode == Input.Keys.A ) {// SW side
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection('A');
            }
            this.keyCodeSW = true;
        }
        if (keycode == Input.Keys.S ) {// DOWN
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection('S');
            }

            this.keyCodeDown = true;
        }
        if (keycode == Input.Keys.D) {// SE side
            if (!keyCodeSPACE) {
                this.movementAlreadySet = moveCheckDirection('D');
            }
            this.keyCodeSE = true;
        }
        this.keyDown++;
    }

    /**
     * character should walk when any key is being pressed down
     * 
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        moveKeyDown(keycode);

        if (keycode == Input.Keys.SPACE && currentWeapon != null) {
            keyCodeSPACE = true;
            meleeAttack();
        }
        if (keycode == Input.Keys.G && currentWeapon != null) {
            dropCurrentWeapon();
        }
        if (keycode == Input.Keys.NUM_1) {
            switchWeapon(1);
        }
        if (keycode == Input.Keys.NUM_2) {
            switchWeapon(2);
        }
        if (keycode == Input.Keys.NUM_3) {
            switchWeapon(3);
        }
        if (keycode == Input.Keys.NUM_4) {
            switchWeapon(4);
        }
    }

    /**
     * character should stop walking if any key is being notify pressed up
     * 
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        if (keycode == Input.Keys.Q) {// NW side
            this.keyCodeNW = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.W) {// UP
            this.keyCodeUp = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.E) {// NE side
            this.keyCodeNE = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.A) {// SW side
            this.keyCodeSW = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.S) {// DOWN
            this.keyCodeDown = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.D) {// SE side
            this.keyCodeSE = false;
            this.movementCounter = 0;
        }
        if (keycode == Input.Keys.SPACE) {
            this.keyCodeSPACE = false;
            //just the naming. it is for melee.
            meleeAttack();
        }
    }

    /**
     * refill character's health to 100
     */
    public void refillHealth() {
        this.setHealth(100);
    }

    /**
     * Update the health of the character by a difference
     * 
     * @param diff the difference between the original health and new health
     */
    public void updateHealth(int diff) {
        if (this.getHealth() + diff < 0) {
            this.setHealth(0);
            return;
        }
        if (this.getHealth() + diff > 100) {
            this.setHealth(100);
            return;
        }
        this.health += diff;
    }

    /**
     * Generate a random integer within a given range
     * @param min the lower bound
     * @param max the upper bound
     * @return a random integer within the given range
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    public void playerAttack() {
        direction = this.getDirection();
        switch(direction) {
            case "BUTTONW" :  {
                for (Character creeps : RandomWorld.getCreepFactory().getCreeps()) {
                    if (creeps.getCol() == this.getCol() + 0.5f && creeps.getRow() == this.getRow()) {
                        creeps.updateHealth(-5);
                    }
                }
            }
        }
    }

    /**
     * A player attacks an enemy using the melee weapon
     */
    public void meleeAttack() {
        if (RandomWorld.getPlayerList() != null) {
            //sound
            GameManager.get().getManager(SoundManager.class).playSound("move_effect.mp3",1);
            for (Character enemy : playerList) {
                if (!enemy.isMainCharacter()){
                    HexVector playerPosition = this.getPosition();

                    if (Math.round(playerPosition.getCol()) == Math.round(enemy.getCol()) &&
                    Math.round(playerPosition.getRow()) == Math.round(enemy.getRow())) {
                        enemy.setHealth(enemy.getHealth() - 20);
                        logger.info("Player {} hit Enemy {}", this.getEntityID(), enemy.getEntityID());
                    }
                    }
                }
            for (Character mummy : RandomWorld.getCreepFactory().getMummies()) {
                HexVector mummyPos = mummy.getPosition();
                HexVector playerPosition = this.getPosition();
                if (Math.round(playerPosition.getCol()) == Math.round(mummyPos.getCol()) 
                && Math.round(playerPosition.getRow()) == Math.round(mummyPos.getRow())) {
                    mummy.setHealth(mummy.getHealth() - 50);
                    logger.info("Player {} hit Mummy {}", this.getEntityID(), mummy.getEntityID());
                }
            }
            for (Character guard : RandomWorld.getCreepFactory().getGuards()) {
                HexVector guardPos = guard.getPosition();
                HexVector playerPosition = this.getPosition();

                if (Math.round(playerPosition.getCol()) == Math.round(guardPos.getCol()) && Math.round(playerPosition.getRow()) == Math.round(guardPos.getRow())) {
                    guard.setHealth(guard.getHealth() - 10);
                    logger.info("Player {} hit Guard {}", this.getEntityID(), guard.getEntityID());
                }
            }
        }
    }

    /**
     * Get the current texture of the character
     * @return the current texture of the character
     */
     @Override
     public TextureAtlas.AtlasRegion getCurrentTexture() {
        return currentTexture;
    }

    /**
     * Character picks up a weapon
     * @param meleeWeapon the melee weapon to be picked up
     *        the order will be HANDBAG, SWORD, DAGGER, AXE
     */
    public void pickUpWeapon(MeleeWeapon meleeWeapon) {
        weapons.add(meleeWeapon);
        GameManager.get().getManager(SoundManager.class).playSound("itemPickUp.wav",1);
     }

    /**
     * Find the weapon of a specific weapon type
     * @param meleeType the type of the melee weapon
     * @return the melee weapon of a certain type
     */
     public MeleeWeapon findWeapon(MeleeType meleeType) {
         for (MeleeWeapon weapon : weapons) {
             if (weapon.getName() == meleeType) {
                 return weapon;
             }
         }
         return null;
     }


    /**
     * Character drops a weapon
     */
    public void dropCurrentWeapon() {
        int currentWeaponIndex = this.weapons.indexOf(this.currentWeapon);
        this.currentWeapon = null;
        this.weapons.set(currentWeaponIndex, null);
        HexVector playerPos = getPosition();
        currentWeapon.setPosition(playerPos.getRow(), playerPos.getCol(), 1);
        GameManager.get().getWorld().addEntity(currentWeapon);
    }

    /**
     * Switch to a new melee weapon
     * @param meleeID the ID of the melee weapon to switch to
     */
    public void switchWeapon(int meleeID) {
        if (meleeID == 1) {
            //Axe
            MeleeWeapon weapon = findWeapon(MeleeType.AXE);
            if (weapon != null) {
                currentWeapon = weapon;
            }
        } else if (meleeID == 2) {
            //Dagger
            MeleeWeapon weapon = findWeapon(MeleeType.DAGGER);
            if (weapon != null) {
                currentWeapon = weapon;
            }
        } else if (meleeID == 3) {
            //Handbag
            MeleeWeapon weapon = findWeapon(MeleeType.HANDBAG);
            if (weapon != null) {
                currentWeapon = weapon;
            }
        } else {
            //Sword
            MeleeWeapon weapon = findWeapon(MeleeType.SWORD);
            if (weapon != null) {
                currentWeapon = weapon;
            }
        }
    }

    /**
     * Get the collection of melee weapons
     * @return a collection of melee weapons
     */
    public List<MeleeWeapon> getWeapons() {
        return new ArrayList<>(this.weapons);
    }

    /**
     * Get the destination tile the character is moving towards
     * @return the destination tile of the character
     */
    public HexVector getDestination() {
        return destinationTile;
    }

    /**
     * Set keyCodeSE
     * @param keyCodeSE new keyCodeSPACE to be set to
     */
    public void setKeyCodeSE(boolean keyCodeSE) {
        this.keyCodeSE = keyCodeSE;
    }

    /**
     * Set keyCodeSW
     * @param keyCodeSW new keyCodeSPACE to be set to
     */
    public void setKeyCodeSW(boolean keyCodeSW) {
        this.keyCodeSW = keyCodeSW;
    }

    /**
     * Set keyCodeUp
     * @param keyCodeUp new keyCodeSPACE to be set to
     */
    public void setKeyCodeUp(boolean keyCodeUp) {
        this.keyCodeUp = keyCodeUp;
    }

    /**
     * Set keyCodeNE
     * @param keyCodeNE new keyCodeSPACE to be set to
     */
    public void setKeyCodeNE(boolean keyCodeNE) {
        this.keyCodeNE = keyCodeNE;
    }

    /**
     * Set keyCodeNW
     * @param keyCodeNW new keyCodeSPACE to be set to
     */
    public void setKeyCodeNW(boolean keyCodeNW) {
        this.keyCodeNW = keyCodeNW;
    }

    /**
     * Set keyCodeDown
     * @param keyCodeDown new keyCodeSPACE to be set to
     */
    public void setKeyCodeDown(boolean keyCodeDown) {
        this.keyCodeDown = keyCodeDown;
    }

    /**
     * Get the weapon that the character is currently holding
     * @return the current weapon holden by the player
     */
    public MeleeWeapon getCurrentWeapon() {
        return currentWeapon;
    }
}

