package pl.warp.engine.graphics.pipeline.rendering.effects.mosaic;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 17
 */
public class MosaicEffect extends ProgramTextureFlow<MosaicEffectProgram> {

    private float tileRadius;
    private float innerTileRadius;
    private Texture2D output;
    private Graphics graphics;

    public MosaicEffect(float tileRadius, float innerTileRadius) {
        super(new MosaicEffectProgram());
        this.tileRadius = tileRadius;
        this.innerTileRadius = innerTileRadius;
    }

    @Override
    protected void prepareProgram(MosaicEffectProgram program) {
        program.useTexture(getInput());
        Display display = graphics.getConfig().getDisplay();
        program.useDimensions(display.getWidth(), display.getHeight());
        program.useTileRadius(this.tileRadius, this.innerTileRadius);
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
