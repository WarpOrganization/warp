package pl.warp.engine.graphics.rendering.screenspace;

import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.framebuffer.ScreenFramebuffer;
import pl.warp.engine.graphics.mesh.shapes.QuadMesh;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import pl.warp.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
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
