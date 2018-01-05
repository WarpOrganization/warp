package net.warpgame.engine.core.execution.task;

import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 2016-07-27 at 14
 */
public class UPSCounterTask extends EngineTask {

    private static final long LOGGING_INTERVAL = 2000;
    private static final Logger logger = Logger.getLogger(UPSCounterTask.class);

    private int pointer = 0;
    private int[] deltas;
    private int deltaValuesToAverage;
    private int sum;
    private float upsAverage;
    private float greatestDelta;

    private boolean log;
    private long timeSinceLastLog = 0;

    public UPSCounterTask(int sampleSize, boolean log) {
        this.deltaValuesToAverage = sampleSize;
        this.deltas = new int[sampleSize];
        this.log = log;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        applyDelta(delta);
        if (log) logPretty(delta);
    }


    private void applyDelta(int delta) {
        calcSum(delta);
        calcAverage();
        checkIfGreatest(delta);
    }


    private void calcSum(int delta) {
        this.sum -= deltas[pointer];
        this.sum += delta;
        deltas[pointer] = delta;
        pointer = (pointer + 1) % deltaValuesToAverage;
    }

    private void calcAverage() {
        this.upsAverage = (deltaValuesToAverage * 1000f) / sum;
    }

    private void checkIfGreatest(int delta) {
        if (delta > greatestDelta || pointer == 0)
            greatestDelta = delta;
    }

    private void logPretty(long delta) {
        timeSinceLastLog += delta;
        if (timeSinceLastLog > LOGGING_INTERVAL) {
            logger.debug("Current sample ups average:       " + upsAverage);
            float greatestDeltaUps = 1000/greatestDelta;
            logger.debug("Greatest delta in current sample: "+ greatestDelta + " (makes it " + greatestDeltaUps + " ups)");
            float p90 = getPercentileDelta(0.9f), p90ups = 1000/p90;
            logger.debug("p90 delta in current sample:      " + p90 + " (makes it " + p90ups + " ups)");
            timeSinceLastLog -= LOGGING_INTERVAL;
        }
    }

    //No need to make it faster atm. (might be eventually considered OPT)
    public float getPercentileDelta(float percentile) {
        if(percentile < 0.0 || percentile > 1.0)
            throw new IllegalArgumentException("Percentile value not in <0.0, 1.0> bounds.");
        int[] sortedDeltas = Arrays.stream(deltas)
                .sorted()
                .toArray();
        int p90Index = (int) (sortedDeltas.length * percentile);
        return sortedDeltas[p90Index];
    }


    public float getUpsAverage() {
        return upsAverage;
    }

    public float getGreatestDeltaInSample() {
        return greatestDelta;
    }
}
