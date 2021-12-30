package deco2800.spooky.entities;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.WorldUtil;
import deco2800.spooky.worlds.Tile;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class StaticEntity extends AbstractEntity {
    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "staticEntityID";

    //pos, texture
    @Expose
    public Map<HexVector, String> children;


    public StaticEntity() {
        super();
    }

    public StaticEntity(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile.getCol(), tile.getRow(), renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug(tile.getCoordinates() + " Is Invalid:");
            return;
        }
        tile.setParent(this);
        tile.setObstructed(obstructed);
    }

    public StaticEntity(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        Tile center = GameManager.get().getWorld().getTile(this.getPosition());

        if (center == null) {
            log.debug("Center is null");
            return;
        }

        if (!WorldUtil.validColRow(center.getCoordinates())) {
            log.debug(center.getCoordinates() + " Is Invalid:");
            return;
        }

        children = new HashMap<>();

        for (Entry<HexVector, String> tex : texture.entrySet()) {
            Tile tile = textureToTile(tex.getKey(), this.getPosition());
            if (tile != null) {
                children.put(tile.getCoordinates(), tex.getValue());
            }
        }

        for (HexVector childpos : children.keySet()) {
            Tile child = GameManager.get().getWorld().getTile(childpos);

            child.setObstructed(true);
        }

    }

    public void setup() {
        if (children != null) {
            for (HexVector childposn : children.keySet()) {
                Tile child = GameManager.get().getWorld().getTile(childposn);
                if (child != null) {
                    child.setParent(this);
                }
            }
        }
    }


    @Override
    public void onTick(long i) {
        // Do the AI for the building in here
    }

    private Tile textureToTile(HexVector offset, HexVector center) {
        if (!WorldUtil.validColRow(offset)) {
            log.debug(offset + " Is Invaid:");
            return null;
        }
        HexVector targetTile = center.add(offset);
        return GameManager.get().getWorld().getTile(targetTile);
    }

    public Set<HexVector> getChildrenPositions() {
        return children.keySet();
    }

    public Texture getTexture(HexVector childpos) {
        String texture = children.get(childpos);
        return GameManager.get().getManager(TextureManager.class).getTexture(texture);
    }

    public void setChildren(Map<HexVector, String> children) {
        this.children = children;
    }

    Pair<Character,Float> closestCharacter(){
        List<Character> characters = GameManager.get().getWorld().getRemainingPlayerList();
        float minDeltaDistance = Integer.MAX_VALUE;
        Character closestCharacter = null;
        for (Character character: characters) {
            float deltaCol = Math.abs(character.getCol() - getCol());
            float deltaRow = Math.abs(character.getRow() - getRow());
            float deltaDistance = deltaCol + deltaRow;
            if (deltaDistance < minDeltaDistance){
                minDeltaDistance = deltaDistance;
                closestCharacter = character;
            }
        }
        return new Pair<>(closestCharacter, minDeltaDistance);
    }
}
