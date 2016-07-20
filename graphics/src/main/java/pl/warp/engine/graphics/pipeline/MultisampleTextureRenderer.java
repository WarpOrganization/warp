package pl.warp.engine.graphics.pipeline;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.RenderingSettings;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.shader.identitymultisample.IdentityMultisampleProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class MultisampleTextureRenderer implements Flow<MultisampleTexture2D,Texture2D> {

    private RenderingSettings settings;
    private MultisampleTexture2D srcTexture;
    private TextureFramebuffer destFramebuffer;
    private Texture2D outputTexture;
    private IdentityMultisampleProgram identityProgram;
    private Quad rect;

    public MultisampleTextureRenderer(RenderingSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init() {
        this.identityProgram = new IdentityMultisampleProgram();
        this.rect = new Quad(IdentityMultisampleProgram.ATTR_VERTEX, IdentityMultisampleProgram.ATTR_TEX_COORD);
        initOutput();
    }

    private void initOutput() {
        this.outputTexture = new Texture2D(settings.getWidth(), settings.getHeight(), GL30.GL_RGBA32F, GL11.GL_RGBA, false, null);
        this.destFramebuffer = new TextureFramebuffer(outputTexture);
    }

    @Override
    public void destroy() {
        srcTexture.delete();
        rect.destroy();
    }

    @Override
    public void setInput(MultisampleTexture2D texture) {
        this.srcTexture = texture;
    }

    @Override
    public void update(int delta) {
        destFramebuffer.bindDraw();
        identityProgram.use();
        identityProgram.useTexture(srcTexture);
        rect.bind();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Quad.INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
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
