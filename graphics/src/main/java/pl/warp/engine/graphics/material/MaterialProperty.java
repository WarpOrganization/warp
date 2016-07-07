package pl.warp.engine.graphics.material;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.material.Material;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 15
 */
public class MaterialProperty extends Property<Component> {

    public static final String MATERIAL_PROPERTY_NAME = "material";
    private Material material;

    public MaterialProperty(Component owner, Material material) {
        super(owner, MATERIAL_PROPERTY_NAME);
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
