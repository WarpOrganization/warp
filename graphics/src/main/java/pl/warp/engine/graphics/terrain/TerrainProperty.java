package pl.warp.engine.graphics.terrain;

import org.joml.Vector2f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-04 at 11
 */
public class TerrainProperty extends Property<Component> {
    public static final String TERRAIN_PROPERTY_NAME = "terrainProperty";
    private Vector2f terrainSize;
    private Texture2D normalMap;

    public TerrainProperty(Vector2f terrainSize, Texture2D normalMap) {
        super(TERRAIN_PROPERTY_NAME);
        this.terrainSize = terrainSize;
        this.normalMap = normalMap;
    }

    public Vector2f getTerrainSize() {
        return terrainSize;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }
}
