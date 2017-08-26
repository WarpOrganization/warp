package pl.warp.engine.resourcemanagement.scene;

import org.joml.Vector3f;
import org.joml.Vector3i;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.common.properties.TransformProperty;

/**
 * @author Jaca777
 *         Created 2016-08-15 at 21
 */
public class ChunkMapLoader {

    private ChunkMap chunkMap;
    private Component actorComponent;
    private ChunkLoader loader;

    public ChunkMapLoader(ChunkMap chunkMap, Component actorComponent, ChunkLoader loader) {
        this.chunkMap = chunkMap;
        this.actorComponent = actorComponent;
        this.loader = loader;
    }

    public void update() {
        loadChunks();
        updateComponents();
    }

    private void loadChunks() {
        Vector3i actorPositionDelta = getChunkPositionDelta(actorComponent);
        if (!isZero(actorPositionDelta)) loadChunks(actorPositionDelta);
    }

    private Vector3i tempVector = new Vector3i();

    private Vector3i getChunkPositionDelta(Component component) {
        TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        Vector3f translation = transformProperty.getTranslation();
        return tempVector.set(
                getChunkPositionDelta(translation.x),
                getChunkPositionDelta(translation.y),
                getChunkPositionDelta(translation.z));
    }

    private boolean isZero(Vector3i vector) {
        return vector.x == 0 && vector.y == 0 && vector.z == 0;
    }

    private int getChunkPositionDelta(float f) {
        if (f < 0) return -1;
        else if (f > chunkMap.getChunkSize()) return 1;
        else return 0;
    }

    private void loadChunks(Vector3i actorPositionDelta) {
        
    }


    private void updateComponents() {

    }
}
