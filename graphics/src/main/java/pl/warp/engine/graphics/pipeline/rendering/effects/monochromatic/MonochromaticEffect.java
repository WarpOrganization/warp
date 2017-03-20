package pl.warp.engine.graphics.pipeline.rendering.effects.monochromatic;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 22
 */
public class MonochromaticEffect extends ProgramTextureFlow<MonochromaticProgram> {

    private Texture2D output;
    private Vector3f color;

    public MonochromaticEffect(Vector3f color) {
        super(new MonochromaticProgram());
        this.color = color;
    }

    @Override
    protected void prepareProgram(MonochromaticProgram program) {
        program.useTexture(getInput());
        program.useColor(color);
    }

    @Override
    public void init(Graphics graphics) {
        this.output = new Texture2D(graphics.getConfig().getDisplay().getWidth(), graphics.getConfig().getDisplay().getHeight(),
                GL11.GL_RGBA, GL11.GL_RGBA, false, null);
        super.init(graphics);
    }

    @Override
    public Texture2D getOutput() {
        return output;
    }
}
