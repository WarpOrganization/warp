package pl.warp.engine.graphics.pipeline.rendering.effects.edgedetection;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public class EdgeDetection extends ProgramTextureFlow<EdgeDetectionProgram, Texture2D> {

    private Vector3f edgeColor;
    private Texture2D output;
    private Graphics graphics;

    public EdgeDetection(Vector3f edgeColor) {
        super(new EdgeDetectionProgram());
        this.edgeColor = edgeColor;
    }

    @Override
    protected void prepareProgram(EdgeDetectionProgram program) {
        program.useTexture(getInput());
        program.useEdgeColor(edgeColor);
        Display display = graphics.getConfig().getDisplay();
        program.useDimensions(display.getWidth(), display.getHeight());
    }

    @Override
    public void init(Graphics graphics) {
        this.output = new Texture2D(graphics.getConfig().getDisplay().getWidth(), graphics.getConfig().getDisplay().getHeight(),
                GL11.GL_RGBA, GL11.GL_RGBA, false, null);
        this.graphics = graphics;
        super.init(graphics);
    }


    @Override
    public Texture2D getOutput() {
        return output;
    }
}
