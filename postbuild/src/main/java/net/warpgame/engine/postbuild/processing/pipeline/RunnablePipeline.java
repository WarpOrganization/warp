package net.warpgame.engine.postbuild.processing.pipeline;

import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jaca777
 * Created 2018-08-06 at 18
 */
public class RunnablePipeline {

    private static final Logger logger = LoggerFactory.getLogger(RunnablePipeline.class);

    private Source source;
    private Sink sink;

    public RunnablePipeline(Source source, Sink sink) {
        this.source = source;
        this.sink = sink;
    }

    public void run() throws Exception {
        sink.process(source.get());
    }
}
