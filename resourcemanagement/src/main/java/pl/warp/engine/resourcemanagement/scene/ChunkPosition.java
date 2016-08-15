package pl.warp.engine.resourcemanagement.scene;

/**
 * @author Jaca777
 *         Created 2016-08-15 at 21
 */
public class ChunkPosition {
    private long x, y, z;

    public ChunkPosition(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }
}
