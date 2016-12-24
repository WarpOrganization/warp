package pl.warp.engine.core.scene.position;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 17
 *         3D complex vector.
 */
public interface BigVector {
    int getLevels();

    long getRatio(int level);

    BigNumber getX();

    BigNumber getY();

    BigNumber getZ();

    void add(BigVector vector);

    void sub(BigVector vector);

    BigNumber getApproximateLength(int skipLevels, BigNumber dest);

    default BigNumber getApproximateLength(BigNumber dest) {
        return getApproximateLength(0, dest);
    }

    long compareTo(BigVector vector);
}
