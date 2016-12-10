package pl.warp.engine.graphics.pipeline;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.shader.program.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Textures;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-12-09 at 19
 */
public class OutputTexture2DRenderer implements Sink<Texture2D> {

    private Texture2D srcTexture;
    private IdentityProgram identityProgram;
    private Quad rect;
    private ByteBuffer outputTexture;

    @Override
    public void init() {
        this.identityProgram = new IdentityProgram();
        this.rect = new Quad();
    }

    @Override
    public void destroy() {
        rect.destroy();
    }

    @Override
    public void setInput(Texture2D input) {
        this.srcTexture = input;
        initializeBuffer(input.getHeight(), input.getWidth(), input.getInternalformat());
    }

    @Override
    public void update(int delta) {
        outputTexture.clear();
        GL11.glGetTexImage(srcTexture.getTexture(), 0, srcTexture.getFormat(), srcTexture.getType(), outputTexture);
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

    public ByteBuffer getOutputTexture() {
        return outputTexture;
    }
}
