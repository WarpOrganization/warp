package pl.warp.engine.graphics.postprocessing;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 14
 */
public class WeightedTexture2D {
    private Texture2D texture;
    private float weight;
    private float scale;

    public WeightedTexture2D(Texture2D texture, float weight, float scale) {
        this.texture = texture;
        this.weight = weight;
        this.scale = scale;
    }

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texture) {
        this.texture = texture;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
