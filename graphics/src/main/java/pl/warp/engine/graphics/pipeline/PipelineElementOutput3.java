package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-10-14 at 13
 */
public abstract class PipelineElementOutput3<T, S, U> extends PipelineElementOutput2<T, S> {

    private U thirdOutput;

    public PipelineElementOutput3(T firstOutput, S secondOutput, U thirdOutput) {
        super(firstOutput, secondOutput);
        this.thirdOutput = thirdOutput;
    }

    public U getThirdOutput() {
        return thirdOutput;
    }

    @Override
    public <R> PipelineElementOutput3<R, S, U> withFirstOutput(R output) {
        return newOutput(output, getSecondOutput(), getThirdOutput());
    }

    @Override
    public <R> PipelineElementOutput3<T, R, U> withSecondOutput(R output) {
        return newOutput(getFirstOutput(), output, getThirdOutput());
    }

    public <R> PipelineElementOutput3<T, S, R> withThirdOutput(R output) {
        return newOutput(getFirstOutput(), getSecondOutput(), output);
    }

    @Override
    protected <V, W> PipelineElementOutput2<V, W> newOutput(V firstOutput, W secondOutput) {
        return newOutput(firstOutput, secondOutput, getThirdOutput());
    }

    protected abstract <V, W, X> PipelineElementOutput3<V, W, X> newOutput(V firstOutput, W secondOutput, X thirdOutput);
}
