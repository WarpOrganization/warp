package pl.warp.engine.graphics.postprocessing;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 14
 */
public class WeightedTexture2D {
    private Texture2D texture;
    private float weight;

    public WeightedTexture2D(Texture2D texture, float weight) {
        this.texture = texture;
        this.weight = weight;
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
}
