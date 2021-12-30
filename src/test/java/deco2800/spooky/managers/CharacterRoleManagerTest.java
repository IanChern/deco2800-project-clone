package deco2800.spooky.managers;

import deco2800.spooky.worlds.TestWorld;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterRoleManagerTest {

    private GameManager gm;

    @Test
    public void tempTest() {
        CharacterRoleManager crm = new CharacterRoleManager(new TestWorld());
    }

    // Set up JennaWorld for testing
//    @Before
//    public void setUp() {
//        gm = GameManager.get();
//        gm.setWorld(new JennaWorld());
//    }

    // Test for the Character role randomizing manager
    // In the nature that the roles are assigned at random,
    // randomization is difficult to be tested using code.
    // Thus, the randomization will be tested using popup messages
    // showing all the player's roles and manually repeat the games to check.
    // For this test, it will check the number of traitors and survivors.
    /*@Test(expected = NullPointerException.class)
    public void characterRoleTest() {
        // Since the world has 3 players,
        // there should be 1 traitor and 2 survivors.

        gm = GameManager.get();
        gm.setWorld(new JennaWorld());

        System.out.println(""+gm.CharactersRemaining());

        int numTraitor = 0;
        int numSurvivor = 0;

        for (int i = 0; i < gm.CharactersRemaining(); i++){
            if(gm.getWorld().getRemainingPlayerList().get(i).isTraitor()){
                numTraitor++;
            } else if(!gm.getWorld().getRemainingPlayerList().get(i).isTraitor()){
                numSurvivor++;
            }
        }

        //assertEquals(1, numTraitor);
        //assertEquals(2, numSurvivor);
    }*/
}
