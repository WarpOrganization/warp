package pl.warp.engine.graphics.pipeline.output;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 19
 */
public class RenderingPipelineOutputHandler {
    private Set<Consumer<RenderingPipelineOutput>> updateListeners;
    private RenderingPipelineOutput currentOutput = new RenderingPipelineOutput();

    public RenderingPipelineOutputHandler() {
        this.updateListeners = new HashSet<>();
    }

    public synchronized void addOutputListener(Consumer<RenderingPipelineOutput> listener) {
        this.updateListeners.add(listener);
    }

    public synchronized void update(ByteBuffer data, int width, int height) {
        currentOutput.setData(data);
        currentOutput.setWidth(width);
        currentOutput.setHeight(height);
        for (Consumer<RenderingPipelineOutput> listener : updateListeners)
            listener.accept(currentOutput);
    }

    public static class RenderingPipelineOutput {
        private ByteBuffer data;
        private int width, height;

        public ByteBuffer getData() {
            return data;
        }

        private void setData(ByteBuffer data) {
            this.data = data;
        }

        public int getWidth() {
            return width;
        }

        private void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        private void setHeight(int height) {
            this.height = height;
        }
    }
}
