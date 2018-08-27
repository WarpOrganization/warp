package net.warpgame.engine.graphics.rendering.ui.image;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.texture.Texture2D;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class ImageProperty extends Property {
    Texture2D texture;

    public ImageProperty(Texture2D texture) {
        this.texture = texture;
    }

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texture) {
        this.texture = texture;
    }
}
