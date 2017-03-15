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
    private ByteBuffer outputData;
    private final RenderingPipelineOutputHandler output;
    private int width, height;

    public OutputTexture2DRenderer() {
        this.output = new RenderingPipelineOutputHandler();
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
        this.width = input.getWidth();
        this.height = input.getHeight();
        initializeBuffer(width, height, input.getInternalformat());
    }

    @Override
    public void update() {
        synchronized (output) {
            outputData.clear();
            srcTexture.bind();
            GL11.glGetTexImage(srcTexture.getType(), 0, srcTexture.getFormat(), GL11.GL_UNSIGNED_BYTE, outputData);
            outputData.rewind();
        }
        this.output.update(outputData, width, height);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
        initializeBuffer(newWidth, newHeight, srcTexture.getInternalformat());
    }

    private void initializeBuffer(int width, int height, int textureInternalformat) {
        int texelSize = Textures.getTexelSizeInBytes(textureInternalformat);
        int textureSize = width * height * texelSize;
        this.outputData = BufferUtils.createByteBuffer(textureSize);
    }

    public RenderingPipelineOutputHandler getOutput() {
        return output;
    }
}
