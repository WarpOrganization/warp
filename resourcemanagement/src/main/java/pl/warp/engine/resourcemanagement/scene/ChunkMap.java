package pl.warp.engine.resourcemanagement.scene;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.SceneComponent;

/**
 * @author Jaca777
 *         Created 2016-08-15 at 20
 */
public class ChunkMap extends SceneComponent {

    private int chunkSize;
    private ChunkPosition center;

    public ChunkMap(Component parent, int chunkSize) {
        super(parent);
        this.chunkSize = chunkSize;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public ChunkPosition getCenter() {
        return center;
    }
}
