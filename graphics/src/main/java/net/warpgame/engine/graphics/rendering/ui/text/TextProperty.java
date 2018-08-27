package net.warpgame.engine.graphics.rendering.ui.text;

import net.warpgame.engine.core.property.Property;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * @author MarconZet
 * Created 27.08.2018
 */
public class TextProperty extends Property {
    private String text;
    private Vector3f color;
    private Integer fontSize;

    private String font;

    public TextProperty(String text, Vector3f color, Integer fontSize, String font) {
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
        this.font = font;
    }

    public TextProperty(String text, String font) {
        this(text, new Vector3f(0, 0, 0), 72, font);
    }

    public TextProperty(String font) {
        this("Bottom Text", font);
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

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
