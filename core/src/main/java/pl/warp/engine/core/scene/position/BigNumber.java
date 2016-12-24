package pl.warp.engine.core.scene.position;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 18
 */
public interface BigNumber {
    int getLevels();

    long getRatio(int level);

    long getValue(int level);

    void setValue(int level, long value);

    void add(BigNumber number);

    void sub(BigNumber number);

    long compareTo(BigNumber number);
}
