package pl.warp.engine.resourcemanagement.scene;

import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-08-15 at 20
 */
public class Chunk extends Component {

    private ChunkPosition position;

    public Chunk(ChunkMap parent, ChunkPosition position) {
        super(parent);
        this.position = position;
    }

    public ChunkPosition getPosition() {
        return position;
    }
}
