package deco2800.spooky.entities.Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.entities.HasVariableTextures;
import deco2800.spooky.managers.AnimationManager;
import deco2800.spooky.managers.GameManager;

public class Coin extends Lootable implements HasVariableTextures {

    private TextureAtlas.AtlasRegion currentTexture;
    private float elapsedTime;

    /**
     * Sets the texture and location
     * @param col - collum number
     * @param row - row number
     */
    public Coin(float col, float row) {
        super(col, row);
        this.setTexture("coin");
        setHeight(1);
        this.setType("COIN");

        this.setObjectName("coin");
    }

    @Override
    public TextureAtlas.AtlasRegion getCurrentTexture() {
        return currentTexture;
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        elapsedTime += Gdx.graphics.getDeltaTime();
        this.currentTexture = GameManager.getManagerFromInstance(AnimationManager.class).getAnimation("coin").getKeyFrame(elapsedTime,true);
    }
}
