package net.warpgame.engine.graphics.texture;

import org.lwjgl.opengl.GL32;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;

/**
 * @author Jaca777
 *         Created 07.04.15 at 13:39
 */
public class MultisampleTexture2D extends TextureShape2D {
    private int samples;
    private int width;
    private int height;

    public MultisampleTexture2D(int width, int height, int internalformat, int format, int samples) {
        super(GL32.GL_TEXTURE_2D_MULTISAMPLE, genTextureMultisample2D(width, height, samples, internalformat), internalformat, format, false);
        this.samples = samples;
        this.width = width;
        this.height = height;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
        bind();
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
    }

    private static int genTextureMultisample2D(int width, int height, int samples, int internalformat) {
        int texture = glGenTextures();
        glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
        return texture;
    }

    @Override
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
