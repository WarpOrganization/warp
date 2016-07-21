package pl.warp.engine.graphics.postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.shader.program.postprocessing.bloomdetection.BloomDetectionProgram;
import pl.warp.engine.graphics.shader.program.postprocessing.gaussianblur.GaussianBlurProgram;
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

    private BloomDetectionProgram bloomDetectionProgram;
    private GaussianBlurProgram gaussianBlurProgram;
    private Quad quad;

    private RenderingConfig config;

    public BloomRenderer(RenderingConfig config) {
        this.config = config;
    }

    @Override
    public void update(int delta) {
        detectBloom();
        blur();
    }

    private void detectBloom() {
        bloomDetectionFramebuffer.bindDraw();
        bloomDetectionFramebuffer.clean();
        bloomDetectionProgram.use();
        bloomDetectionProgram.useTexture(input);
        quad.bind();
        quad.draw();
    }

    private void blur() {
        blurVertically();
        blurHorizontally();
    }

    private void blurVertically() {
        verticalBlurFramebuffer.bindDraw();
        verticalBlurFramebuffer.clean();
        quad.bind();
        gaussianBlurProgram.use();
        gaussianBlurProgram.useTexture(bloomDetectionTexture);
        gaussianBlurProgram.setStage(GaussianBlurProgram.GaussianBlurStage.VERTICAL);
        quad.draw();
    }

    private void blurHorizontally() {
        blurredBloomFramebuffer.bindDraw();
        blurredBloomFramebuffer.clean();
        quad.bind();
        gaussianBlurProgram.use();
        gaussianBlurProgram.useTexture(verticalBlurTexture);
        gaussianBlurProgram.setStage(GaussianBlurProgram.GaussianBlurStage.HORIZONTAL);
        quad.draw();
    }


    @Override
    public void init() {
        createTextures();
        createFramebuffers();
        createPrograms();
        this.quad = new Quad(BloomDetectionProgram.ATTR_VERTEX, BloomDetectionProgram.ATTR_TEX_COORD);
        this.output = new BloomRendererOutput(input, blurredBloomTexture);
    }

    private void createTextures() {
        this.bloomDetectionTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        this.verticalBlurTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        this.blurredBloomTexture = new Texture2D(config.getWidth(), config.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
    }

    private void createFramebuffers() {
        this.bloomDetectionFramebuffer = new TextureFramebuffer(bloomDetectionTexture);
        this.verticalBlurFramebuffer = new TextureFramebuffer(verticalBlurTexture);
        this.blurredBloomFramebuffer = new TextureFramebuffer(blurredBloomTexture);
    }

    private void createPrograms() {
        this.bloomDetectionProgram = new BloomDetectionProgram();
        this.gaussianBlurProgram = new GaussianBlurProgram(config);
    }

    @Override
    public void destroy() {
        this.bloomDetectionFramebuffer.delete();
        this.verticalBlurFramebuffer.delete();
        this.blurredBloomFramebuffer.delete();
        this.bloomDetectionProgram.delete();
        this.gaussianBlurProgram.delete();
        this.quad.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.bloomDetectionFramebuffer.resize(newWidth, newHeight);
        this.verticalBlurFramebuffer.resize(newWidth, newHeight);
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
