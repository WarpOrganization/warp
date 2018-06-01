package net.warpgame.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 06.04.15 at 15:21
 */
public abstract class Framebuffer {

    protected int name;

    public Framebuffer(int name) {
        this.name = name;
    }

    public void bindDraw() {
        GL30.glBindFramebuffer(GL_DRAW_FRAMEBUFFER, name);
        adjustViewport();
    }

    public void adjustViewport() {
        GL11.glViewport(0, 0, this.getWidth(), this.getHeight());
    }

    public void bindRead() {
        GL30.glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void delete() {
        GL30.glDeleteFramebuffers(this.name);
    }

    public abstract int getWidth();


    public abstract int getHeight();


    public abstract void clear();

    public boolean isComplete() {
        return GL30.glCheckFramebufferStatus(getName()) == GL30.GL_FRAMEBUFFER_COMPLETE;
    }




}
