package net.warpgame.engine.graphics.rendering.screenspace;

import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.framebuffer.ScreenFramebuffer;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.SceneLightManager;
import net.warpgame.engine.graphics.rendering.screenspace.program.ScreenspaceProgram;

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
    private SceneHolder sceneHolder;
    private CameraHolder cameraHolder;
    private int maxLights;

    public ScreenspaceRenderer(
            GBufferManager gBufferManager,
            ScreenFramebuffer screenFramebuffer,
            SceneLightManager sceneLightManager,
            SceneHolder sceneHolder,
            CameraHolder cameraHolder,
            Config config
    ) {
        this.gBufferManager = gBufferManager;
        this.screenFramebuffer = screenFramebuffer;
        this.sceneLightManager = sceneLightManager;
        this.sceneHolder = sceneHolder;
        this.cameraHolder = cameraHolder;
        this.maxLights = config.getValue("graphics.rendering.scene.maxLights");
    }

    public void init() {
        this.quadMesh = new QuadMesh();
        this.screenspaceProgram = new ScreenspaceProgram(maxLights);
    }

    public void update() {
        prepareFramebuffer();
        prepareProgram();
        renderScreenspace();
    }

    protected void prepareFramebuffer() {
        screenFramebuffer.bindDraw();
        screenFramebuffer.clean();
    }


    protected void prepareProgram() {
        this.screenspaceProgram.use();
        this.screenspaceProgram.useCamera(cameraHolder.getCamera());
        this.screenspaceProgram.useLights(sceneLightManager.getLightPositions(), sceneLightManager.getLightProperties());
        this.screenspaceProgram.useGBuffer(gBufferManager.getGBuffer());
        prepareCubemap();
    }

    private void prepareCubemap() {
        Scene scene = sceneHolder.getScene();
        if(scene.hasProperty(CubemapProperty.NAME)) {
            CubemapProperty cubemapProperty = scene.getProperty(CubemapProperty.NAME);
            this.screenspaceProgram.useCubemap(cubemapProperty.getCubemap());
        }
    }


    protected void renderScreenspace() {
        quadMesh.draw();
        this.quadMesh.bind();
    }

    public void destroy() {
        quadMesh.destroy();
    }
}
