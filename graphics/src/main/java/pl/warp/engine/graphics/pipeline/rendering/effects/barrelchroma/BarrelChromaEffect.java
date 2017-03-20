package pl.warp.engine.graphics.pipeline.rendering.effects.barrelchroma;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public class BarrelChromaEffect extends ProgramTextureFlow<BarrelChromaProgram> {

    private int maxDistortionPx;
    private Texture2D output;
    private Graphics graphics;

    public BarrelChromaEffect(int maxDistortionPx) {
        super(new BarrelChromaProgram());
        this.maxDistortionPx = maxDistortionPx;
    }

    @Override
    protected void prepareProgram(BarrelChromaProgram program) {
        program.useTexture(getInput());
        Display display = graphics.getConfig().getDisplay();
        program.useResolution(display.getWidth(), display.getHeight());
        program.useMaxDistortPx(maxDistortionPx);
    }

    @Override
    public void init(Graphics graphics) {
        this.output = new Texture2D(graphics.getConfig().getDisplay().getWidth(), graphics.getConfig().getDisplay().getHeight(),
                GL11.GL_RGBA, GL11.GL_RGBA, false, null);
        this.graphics = graphics;
        scheduleUpdater();
        super.init(graphics);
    }

    private void scheduleUpdater() {
        graphics.getThread().scheduleTask(new UpdaterTask(getProgram()));
    }


    @Override
    public Texture2D getOutput() {
        return output;
    }
}
