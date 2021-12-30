package deco2800.spooky.worlds;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Chris;
import deco2800.spooky.entities.Damien;
import deco2800.spooky.entities.Jane;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JennaWorldTest {

    @Test
    public void getRemainingPlayerList() {
        JennaWorld jenna = new JennaWorld();
        List<Character> players = new ArrayList<>();
        Character creeper = new Damien(-3f, -3f, false);

        Character player1 = new Chris(0, 0, true);
        players.add(player1);
        players.add(creeper);
        Character jane = new Jane(5f, 5f, false);
        players.add(jane);
        //assertEquals(jenna.getRemainingPlayerList().toString().trim(), players.toString().trim());
    }

    @Test
    public void generateWorld() {
        JennaWorld jenna = new JennaWorld();
        jenna.generateWorld();
    }

}