package pl.warp.engine.graphics.material;

import pl.warp.engine.graphics.texture.Texture2D;

import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 20
 */
public class Material {

    private Texture2D mainTexture;
    private Texture2D specularTexture;
    private float brightness = 1.0f;
    private float shininess = 0.1f;

    public Material(Texture2D mainTexture) {
        this.mainTexture = mainTexture;
    }

    public Texture2D getMainTexture() {
        return mainTexture;
    }

    public void setMainTexture(Texture2D mainTexture) {
        Objects.requireNonNull(mainTexture);
        this.mainTexture = mainTexture;
    }

    public Texture2D getSpecularTexture() {
        return specularTexture;
    }

    public void setSpecularTexture(Texture2D specularTexture) {
        this.specularTexture = specularTexture;
    }

    public boolean hasSpecularTexture() {
        return specularTexture != null;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}

