package pl.warp.engine.graphics.pipeline.output;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 19
 */
public class RenderingPipelineOutput {
    private Set<Consumer<ByteBuffer>> updateListeners;

    public RenderingPipelineOutput() {
        this.updateListeners = new HashSet<>();
    }

    public synchronized void addOutputListener(Consumer<ByteBuffer> listener) {
        this.updateListeners.add(listener);
    }

    public synchronized void update(ByteBuffer output) {
        for(Consumer<ByteBuffer> listener : updateListeners)
            listener.accept(output);
    }
}
