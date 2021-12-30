package deco2800.spooky.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * This Interface indicates that some entities may have variable textures (Animations)
 */
public interface HasVariableTextures {

    /** get the current texture (frame) of the entity.
     *
     * @return the current texture of the entity.
     */
    TextureAtlas.AtlasRegion getCurrentTexture();
}
