package net.warpgame.engine.graphics.material;

import net.warpgame.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class Material {

    private Texture2D diffuseTexture;
    private Texture2D normalMap;
    private Texture2D displacementMap;
    private float displacementFactor;
    private float roughness = 0.15f;
    private float shininess = 0.15f;

    public Material(Texture2D diffuseTexture) {
        this.diffuseTexture = diffuseTexture;
    }

    public void setNormalMap(Texture2D normalMap) {
        this.normalMap = normalMap;
    }

    public void setDisplacement(Texture2D displacementMap, float displacementFactor) {
        this.displacementMap = displacementMap;
        this.displacementFactor = displacementFactor;
    }

    public Texture2D getDiffuseTexture() {
        return diffuseTexture;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public Texture2D getDisplacementMap() {
        return displacementMap;
    }

    public float getDisplacementFactor() {
        return displacementFactor;
    }

    public boolean hasDiffuseTexture() {
        return diffuseTexture != null;
    }

    public boolean hasNormalMap() {
        return normalMap != null;
    }

    public boolean hasDisplacementMap() {
        return displacementMap != null;
    }

    public float getRoughness() {
        return roughness;
    }

    public void setRoughness(float roughness) {
        this.roughness = roughness;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
