package pl.warp.engine.core.scene.properties;

import com.sun.javafx.geom.Vec3f;

public class TranslationProperty {

    private Vec3f translation;

    public TranslationProperty(Vec3f translation){

        this.translation = translation;
    }


    public Vec3f getTranslation() {
        return translation;
    }
}
