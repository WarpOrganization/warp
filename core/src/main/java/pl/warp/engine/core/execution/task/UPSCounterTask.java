package pl.warp.engine.core.execution.task;

import org.apache.log4j.Logger;

/**
 * @author Jaca777
 *         Created 2016-07-27 at 14
 */
public class UPSCounterTask extends EngineTask {

    private static final long LOGGING_INTERVAL = 2000;
    private static final Logger logger = Logger.getLogger(UPSCounterTask.class);

    private int pointer = 0;
    private float[] deltas;
    private int deltaValuesToAverage;
    private int sum;
    private float upsAverage;
    private float greatestDelta;

    private boolean log;
    private long timeSinceLastLog = 0;

    public UPSCounterTask(int sampleSize, boolean log) {
        this.deltaValuesToAverage = sampleSize;
        this.deltas = new float[sampleSize];
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
        if (log) log(delta);
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

    private void log(long delta) {
        timeSinceLastLog += delta;
        if (timeSinceLastLog > LOGGING_INTERVAL) {
            logger.debug("Current sample ups average: " + upsAverage);
            logger.debug("Greatest delta in a current sample: " + greatestDelta);
            timeSinceLastLog -= LOGGING_INTERVAL;
        }
    }

    public float getUpsAverage() {
        return upsAverage;
    }

    public float getGreatestDeltaInSample() {
        return greatestDelta;
    }
}
