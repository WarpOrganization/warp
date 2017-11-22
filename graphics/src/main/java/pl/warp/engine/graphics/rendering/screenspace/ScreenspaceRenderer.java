package pl.warp.engine.graphics.rendering.screenspace;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.framebuffer.ScreenFramebuffer;
import pl.warp.engine.graphics.mesh.shapes.QuadMesh;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import pl.warp.engine.graphics.rendering.screenspace.light.SceneLightManager;
import pl.warp.engine.graphics.rendering.screenspace.program.ScreenspaceProgram;

/**
 * @author Jaca777
 * Created 2017-11-11 at 15
 */
@Service
public class ScreenspaceRenderer {

    private GBufferManager gBufferManager;
    private ScreenFramebuffer screenFramebuffer;
    private ScreenspaceProgram screenspaceProgram;
    private SceneLightManager sceneLightManager;
    private QuadMesh quadMesh;

    public ScreenspaceRenderer(GBufferManager gBufferManager, ScreenFramebuffer screenFramebuffer, SceneLightManager sceneLightManager) {
        this.gBufferManager = gBufferManager;
        this.screenFramebuffer = screenFramebuffer;
    }

    public void init() {
        this.quadMesh = new QuadMesh();
        this.screenspaceProgram = new ScreenspaceProgram();
    }

    public void update() {
        screenFramebuffer.bindDraw();
        screenFramebuffer.clean();
        this.screenspaceProgram.use();
        this.screenspaceProgram.useGBuffer(gBufferManager.getGBuffer());
        quadMesh.draw();
        this.quadMesh.bind();
    }

    public void destroy() {
        quadMesh.destroy();
    }
}
