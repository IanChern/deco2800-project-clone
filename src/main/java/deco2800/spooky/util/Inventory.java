package deco2800.spooky.util;

import deco2800.spooky.entities.AbstractEntity;

import java.util.ArrayList;

public class Inventory {


    ArrayList<AbstractEntity> main;
    ArrayList<AbstractEntity> equipped;
    int mainCapacity;
    int mainSize;
    int equippedCapacity;
    int equippedSize;

    /**
     * getAll();
     * delete(x);
     * move()
     * get_item(x)
     * deletAll()
     * Add_item()
     *

    /**
     * Inventory Class used for main/secondary player inventories
     * Utilises two 2D arrays (from util)
     *
     * Size
     *
     */
    public Inventory(int mainCapacity, int equippedCapacity) {

        this.equippedCapacity = equippedCapacity;
        this.mainCapacity = mainCapacity;

        this.mainSize = 0;
        this.equippedSize = 0;

        this.main = new ArrayList<>();
        this.equipped = new ArrayList<>();

    }



}
