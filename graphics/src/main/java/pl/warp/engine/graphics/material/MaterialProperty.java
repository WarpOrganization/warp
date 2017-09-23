package pl.warp.engine.graphics.material;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class MaterialProperty extends Property{
    public static final String NAME = "material";

    private Material material;

    public MaterialProperty(Material material) {
        super(NAME);
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
