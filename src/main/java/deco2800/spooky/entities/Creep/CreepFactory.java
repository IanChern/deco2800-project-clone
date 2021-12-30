package deco2800.spooky.entities.Creep;

import deco2800.spooky.entities.Character;
import deco2800.spooky.entities.Peon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class CreepFactory {

    private int numCreeps;
    private List<Character> creeps= new ArrayList<>();
    private List<Mummy> mummies = new ArrayList<>();
    private List<Guard> guards= new ArrayList<>();

    // MethodHandles.lookup().lookupClass() provides a general way to select this class.
    // It is a generic approach to defining a logger for a class, rather than explicitly naming the class.
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CreepFactory(){
        this.numCreeps = 0;
    }

    /**
     * Creates a selected type of creep
     * @param creepType - A string to select which creep to create
     * @param col - Colum coord of where to place the creep
     * @param row - Row coord of where to place the creep
     */
    public Peon createCreep (String creepType, float col, float row) {
        if ("Mummy".equals(creepType)) {
            Mummy mummy = new Mummy(col, row, false);
            mummies.add(mummy);
            creeps.add(mummy);
            numCreeps++;
            logger.info("A mummy has been created in the creep factory.");
            return mummy;
        } else if ("Guard".equals(creepType)) {
            Guard guard = new Guard(col, row, false);
            guards.add(guard);
            creeps.add(guard);
            numCreeps++;
            logger.info("A guard has been created in the creep factory.");
            return guard;
        }
        logger.warn("The creep factory was unable to create the creep of type: {}.", creepType);
        return null;
    }

    public int getNumCreeps() {
        return numCreeps;
    }

    public void setNumCreeps(int numCreeps) {
        this.numCreeps = numCreeps;
    }

    public List<Character> getCreeps() {
        return creeps;
    }

    public List<Mummy> getMummies() {
        return mummies;
    }


    public List<Guard> getGuards() {
        return guards;
    }


}
