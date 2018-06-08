package net.warpgame.engine.graphics.material;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class MaterialProperty extends Property{

    private Material material;

    public MaterialProperty(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

}
