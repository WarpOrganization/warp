package pl.warp.engine.core.scene.properties;

import org.joml.Vector3f;

public class TranslationProperty {

    private Vector3f translation;

    public TranslationProperty(Vector3f translation) {
        this.translation = translation;
    }


    public Vector3f getTranslation() {
        return translation;
    }

    public void translate(Vector3f translation) {
        this.translation.add(translation);
    }
}
