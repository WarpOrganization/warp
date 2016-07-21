package pl.warp.engine.graphics.postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class BloomRenderer implements Flow<Texture2D, BloomRendererOutput> {

    private Texture2D input;
    private BloomRendererOutput output;

    private Texture2D bloomDetectionTexture;
    private Texture2D verticalBlurTexture;
    private Texture2D blurredBloomTexture;

    private TextureFramebuffer bloomDetectionFramebuffer;
    private TextureFramebuffer verticalBlurFramebuffer;
    private TextureFramebuffer blurredBloomFramebuffer;

    private RenderingConfig config;

    public BloomRenderer(RenderingConfig config) {
        this.config = config;
    }

    @Override
    public void update(int delta) {

    }

    @Override
    public void init() {
        createTextures();
        createFramebuffers();
        this.output = new BloomRendererOutput(input, blurredBloomTexture);
    }

    private void createTextures() {
        this.bloomDetectionTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        this.verticalBlurTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        this.blurredBloomTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
    }

    private void createFramebuffers() {
        this.bloomDetectionFramebuffer = new TextureFramebuffer(bloomDetectionTexture);
    }

    @Override
    public void destroy() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.bloomDetectionFramebuffer.resize(newWidth, newHeight);
        this.bloomDetectionFramebuffer.resize(newWidth, newHeight);
        this.bloomDetectionFramebuffer.resize(newWidth, newHeight);
    }

    @Override
    public BloomRendererOutput getOutput() {
        return output;
    }

    @Override
    public void setInput(Texture2D input) {
        this.input = input;
        output.setScene(input);
    }
}
