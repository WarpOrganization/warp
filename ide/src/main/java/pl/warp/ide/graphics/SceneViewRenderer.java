package pl.warp.ide.graphics;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import pl.warp.engine.graphics.pipeline.output.RenderingPipelineOutput;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 19
 */
public class SceneViewRenderer {
    private Canvas canvas;
    private final RenderingPipelineOutput pipelineOutput;

    public SceneViewRenderer(Canvas canvas, RenderingPipelineOutput pipelineOutput) {
        this.canvas = canvas;
        this.pipelineOutput = pipelineOutput;
    }

    public void startRendering() {
        pipelineOutput.addOutputListener(this::drawScene);
    }

    public void drawScene(ByteBuffer scene) {
        Platform.runLater(() -> drawOnCanvas(canvas, scene));
    }

    private byte[] dataArray;

    private void drawOnCanvas(Canvas canvas, ByteBuffer data) {
        synchronized (pipelineOutput) {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            if (dataArray == null || dataArray.length != data.limit() )
                dataArray = new byte[data.limit()];
            data.get(dataArray);
            canvas.getGraphicsContext2D().getPixelWriter().setPixels(0, 0,
                    (int) canvas.getWidth(), (int) canvas.getHeight(), PixelFormat.getByteRgbInstance(), dataArray, 0, 0);
        }
    }
}
