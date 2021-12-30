package deco2800.spooky.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.HashMap;

/**
 * Font manager is used to generate font BitMaps from font ttf files
 * and is stored and managed in font manager.
 */
public class FontManager implements AbstractManager {
    private static final Logger logger = LoggerFactory.getLogger(FontManager.class);

    // Map from fontName to font generators
    private Map<String, FreeTypeFontGenerator> fontGeneratorMap = new HashMap<>();

    public FontManager() {
        try {
            logger.info("Loading fonts");
            //Add any new font files to resources/fonts and reference below
            fontGeneratorMap.put("OpenSans", new FreeTypeFontGenerator(
                    Gdx.files.internal("resources/fonts/OpenSans-Regular.ttf")));

        } catch (Exception e) {
            logger.error("Error loading fonts", e);
        }
    }

    /**
     * Generate a BitmapFont with font customisation
     * @param fontName Name of the font generator
     * @param size Font Size
     * @param color Font Colour
     * @param borderWidth Font Border Width
     * @param borderColor Font Border Colour
     * @return generated BitmapFont
     */
    public BitmapFont getFont(String fontName, int size, Color color, int borderWidth, Color borderColor) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.spaceX = size / 16;
        parameter.color = color;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;
        return getFont(fontName, parameter);
    }

    /**
     * Generate a BitmapFont from the library
     * @param fontName Name of the font generator
     * @param parameter
     * @return generated BitmapFont
     */
    private BitmapFont getFont(String fontName, FreeTypeFontParameter parameter) {
        if (fontGeneratorMap.containsKey(fontName)) {
            return fontGeneratorMap.get(fontName).generateFont(parameter);
        } else {
            return new BitmapFont();
        }
    }
}
