package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewTextureManager implements AbstractManager {

    private static final Logger logger = LoggerFactory.getLogger(NewTextureManager.class);
    /**
     * The width of the tile to use then positioning the tile.
     */
    public static final int TILE_WIDTH = 320;

    /**
     * The height of the tile to use when positioning the tile.
     */
    public static final int TILE_HEIGHT = 278;

    private Map<String, String> loadTexture = new HashMap<>();

    private Map<String, Texture> newTextureMap = new HashMap<>();

    public NewTextureManager() {
        checkReadFile();

        for (Map.Entry<String, String> entry : loadTexture.entrySet()) {
            try {
                String key = entry.getKey();
                newTextureMap.put(key, new Texture(entry.getValue()));
            } catch (Exception e) {
                logger.warn("Error thrown {}", e);
            }
        }
    }

    public void checkReadFile(){
        File directory = new File("resources/TestTextureManager");
        List<File> fileList = new ArrayList<>();
        retrieveFile(fileList, directory);
        loadTexture = new HashMap<>();
        logger.info("Num of files: {}", fileList.size());
    }

    public void retrieveFile(List<File> fileList, File loadingFile){
        File[] folder = loadingFile.listFiles();
        for (File file: folder){
            if (file.isDirectory()) {
                retrieveFile(fileList, file);
            } else {
                logger.info("A file {}", file.getAbsolutePath());
                if (loadTexture.get(file.getName().split("\\.")[0]) == null) {
                    String name= file.getName();
                    String[] strip = name.split("\\.");
                    loadTexture.put(strip[0], file.getPath());
                }
                fileList.add(file);
            }
        }
    }


    /**
     * Gets a texture object for a given string id
     *
     * @param id Texture identifier
     * @return Texture for given id
     */
    public Texture getTexture(String id) {
        if (newTextureMap.containsKey(id)) {
            return newTextureMap.get(id);
        } else {
            logger.info("Texture map does not contain P{}, returning default texture.", id);
            return newTextureMap.get("spacman_ded");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     * @return If texture is available or not.
     */
    public boolean hasTexture(String id) {
        return newTextureMap.containsKey(id);

    }

    /**
     * Saves a texture with a given id
     *
     * @param id       Texture id
     * @param filename Filename within the assets folder
     */
    public void saveTexture(String id, String filename) {
        if (!newTextureMap.containsKey(id)) {
            newTextureMap.put(id, new Texture(filename));
        }
    }

    public Map<String, Texture> getTextureMap() {
        return newTextureMap;
    }


    public Map<String,String> getLoadTexture() {
        return loadTexture;
    }


}
