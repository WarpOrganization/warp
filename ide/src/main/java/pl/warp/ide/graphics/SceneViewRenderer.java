package pl.warp.ide.graphics;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import pl.warp.engine.graphics.pipeline.output.RenderingPipelineOutputHandler;
import pl.warp.engine.graphics.pipeline.output.RenderingPipelineOutputHandler.RenderingPipelineOutput;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 19
 */
public class SceneViewRenderer {
    private Canvas canvas;
    private final RenderingPipelineOutputHandler pipelineOutput;

    public SceneViewRenderer(Canvas canvas, RenderingPipelineOutputHandler pipelineOutput) {
        this.canvas = canvas;
        this.pipelineOutput = pipelineOutput;
    }

    public void startRendering() {
        pipelineOutput.addOutputListener(this::drawScene);
    }

    public void drawScene(RenderingPipelineOutput scene) {
        Platform.runLater(() -> drawOnCanvas(canvas, scene));
    }


    private void drawOnCanvas(Canvas canvas, RenderingPipelineOutput output) {
        synchronized (pipelineOutput) {
            GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
            graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext2D.getPixelWriter().setPixels(0, 0,
                    (int) canvas.getWidth(), (int) canvas.getHeight(), PixelFormat.getByteRgbInstance(), output.getData(), output.getWidth() * 3);
        }
    }
}
