package deco2800.spooky.renderers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import deco2800.spooky.entities.*;
import deco2800.spooky.managers.InputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.TextureManager;
import deco2800.spooky.tasks.AbstractTask;
import deco2800.spooky.tasks.MovementTask;
import deco2800.spooky.util.HexVector;
import deco2800.spooky.util.Vector2;
import deco2800.spooky.util.WorldUtil;
import deco2800.spooky.worlds.Tile;

import static deco2800.spooky.util.WorldUtil.TILE_WIDTH;

/**
 * A ~simple~ complex hex renderer for DECO2800 games
 * 
 * @Author Tim Hadwen & Lachlan Healey
 */
public class Renderer3D implements Renderer {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(Renderer3D.class);

	BitmapFont font;
	
	//mouse cursor
	private static final String TEXTURE_SELECTION = "selection";
	private static final String TEXTURE_PATH = "path";

	private int tilesSkipped = 0;

	private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

	/**
	 * Renders onto a batch, given a renderables with entities It is expected
	 * that AbstractWorld contains some entities and a Map to read tiles from
	 * 
	 * @param batch
	 *            Batch to render onto
	 */
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		if (font == null) {
			font = new BitmapFont();
			font.getData().setScale(1f);
		}

		// Render tiles onto the map
		List<Tile> tileMap = GameManager.get().getWorld().getTileMap();
		List<Tile> tilesToBeSkipped = new ArrayList<>();
				
		batch.begin();
		// Render elements section by section
		//	tiles will render the static entity attaced to each tile after the tile is rendered

		tilesSkipped =0;
		for (Tile t: tileMap) {
			// Render each tile
			renderTile(batch, camera, tileMap, tilesToBeSkipped, t);

			// Render each undiscovered area
		}


		renderAbstractEntities(batch, camera);

		renderMouse(batch);

		debugRender(batch, camera);

		batch.end();
	}
	
	
	/**
	 * Render a single tile.
	 * @param batch the sprite batch.
	 * @param camera the camera.
	 * @param tileMap the tile map.
	 * @param tilesToBeSkipped a list of tiles to skip.
	 * @param tile the tile to render.
	 */
	private void renderTile(SpriteBatch batch, OrthographicCamera camera, List<Tile> tileMap, List<Tile> tilesToBeSkipped, Tile tile) {

        if (tilesToBeSkipped.contains(tile)) {
            return;
        }
        float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());

        if (WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
            tilesSkipped++;
            GameManager.get().setTilesRendered(tileMap.size() - tilesSkipped);
            GameManager.get().setTilesCount(tileMap.size());
            return;
        }

        Texture tex = tile.getTexture();
			batch.draw(tex, tileWorldCord[0], tileWorldCord[1], tex.getWidth() * WorldUtil.SCALE_X,
					tex.getHeight() * WorldUtil.SCALE_Y);
		GameManager.get().setTilesRendered(tileMap.size() - tilesSkipped);
		GameManager.get().setTilesCount(tileMap.size());
		

	}


	

	/**
	 * Render the tile under the mouse.
	 * @param batch the sprite batch.
	 */
    private void renderMouse(SpriteBatch batch) {
        Vector2 mousePosition = GameManager.getManagerFromInstance(InputManager.class).getMousePosition();

        Texture tex = textureManager.getTexture(TEXTURE_SELECTION);

        // get mouse position
        float[] worldCoord = WorldUtil.screenToWorldCoordinates(mousePosition.getX(), mousePosition.getY());

        // snap to the tile under the mouse by converting mouse position to colrow then back to mouse coordinates
        float[] colrow = WorldUtil.worldCoordinatesToColRow(worldCoord[0], worldCoord[1]);

        float[] snapWorldCoord = WorldUtil.colRowToWorldCords(colrow[0], colrow[1] + 1);

        //Needs to getTile with a HexVector for networking to work atm
        Tile tile = GameManager.get().getWorld().getTile(new HexVector(colrow[0], colrow[1]));

        if (tile != null) {
            batch.draw(tex, (int) snapWorldCoord[0], (int) snapWorldCoord[1]  - (tex.getHeight() * WorldUtil.SCALE_Y), 
                    tex.getWidth() * WorldUtil.SCALE_X,
                    tex.getHeight() * WorldUtil.SCALE_Y);
        }

	}	
	
	
	
    /**
	 * Render all the entities on in view, including movement tiles, and excluding undiscovered area.
	 * @param batch the sprite batch.
	 * @param camera the camera.
	 */
	private void renderAbstractEntities(SpriteBatch batch, OrthographicCamera camera) {
		List<AbstractEntity> entities = GameManager.get().getWorld().getSortedEntities();
		int entitiesSkipped = 0;
		logger.debug("NUMBER OF ENTITIES IN ENTITY RENDER LIST: {}", entities.size());
		for (AbstractEntity entity : entities) {
			Texture tex = textureManager.getTexture(entity.getTexture());
			float[] entityWorldCoord = WorldUtil.colRowToWorldCords(entity.getCol(), entity.getRow());
			// If it's offscreen
			if (WorldUtil.areCoordinatesOffScreen(entityWorldCoord[0], entityWorldCoord[1], camera)) {
				entitiesSkipped++;
				continue;
			}
			/* Draw Peon */
			// Place movement tiles
			if (entity instanceof Peon) {
				if (GameManager.get().getShowPath()) {
					renderPeonMovementTiles(batch, camera, entity);
				}
				renderAbstractEntity(batch, entity, entityWorldCoord, tex);
			 }

			if (entity instanceof StaticEntity) {	 
				StaticEntity staticEntity = ((StaticEntity) entity);
				Set<HexVector> childrenPosns = staticEntity.getChildrenPositions();
				for(HexVector childpos: childrenPosns) {
					Texture childTex = staticEntity.getTexture(childpos);
					
					float[] childWorldCoord = WorldUtil.colRowToWorldCords(childpos.getCol(), childpos.getRow());
										
					// time for some funky math: we want to render the entity at the centre of the tile. 
					// this way centres of textures bigger than tile textures render exactly on the top of the tile centre
					// think of a massive tree with the tree trunk at the centre of the tile 
					// and it's branches and leaves over surrounding tiles 
					
					// We get the tile height and width :
					int w = GameManager.get().getWorld().getTile(childpos).getTexture().getWidth();
					int h = GameManager.get().getWorld().getTile(childpos).getTexture().getHeight(); 
					
					int drawX = (int) (childWorldCoord[0] + (w - childTex.getWidth()) /2 * WorldUtil.SCALE_X);
					int drawY = (int) (childWorldCoord[1] + (h - childTex.getHeight())/2 * WorldUtil.SCALE_Y);

					if (entity instanceof HasVariableTextures) {
						drawX = (int) (childWorldCoord[0]);
						drawY = (int) (childWorldCoord[1]);

						if (((HasVariableTextures) entity).getCurrentTexture() != null) {
							float texH = ((HasVariableTextures) entity).getCurrentTexture().getRegionHeight() * entity.getRowRenderLength();
							float texW = ((HasVariableTextures) entity).getCurrentTexture().getRegionWidth() * entity.getColRenderLength();
							batch.draw(
									((HasVariableTextures) entity).getCurrentTexture(), drawX, drawY,
									TILE_WIDTH,
									TILE_WIDTH * (texH/texW));
						} else{
							batch.draw(
									childTex, drawX, drawY,
									TILE_WIDTH,
									TILE_WIDTH * (childTex.getHeight() * entity.getRowRenderLength()/childTex.getWidth()*entity.getColRenderLength()));
						}
					}

					if (!(entity instanceof HasVariableTextures)) {
						batch.draw(
								childTex, drawX, drawY,
								childTex.getWidth() * WorldUtil.SCALE_X,
								childTex.getHeight() * WorldUtil.SCALE_Y);
					}
				}
			}
		}

		GameManager.get().setEntitiesRendered(entities.size() - entitiesSkipped);
		GameManager.get().setEntitiesCount(entities.size());
	}
	
	
	private void renderAbstractEntity(SpriteBatch batch, AbstractEntity entity, float[] entityWorldCord, Texture tex) {
        float x = entityWorldCord[0];
		float y = entityWorldCord[1];

        float width = tex.getWidth() * entity.getColRenderLength() * WorldUtil.SCALE_X;
        float height = tex.getHeight() * entity.getRowRenderLength() * WorldUtil.SCALE_Y;

		if (entity instanceof HasVariableTextures) {
			TextureAtlas.AtlasRegion currentTex = ((HasVariableTextures) entity).getCurrentTexture();
			if (currentTex != null) {
				batch.draw(
						((HasVariableTextures) entity).getCurrentTexture(), x, y,
						currentTex.getRegionWidth() * WorldUtil.SCALE_X,
						currentTex.getRegionHeight() * WorldUtil.SCALE_Y);
				return;
			}
		}

        batch.draw(tex, x, y, width, height);
    }
	
	private void renderPeonMovementTiles(SpriteBatch batch, OrthographicCamera camera, AbstractEntity entity) {
		Peon actor = (Peon) entity;
		AbstractTask task = actor.getTask();
		if (task instanceof MovementTask) {
			if (((MovementTask)task).getPath() == null) { //related to issue #8
				return;
			}
			List<Tile> path = ((MovementTask)task).getPath();
			for (Tile tile : path) {
				// Place transparent tiles for the path, but place a non-transparent tile for the destination
				Texture tex = path.get(path.size() - 1) == tile ?
						textureManager.getTexture(TEXTURE_SELECTION) : textureManager.getTexture(TEXTURE_PATH);
				float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());
				if (WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
					tilesSkipped++;
					continue;
				}
				batch.draw(tex, tileWorldCord[0],
						tileWorldCord[1]// + ((tile.getElevation() + 1) * elevationZeroThiccness * WorldUtil.SCALE_Y)
						, tex.getWidth() * WorldUtil.SCALE_X,
						tex.getHeight() * WorldUtil.SCALE_Y);

			}
		}
	}
	
	private void debugRender(SpriteBatch batch, OrthographicCamera camera) {

		if (GameManager.get().getShowCoords()) {
			List<Tile> tileMap = GameManager.get().getWorld().getTileMap();
			for (Tile tile : tileMap) {
				float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());

				if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
					font.draw(batch, 
							tile.toString(),
							//String.format("%.0f, %.0f, %d",tileWorldCord[0], tileWorldCord[1], tileMap.indexOf(tile)),
							tileWorldCord[0] + TILE_WIDTH / 4.5f,
							tileWorldCord[1]);
				}

			}
		}

		if (GameManager.get().getShowCoordsEntity()) {
			List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
			for (AbstractEntity entity : entities) {
				float[] tileWorldCord = WorldUtil.colRowToWorldCords(entity.getCol(), entity.getRow());

				if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
					font.draw(batch, String.format("%.0f, %.0f", entity.getCol(), entity.getRow()),
							tileWorldCord[0], tileWorldCord[1]);
				}
			}
		}
	}
}
