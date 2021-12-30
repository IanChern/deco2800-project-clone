package deco2800.spooky.worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import deco2800.spooky.managers.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.spooky.GameLauncher;
import deco2800.spooky.Inventory.InventoryBox;
import deco2800.spooky.entities.AbstractEntity;
import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.entities.Damien;
import deco2800.spooky.entities.Jane;
import deco2800.spooky.entities.Peon;
import deco2800.spooky.entities.Pill;
import deco2800.spooky.entities.Pillar;
import deco2800.spooky.entities.Pot;
import deco2800.spooky.entities.Rock;
import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.entities.TrapType;
import deco2800.spooky.entities.Creep.CreepFactory;
import deco2800.spooky.entities.Creep.Guard;
import deco2800.spooky.entities.Creep.Mummy;
import deco2800.spooky.entities.Items.Coin;
import deco2800.spooky.entities.Items.ItemFactory;
import deco2800.spooky.entities.Items.Rupee;
import deco2800.spooky.managers.CharacterRoleManager;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.util.Cube;

public class JennaWorld extends AbstractWorld {

    boolean notGenerated = true;

    private static int mapRadius = 25; // Used to determine size of map

    private static List<Character> playerList = new ArrayList<>();
    private List<Character> remainingPlayerList;
    private static CreepFactory creepFactory;
    private static ItemFactory itemFactory;

    private long moveCoolDownTime = 1000;
    private long movedTime;

    private static final float BULLET_BOX_LENGTH = 0.1f;

    public JennaWorld() {
        super();
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(JennaWorld.class.getPackage().getName());

    /**
     * Add Rocks to the Map at col and row location
     *
     * @param col column of the rock
     * @param row row of the rock
     */
    private void addRock(float col, float row) {
        Tile t = GameManager.get().getWorld().getTile(col, row);
        Rock rock = new Rock(t, true);
        entities.add(rock);
    }

    /**
     * Add Pillars to the Map at col and row location
     *
     * @param col the column value of the pillar
     * @param row the row value of the pillar
     */

    public void addPot(float col, float row) {
        Tile t = GameManager.get().getWorld().getTile(col, row);
        Pot p = new Pot(t, 1);
        entities.add(p);
    }

    private void addTrap(float col, float row, TrapType trapType) {
        Tile t = GameManager.get().getWorld().getTile(col, row);
        RoomTrap trap = this.trapManager.setTrap(t, trapType);
        entities.add(trap);
    }

    private void buildMaze() {
        // COLUMN MUST BE AN ODD INTEGER, initial bounds are +7 -9
        // Builds outer walls, not needed if initial map built correctly

        // Sides
        // Time to make the inner "walls"
        addRock(-3, 3.5f);
        addRock(-3, 2.5f);
        addRock(-3, 1.5f);
        addRock(-3, 0.5f);
        addRock(-4, 0f);
        addRock(-5, 0.5f);
        addRock(-6, 0f);

        addRock(6, 0);
        addRock(5, 0.5f);
        addRock(4, 0);
        addRock(3, 0.5f);
        addRock(2, 0);
        addRock(2, -2);
        addRock(1, -0.5f);
        addRock(1, -1.5f);

        addPillar(-2, 2);
        addPillar(-2, 0);

        addPot(-2, -1);


        addTrap(-4, -1, TrapType.PITFALL);
        addTrap(-2, 0, TrapType.QUICKSAND);

    }

    public static List<Character> getPlayerList() {
        return playerList;
    }


    public void removeCharacter(Character character) {
        remainingPlayerList.remove(character);
        entities.remove(character);
    }

    private void generateRupees() {
        String rupee = "Rupee";
        Rupee rupee3 = (Rupee) itemFactory.createItem(rupee, 1, 1);
        addEntity(rupee3);

        Rupee rupee1 = (Rupee) itemFactory.createItem(rupee, 2, 1);
        addEntity(rupee1);

        Rupee rupee2 = (Rupee) itemFactory.createItem(rupee, 3, 1);
        addEntity(rupee2);
    }

    @Override
    protected void generateWorld() {


        playerList = new ArrayList<>();
        creepFactory = new CreepFactory();
        itemFactory = new ItemFactory();
        Random random = new Random();


        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= mapRadius) {

                    int col = q;

                    float oddCol = (col % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);

                    String type = "grass_" + elevation;

                    tiles.add(new Tile(type, q, r + oddCol));
                }
            }
        }

        addPillar(-2, 2);
        addPillar(-2, 0);
        addPillar(-2, -2);
        addPillar(-2, -4);

        generateRupees();

        Coin coin1 = new Coin(2, 2);
        addEntity(coin1);

        Character creeper = new Damien(-3f, -3f, false);
        playerList.add(creeper);
        addEntity(creeper);

        //Add the selected character to the JennaWorld
        Character player1 = new Chris(0, 0, true);
        try {
            player1 = new GameLauncher().game.getMainMenuScreen().getCharScreen().getPlayer();
        } catch (Exception e) {
            LOGGER.warn(String.valueOf(e));
        }
        remainingPlayerList = new ArrayList<>();
        player1.setHealth(100);
        remainingPlayerList.add(player1);

        player1.setMainCharacter(true);
        player1.setSpeed(0.1f);
        playerList.add(player1);
        addEntity(player1);

        Character jane = new Jane(5f, 5f, false);
        playerList.add(jane);
        addEntity(jane);

        Character mummy = (Mummy) creepFactory.createCreep("Mummy", 3f, 3f);
        mummy.setSpeed(0.005f);
        addEntity(mummy);

        for (Pillar pillars : getPillarList()) {
            float pillarx = pillars.getCol();
            float pillary = pillars.getRow();
            Character guard = (Guard) creepFactory.createCreep("Guard", pillarx, pillary - 1f);
            guard.setSpeed(0.01f);
            addEntity(guard);
        }

        initializeRemainingCharacter(playerList);
        new CharacterRoleManager(this).assignRole();


    }

    
    /**
     * Apply the damage inflicted to the player depending on the overLapResult generated
     *
     * @param item          entity created within the game
     * @param overLapResult of two different peon clashed
     * @param player        to be updated their health if the item causing the player to change
     */
    public void damageInflict(AbstractEntity item, int overLapResult, Character player) {
        LOGGER.info("The remaining health is " + player.getHealth());
        deleteEntity(item.getEntityID());
        removeEntity(item);
    }

    private int randomDamage(int damage) {
        int damageTaken;
        switch (damage) {
            case 0: { // damage 1
                damageTaken = 1;
                break;
            }
            case 1: { // damage 2
                damageTaken = 2;
                break;
            }
            case 2: {// damage 3
                damageTaken = 3;
                break;
            }
            case 3: {// damage 5
                damageTaken = 5;
                break;
            }
            case 4: {// damage 10
                damageTaken = 10;
                break;
            }
            default:
                damageTaken = 0;
        }
        return damageTaken;
    }

    private void updateCreep() {
        for (Character creep : creepFactory.getCreeps()) {
            if (creep.getHealth() == 0 && !creep.isKilled()) {
                Coin coin = (Coin) itemFactory.createItem("Coin", creep.getCol(), creep.getRow());
                removeEntity(creep);
                addEntity(coin);
                creep.setKilled(true);
            }
        }
    }

    private void updateEntities(Character player) {
        for (AbstractEntity item : entities) {
            if (item instanceof Character) {
                player.overlaps((Peon) item, BULLET_BOX_LENGTH * 72,
                        1.5f, 1.5f);

            } else if (item instanceof Peon) {
                int overLapResult = player.overlaps((Peon) item,
                        BULLET_BOX_LENGTH * 72, 1.5f, 1.5f);
                if (overLapResult >= 0 && item instanceof InventoryBox) {

                    player.stopWalk(player.getDirection());
                }
            }
        }
    }

    private void playerMummyOverlaps(Character player, Peon mummy, long currentTime, Random r) {
        int overLapResult = player.overlaps(mummy,
                BULLET_BOX_LENGTH * 72, 1.5f, 0.2f);
        //player picks up an ammo packet and the ammo refills
        if (overLapResult >= 1 && (mummy instanceof Mummy && (currentTime - movedTime) >= moveCoolDownTime)) {
            int damage = r.nextInt(10);
            int damageTaken = randomDamage(damage);
            player.updateHealth(-damageTaken);
            LOGGER.info(damageTaken + "damage taken");
            movedTime = currentTime;
        }
    }

    private void playerGuardOverlaps(Character player, Peon guard, long currentTime, Random r) {
        int overLapResult = player.overlaps(guard, BULLET_BOX_LENGTH * 72, 1.5f, 0.2f);
        if (overLapResult >= 1 && ((guard instanceof Guard) && (currentTime - movedTime) >= moveCoolDownTime)) {
            // player picks up an ammo packet and the ammo refills
            int damage = r.nextInt(10);
            int damageTaken = randomDamage(damage);
            player.updateHealth(-damageTaken);
            LOGGER.info(damageTaken + "damage taken");
            movedTime = currentTime;
        }
    }
    
    public static CreepFactory getCreepFactory() {
        return (creepFactory);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        long currentTime = System.currentTimeMillis();
        Random r = new Random();

        updateCreep();

        GameManager.get().getManager(SoundManager.class).playBackground();

        for (Character player : playerList) {
            for (Character mummy : creepFactory.getMummies()) {
                if (mummy instanceof Peon) {
                    playerMummyOverlaps(player, mummy, currentTime, r);
                }
            }

            for (Character guard : creepFactory.getGuards()) {
                if (guard instanceof Peon) {
                    playerGuardOverlaps(player, guard, currentTime, r);
                }
            }
            updateEntities(player);
        }

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            buildMaze();
            notGenerated = false;
        }
    }
}

