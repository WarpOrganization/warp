package net.warpgame.engine.graphics.framebuffer;

import net.warpgame.engine.graphics.texture.Texture2D;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 * Created 2018-01-06 at 18
 */
public class TextureFramebuffer extends Framebuffer {

    private Texture2D destTexture;

    public TextureFramebuffer(Texture2D destTexture) {
        super(GL30.glGenFramebuffers());
        this.destTexture = destTexture;
        initialize();
    }

    private void initialize() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, getName());
        GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glFramebufferTexture2D(
                GL30.GL_FRAMEBUFFER,
                GL30.GL_COLOR_ATTACHMENT0,
                GL11.GL_TEXTURE_2D,
                destTexture.getTexture(),
                0
        );
    }

    @Override
    public int getWidth() {
        return destTexture.getWidth();
    }

    @Override
    public int getHeight() {
        return destTexture.getHeight();
    }

    @Override
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

}
