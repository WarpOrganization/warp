package pl.warp.engine.core.scene.properties;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * Created by Hubertus on 2016-06-26.
 */
public class ScaleProperty extends Property<Component> {

    public static final String SCALE_PROPERTY_NAME = "scale";

    private Vector3f scale;

    public ScaleProperty(Component owner, Vector3f scale){
        super(owner);
        this.scale = scale;
    }


    public Vector3f getScale() {
        return scale;
    }

    public void scale(Vector3f value){
        this.scale.mul(value);
    }
}
