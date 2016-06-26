package pl.warp.engine.core.scene.properties;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;

/**
 * Created by Hubertus on 2016-06-26.
 */
public class ScaleProperty extends Property {

    private Vector3f scale;

    public ScaleProperty(Vector3f scale){

        this.scale = scale;
    }


    public Vector3f getScale() {
        return scale;
    }

    public void scale(Vector3f value){
        this.scale.mul(value);
    }
}
