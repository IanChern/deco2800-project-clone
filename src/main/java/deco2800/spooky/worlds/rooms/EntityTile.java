package deco2800.spooky.worlds.rooms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EntityTile {
    //The tile ID that this entity will be put
    protected int tileID;

    //The number of possible entity
    protected int entityNo;

    //The texture/type of this entity (after randomization)
    //TODO: REPLACE THIS WITH ENTITY OBJECT
    protected String entityTexture;


    //Boolean to determine if the entity can be created or not
    protected boolean created = true;

    public int getTileID() {
        return tileID;
    }

    /**
     * Create Entity Tile from line contains entity info
     *
     * @author: Hanh La
     *
     * @param line String line from file
     *
     */

    public EntityTile(String line){
        List<Double> probability = new ArrayList<Double>();

        List<String> pattern = new ArrayList<String>();
        String[] tileInfo = line.split(":", 3);
        if(tileInfo.length == 3){

            //Try parsing tile ID and number of entities
            try{
                this.tileID = Integer.parseInt(tileInfo[0]);
                this.entityNo = Integer.parseInt(tileInfo[1]);
            } catch (NumberFormatException e){
                this.created = false;
            }

            //Try parsing probability and entities name
            if (this.created){
                if (!tileInfo[2].contains(":")){
                    String[] entityInfoList = tileInfo[2].split("[\\(\\)]");
                    List<String>  entities = new ArrayList<String>();
                    for (int i = 0; i < entityInfoList.length; i++){
                        if(!entityInfoList[i].equals("") && entityInfoList[i] != null){
                            entities.add(entityInfoList[i]);
                        }
                    }
                    if (entities.size() == this.entityNo){
                        boolean toNext = false;
                        double entityProb = 0;
                        String textureName = "";
                        for (int i = 0; i < this.entityNo; i++){
                            String[] entityInfo = entities.get(i).split(",");
                            try{
                                entityProb = Double.parseDouble(entityInfo[0]);
                            } catch (NumberFormatException e){
                                toNext = true;
                            }

                            textureName = entityInfo[1];

                            if (!toNext){
                                probability.add(entityProb);
                                pattern.add(textureName);
                            }
                        }

                        int randEntityIndex = RandomWithProbability(probability);
                        if (randEntityIndex >= 0){
                            this.entityTexture = pattern.get(randEntityIndex);
                        } else {
                            this.created = false;
                            System.out.println("Fail to random texture");
                        }

                    } else {
                        this.created = false;
                    }
                } else {
                    this.created = false;
                }
            }
        } else {
            this.created = false;
        }
    }

    public boolean getCreated(){
        return this.created;
    }

    public String getEntityTexture(){
        return this.entityTexture;
    }

    //Method to random with ArrayList of probability (Guassian)
    public int RandomWithProbability(List<Double> probability){
        int[] randomNo = new int[100];
        double total = 0;
        for (int i = 0; i < probability.size(); i++){
            if (probability.get(i) > 1 || probability.get(i) < 0) {
                System.out.println("Unable to generate random because of prob. info");
                return -2;
            }
            total += probability.get(i);
        }
        if (total <= 1.00){
            Collections.sort(probability);
            int sum = 0;
            for (int i = 0; i < probability.size(); i++){
                int eleNo = (int) Math.round(probability.get(i)*100);
                if (sum + eleNo <= 100){
                    for (int j = sum; j < eleNo; j++){
                        randomNo[j] = i;
                    }
                    sum += eleNo;
                }
            }

            if (sum < 100){
                for (int i = sum; i < 100; i++){
                    randomNo[i] = -1;
                }
            }

            Random rand = new Random();
            int randEntityIndex = rand.nextInt(100);
            return (randomNo[randEntityIndex]);
        }

        return -2;
    }

}
