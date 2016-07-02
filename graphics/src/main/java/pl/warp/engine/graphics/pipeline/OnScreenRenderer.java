package pl.warp.engine.graphics.pipeline;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.mesh.MeshUtil;
import pl.warp.engine.graphics.shader.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 23
 */
public class OnScreenRenderer implements Sink<MultisampleTexture2D> {

    private int srcTexture;
    private IdentityProgram identityProgram;
    private int rectVAO;

    @Override
    public void init() {
        identityProgram = new IdentityProgram();
        rectVAO = MeshUtil.mkFullRect(identityProgram.getAttrVertex(), identityProgram.getAttrTexCoord());
    }

    @Override
    public void setInput(MultisampleTexture2D texture) {
        this.srcTexture = texture.getTexture();
    }

    @Override
    public void update(long delta) {
        identityProgram.use();
        identityProgram.useTexture(srcTexture);
        Framebuffer.SCREEN_FRAMEBUFFER.bindDraw();
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
