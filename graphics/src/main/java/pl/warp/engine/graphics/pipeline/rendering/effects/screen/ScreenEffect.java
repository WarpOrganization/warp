package pl.warp.engine.graphics.pipeline.rendering.effects.screen;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 21
 */
public class ScreenEffect extends ProgramTextureFlow<ScreenProgram, Texture2D> {

    private Texture2D output;
    private Graphics graphics;

    public ScreenEffect() {
        super(new ScreenProgram());
    }

    @Override
    protected void prepareProgram(ScreenProgram program) {
        program.useTexture(getInput());
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
