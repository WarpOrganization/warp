package pl.warp.engine.graphics.postprocessing;

import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
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

    @Override
    public void update(int delta) {

    }

    @Override
    public void init() {
        this.output = new BloomRendererOutput(input);
    }

    @Override
    public void destroy() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public BloomRendererOutput getOutput() {
        return output;
    }

    @Override
    public void setInput(Texture2D input) {
        this.input = input;
    }
}
