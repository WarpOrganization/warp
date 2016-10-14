package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-10-14 at 13
 */
public abstract class PipelineElementOutput1<T> {

    private T firstOutput;

    public PipelineElementOutput1(T firstOutput) {
        this.firstOutput = firstOutput;
    }

    public T getFirstOutput() {
        return firstOutput;
    }

    public <R> PipelineElementOutput1<R> withFirstOutput(R output){
        return newOutput(output);
    }

    protected abstract <R> PipelineElementOutput1<R> newOutput(R firstOutput);

}
