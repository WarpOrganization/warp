package net.warpgame.engine.graphics.memory.scene.material;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class MaterialProperty extends Property{
    Texture texture;

    public MaterialProperty(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void init() {
        texture.scheduleForLoad(this);
        MeshProperty meshProperty = getOwner().getPropertyOrNull(Property.getTypeId(MeshProperty.class));
        if(meshProperty != null){

        }
    }

    public Texture getTexture() {
        return texture;
    }
}
