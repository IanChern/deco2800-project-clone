package deco2800.spooky.entities.Items;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.entities.HasVariableTextures;

import java.util.Random;

public class Rupee extends Lootable implements HasVariableTextures{

    //sets the textures
    private TextureAtlas.AtlasRegion currentTexture;
    private String[] colours={"blue", "yellow", "green", "purple"};
    private String colour;

    /**
     *Chooses a random rupee and sets the location and description
     * @param col - Collum number
     * @param row - row number
     */
    public Rupee(float col, float row){
        super(col,row);

        //Get a random rupee texture
        Random r=new Random();
        int index =r.nextInt(colours.length);

        this.setValue(1);
        this.setItemDesc("Rupee");
        this.setType("RUPEE");
        this.colour = colours[index];
        this.setTexture(colour + "-rupee0");

        this.setObjectName("rupee");

    }

    @Override
    public TextureAtlas.AtlasRegion getCurrentTexture() {
        return currentTexture;
    }
}
