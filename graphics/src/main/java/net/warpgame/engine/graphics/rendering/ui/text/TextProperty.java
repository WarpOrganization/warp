package net.warpgame.engine.graphics.rendering.ui.text;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.resource.font.Font;
import net.warpgame.engine.graphics.resource.font.FontManager;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * @author MarconZet
 * Created 27.08.2018
 */
public class TextProperty extends Property {
    private String text;
    private Vector3f color;
    private int fontSize;

    private String fontName;
    private Font font;
    private FontManager fontManager;

    public TextProperty(String text, Vector3fc color, Integer fontSize, String fontName) {
        this.text = text;
        this.color = new Vector3f(color);
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

    public TextProperty(String text, String fontName) {
        this(text, new Vector3f(0, 0, 0), 72, fontName);
    }

    public TextProperty(String fontName) {
        this("Bottom Text", fontName);
    }

    @Override
    public void init() {
        super.init();
        fontManager = getOwner().getContext().getLoadedContext().findOne(FontManager.class).get();
        updateFont();
    }

    private void updateFont(){
        font = fontManager.findFont(fontName);
        if(font == null)
            throw new RuntimeException(String.format("Font of name %s hasn't been loaded", fontName));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Vector3fc getColor() {
        return color;
    }

    public void setColor(Vector3fc color) {
        this.color.set(color);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        if(getOwner() != null)
            updateFont();
    }
}
