package pl.warp.engine.core.scene.position;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2016-12-17 at 02
 */
public class BigNumberN implements BigNumber {

    private int levels;
    private long[] ratios;
    private long[] values;

    public BigNumberN(int levels, long[] ratios) {
        if (ratios.length != levels)
            throw new IllegalArgumentException("Size of ratio array doesn't equal level number.");
        this.levels = levels;
        this.ratios = ratios;
        this.values = new long[levels];
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
    public long getValue(int level) {
        return values[level];
    }

    @Override
    public void setValue(int level, long value) {
        values[level] = value;
    }


    @Override
    public void add(BigNumber number) {
        add(number, 1);
    }


    @Override
    public void sub(BigNumber number) {
        add(number, -1);
    }

    private void add(BigNumber number, int sign) {
        if (number.getLevels() > getLevels())
            throw new UnsupportedOperationException("BigNumber can't be added to the one with less levels");
        for (int l = 0; l < number.getLevels(); l++)
            values[l] += number.getValue(l) * sign;
        carry();
    }

    private void carry() {
        try {
            for (int l = 0; l < levels; l++) {
                if (Math.abs(values[l]) > ratios[l]) {
                    values[l + 1] += Math.signum(values[l]);
                    values[l] -= Math.signum(values[l]) * ratios[l];
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Size of BigVector exceeds the limit.", e);
        }
    }

    @Override
    public long compareTo(BigNumber number) {
        for (int l = number.getLevels() - 1; l >= 0; l--) {
            if (l >= levels) {
                if (number.getValue(l) != 0)
                    return number.getValue(l);
            } else if (number.getValue(l) != getValue(l))
                return number.getValue(l) - getValue(l);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigNumberN that = (BigNumberN) o;
        return getLevels() == that.getLevels() &&
                Arrays.equals(ratios, that.ratios) &&
                Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevels(), ratios, values);
    }
}
