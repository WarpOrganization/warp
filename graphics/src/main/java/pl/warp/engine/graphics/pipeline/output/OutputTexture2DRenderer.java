package pl.warp.engine.graphics.pipeline.output;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Textures;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-12-09 at 19
 */
public class OutputTexture2DRenderer implements Sink<Texture2D> {

    private Texture2D srcTexture;
    private ByteBuffer outputTexture;
    private final RenderingPipelineOutput output;


    public OutputTexture2DRenderer() {
        this.output = new RenderingPipelineOutput();
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {
    }

    @Override
    public void setInput(Texture2D input) {
        this.srcTexture = input;
        initializeBuffer(input.getHeight(), input.getWidth(), input.getInternalformat());
    }

    @Override
    public void update(int delta) {
        synchronized (output) {
            outputTexture.clear();
            srcTexture.bind();
            GL11.glGetTexImage(srcTexture.getType(), 0, srcTexture.getFormat(), GL11.GL_UNSIGNED_BYTE, outputTexture);
            outputTexture.rewind();
        }
        this.output.update(outputTexture);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        initializeBuffer(newWidth, newHeight, srcTexture.getInternalformat());
    }

    private void initializeBuffer(int width, int height, int textureInternalformat) {
        int texelSize = Textures.getTexelSizeInBytes(textureInternalformat);
        int textureSize = width * height * texelSize;
        this.outputTexture = BufferUtils.createByteBuffer(textureSize);
    }

    public RenderingPipelineOutput getOutput() {
        return output;
    }
}
