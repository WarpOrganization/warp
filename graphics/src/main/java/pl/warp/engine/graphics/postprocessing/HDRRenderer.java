package pl.warp.engine.graphics.postprocessing;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.program.rendering.postprocessing.hdr.HDRProgram;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class HDRRenderer implements Flow<WeightedTexture2D[], Texture2D> {

    private static final Logger logger = Logger.getLogger(HDRRenderer.class);

    private RenderingConfig config;

    private WeightedTexture2D[] src;
    private HDRProgram hdrProgram;
    private Quad rect;

    private TextureFramebuffer destFramebuffer;
    private Texture2D outputTexture;

    public HDRRenderer(RenderingConfig config) {
        this.config = config;
    }

    @Override
    public void init(Graphics g) {
        logger.info("Initializing HDR renderer...");
        this.hdrProgram = new HDRProgram();
        this.hdrProgram.setExposure(config.getExposure());
        this.rect = new Quad();
        this.outputTexture = new Texture2D(config.getDisplay().getWidth(), config.getDisplay().getHeight(), GL11.GL_RGB16, GL11.GL_RGB, false, null);
        this.destFramebuffer = new TextureFramebuffer(outputTexture);
        logger.info("HDR renderer initialized.");
    }

    @Override
    public void destroy() {
        rect.destroy();
        destFramebuffer.delete();
        hdrProgram.delete();
        logger.info("HDR renderer destroyed.");
    }

    @Override
    public void setInput(WeightedTexture2D[] input) {
        this.src = input;
    }

    @Override
    public void update() {
        destFramebuffer.bindDraw();
        destFramebuffer.clean();
        hdrProgram.use();
        hdrProgram.useTextures(src);
        rect.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, Quad.INDICES_NUMBER, GL11.GL_UNSIGNED_INT, 0);
        rect.unbind();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        destFramebuffer.resize(newWidth, newHeight);
    }

    @Override
    public Texture2D getOutput() {
        return outputTexture;
    }
}
