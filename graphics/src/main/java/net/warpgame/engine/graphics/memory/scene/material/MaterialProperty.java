package net.warpgame.engine.graphics.memory.scene.material;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class MaterialProperty extends Property{
    private Texture texture;

    public MaterialProperty(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void init() {
        texture.scheduleForLoad(this);
    }

    public Texture getTexture() {
        return texture;
    }
}
