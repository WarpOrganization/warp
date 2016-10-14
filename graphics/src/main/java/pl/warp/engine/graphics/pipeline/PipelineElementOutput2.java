package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-10-14 at 13
 */
public abstract class PipelineElementOutput2<T, S> extends PipelineElementOutput1<T> {

    private S secondOutput;

    public PipelineElementOutput2(T firstOutput, S secondOutput) {
        super(firstOutput);
        this.secondOutput = secondOutput;
    }

    public S getSecondOutput() {
        return secondOutput;
    }

    @Override
    public <R> PipelineElementOutput2<R, S> withFirstOutput(R output) {
        return newOutput(output, getSecondOutput());
    }

    public <R> PipelineElementOutput2<T, R> withSecondOutput(R output) {
        return newOutput(getFirstOutput(), output);
    }

    @Override
    protected <R> PipelineElementOutput1<R> newOutput(R firstOutput) {
        return newOutput(firstOutput, secondOutput);
    }

    protected abstract <U, V> PipelineElementOutput2<U, V> newOutput(U firstOutput, V secondOutput);

}
