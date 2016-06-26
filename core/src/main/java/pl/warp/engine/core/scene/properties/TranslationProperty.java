package pl.warp.engine.core.scene.properties;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationProperty that = (TranslationProperty) o;
        return Objects.equals(translation, that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translation);
    }
}
