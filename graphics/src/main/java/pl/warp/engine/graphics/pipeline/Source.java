package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 12
 */
public interface Source<O> extends PipelineElement {
    O getOutput();
}
