package deco2800.spooky.managers;

import deco2800.spooky.entities.Character;
import deco2800.spooky.worlds.AbstractWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Random;

/**
 * This class manages the roles of the players.
 *
 * Assigns ONE traitor and the rest will be survivors.
 *
 * NOTE:
 * The players roles will be shown on the game using PopUpManager.
 * (For development, all the roles are shown on the game)
 */
public class CharacterRoleManager {

    // Message logger for the class.
    private static final Logger logger = LoggerFactory.getLogger(CharacterRoleManager.class);

    // The game world currently being played on.
    private AbstractWorld gameWorld;

    // Public constructor
    public CharacterRoleManager(AbstractWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    /**
     * Makes one of the players to be a traitor.
     * When there is 0 or 1 player, the game is considered to be
     * lacking in player number and thus 'not ready to begin'.
     * Hence, no roles will be assigned.
     *
     * Preconditions:
     * - There should be more than or equals to 2 players on the world.
     */
    public void assignRole(){
        List<Character> currentPlayers = gameWorld.getRemainingPlayerList();

        if(currentPlayers != null){
            int numPlayers = currentPlayers.size();

            if(numPlayers == 0){
                logger.warn("There are 0 players in this world.");
            } else if(numPlayers == 1){
                logger.warn("There is only 1 player in this world.");
            } else {
                // randomly assigns ONE player to be a traitor
                Random randomNumGen = new Random();
                int randomNum = randomNumGen.nextInt(numPlayers);
                gameWorld.getRemainingPlayerList().get(randomNum).setTraitor(true);
            }

        } else {
            logger.warn("There are 0 players in this world.");
        }
    }


}
