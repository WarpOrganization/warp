package pl.warp.engine.graphics.pipeline.impl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.mesh.MeshUtil;
import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.shader.IdentityProgram;
import pl.warp.engine.graphics.texture.Texture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 23
 */
public class OnScreenSink implements Sink<Texture2D> {

    private int srcTexture;
    private IdentityProgram identityProgram;
    private int rectVAO;

    @Override
    public void init() {
        identityProgram = new IdentityProgram();
        rectVAO = MeshUtil.mkFullRect(identityProgram.getAttrVertex(), identityProgram.getAttrTexCoord());
    }

    @Override
    public void setInput(Texture2D texture) {
        this.srcTexture = texture.getTexture();
    }

    @Override
    public void render() {
        identityProgram.use();
        identityProgram.useTexture(srcTexture, GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL_DEPTH_TEST);
        GL30.glBindVertexArray(rectVAO);
        GL11.glDrawElements(GL11.GL_TRIANGLES, MeshUtil.INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
        GL11.glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //do nothing
    }
}
