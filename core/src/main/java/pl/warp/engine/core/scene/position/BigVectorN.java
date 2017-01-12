package pl.warp.engine.core.scene.position;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 18
 */
public class BigVectorN implements BigVector {

    // DEFAULT_RATIO = 149597871000000L

    private int levels;
    private long[] ratios;
    private BigNumber x, y, z;

    public BigVectorN(int n, long[] ratios) {
        this.levels = n;//tesdt
        this.ratios = ratios;
        initCoords();
    }

    private void initCoords() {
        this.x = new BigNumberN(levels, ratios);
        this.y = new BigNumberN(levels, ratios);
        this.z = new BigNumberN(levels, ratios);
    }


    @Override
    public int getLevels() {
        return levels;
    }

    @Override
    public long getRatio(int level) {
        return ratios[level];
    }

    @Override
    public BigNumber getX() {
        return x;
    }

    @Override
    public BigNumber getY() {
        return y;
    }

    @Override
    public BigNumber getZ() {
        return z;
    }

    private void add(BigVector vector, int sign) {
        x.add(vector.getX());
        y.add(vector.getY());
        z.add(vector.getZ());
    }

    @Override
    public void add(BigVector vector) {
        add(vector, 1);
    }

    @Override
    public void sub(BigVector vector) {
        add(vector, -1);
    }

    private final Object compareLock = new Object();
    private final BigNumber tmpNumber1 = new BigNumberN(levels, ratios);
    private final BigNumber tmpNumber2 = new BigNumberN(levels, ratios);

    @Override
    public long compareTo(BigVector vector) {
        synchronized (compareLock) {
            return getApproximateLength(tmpNumber1).compareTo(vector.getApproximateLength(tmpNumber2));
        }
    }

    @Override
    public BigNumber getApproximateLength(int skipLevels, BigNumber dest) {
        if (dest.getLevels() < this.getLevels() - skipLevels)
            throw new IllegalArgumentException("Can't store length in BigNumber with less levels");
        if (skipLevels > levels)
            throw new IllegalArgumentException("Can't skip a greater number of levels than a vector has.");
        for (int l = 0; l < levels - skipLevels; l++) {
            double length = getLevelApproximateLength(l + skipLevels);
            dest.setValue(l, (long) length);
        }
        return dest;
    }

    private double getLevelApproximateLength(int level) { //Not using a third power for more accuracy.
        double yValue = y.getValue(level);
        double xValue = x.getValue(level);
        double xyLength = Math.sqrt(xValue * xValue + yValue * yValue);
        double zValue = z.getValue(level);
        return Math.sqrt(xyLength * xyLength + zValue * zValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigVectorN that = (BigVectorN) o;
        return levels == that.levels &&
                Arrays.equals(ratios, that.ratios) &&
                Objects.equals(getX(), that.getX()) &&
                Objects.equals(getY(), that.getY()) &&
                Objects.equals(getZ(), that.getZ());
    }

    @Override
    public int hashCode() {
        return Objects.hash(levels, ratios, getX(), getY(), getZ());
    }
}
