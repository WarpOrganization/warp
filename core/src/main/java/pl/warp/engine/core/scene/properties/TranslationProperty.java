package pl.warp.engine.core.scene.properties;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;

public class TranslationProperty implements Property {

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

    @Override
    public String getName() {
        return "TranslationProperty";
    }
}
