package pl.warp.engine.graphics.material;

import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class Material {
    private Texture2D diffuseTexture;
    private Texture2D normalMap;
    private Texture2D bumpMap;

    public Material(Texture2D diffuseTexture, Texture2D normalMap, Texture2D bumpMap) {
        this.diffuseTexture = diffuseTexture;
        this.normalMap = normalMap;
        this.bumpMap = bumpMap;
    }

    public Texture2D getDiffuseTexture() {
        return diffuseTexture;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public Texture2D getBumpMap() {
        return bumpMap;
    }
}
