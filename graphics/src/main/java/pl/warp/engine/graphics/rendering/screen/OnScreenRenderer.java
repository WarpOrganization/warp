package pl.warp.engine.graphics.rendering.screen;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.framebuffer.ScreenFramebuffer;
import pl.warp.engine.graphics.mesh.shapes.QuadMesh;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import pl.warp.engine.graphics.rendering.screen.program.IdentityProgram;

/**
 * @author Jaca777
 * Created 2017-11-11 at 15
 */
@Service
public class OnScreenRenderer {

    private GBufferManager gBufferManager;
    private ScreenFramebuffer screenFramebuffer;
    private IdentityProgram identityProgram;
    private QuadMesh quadMesh;

    public OnScreenRenderer(GBufferManager gBufferManager, ScreenFramebuffer screenFramebuffer) {
        this.gBufferManager = gBufferManager;
        this.screenFramebuffer = screenFramebuffer;
    }

    public void init() {
        this.quadMesh = new QuadMesh();
        this.identityProgram = new IdentityProgram();
    }

    public void update() {
        screenFramebuffer.bindDraw();
        this.identityProgram.use();
        this.identityProgram.useTexture(gBufferManager.getGBuffer().getTextureName(0), GL11.GL_TEXTURE_2D);
        this.quadMesh.bind();

    }

    public void destroy() {
        quadMesh.destroy();
    }
}
