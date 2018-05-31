package net.warpgame.engine.graphics.rendering.screenspace;

import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.framebuffer.TextureFramebuffer;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.SceneLightManager;
import net.warpgame.engine.graphics.rendering.screenspace.program.ScreenspaceProgram;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.graphics.window.Display;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jaca777
 * Created 2017-11-11 at 15
 */
@Service
public class ScreenspaceRenderer {

    private static final Logger logger = LoggerFactory.getLogger(ScreenspaceRenderer.class);

    private GBufferManager gBufferManager;
    private ScreenspaceProgram screenspaceProgram;
    private SceneLightManager sceneLightManager;
    private QuadMesh quadMesh;
    private SceneHolder sceneHolder;
    private CameraHolder cameraHolder;
    private ScreenspaceAlbedoHolder albedoHolder;
    private Display display;

    private int maxLights;
    private Texture2D destinationTexture;
    private TextureFramebuffer destinationFramebuffer;

    public ScreenspaceRenderer(
            GBufferManager gBufferManager,
            SceneLightManager sceneLightManager,
            SceneHolder sceneHolder,
            CameraHolder cameraHolder,
            ScreenspaceAlbedoHolder albedoHolder,
            Config config
    ) {
        this.gBufferManager = gBufferManager;
        this.sceneLightManager = sceneLightManager;
        this.sceneHolder = sceneHolder;
        this.cameraHolder = cameraHolder;
        this.albedoHolder = albedoHolder;
        this.display = config.getValue("graphics.display");
        this.maxLights = config.getValue("graphics.rendering.scene.maxLights");
    }

    public void init() {
        this.quadMesh = new QuadMesh();
        try {
            this.screenspaceProgram = new ScreenspaceProgram(maxLights);
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile screenspace rendering program");
        }
        initRenderDestination();
    }

    private void initRenderDestination() {
        this.destinationTexture = new Texture2D(
                display.getWidth(),
                display.getHeight(),
                GL11.GL_RGBA8,
                GL11.GL_RGBA,
                true,
                null
        );
        this.destinationFramebuffer = new TextureFramebuffer(destinationTexture);
        this.albedoHolder.setAlbedoTex(destinationTexture);
    }

    public void update() {
        if (cameraHolder.getCamera() != null) {
            prepareFramebuffer();
            prepareProgram();
            renderScreenspace();
            destinationTexture.genMipmap();
        }//TODO do stuff
    }

    protected void prepareFramebuffer() {
        destinationFramebuffer.bindDraw();
        destinationFramebuffer.clear();
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
        if (scene.hasProperty(CubemapProperty.NAME)) {
            CubemapProperty cubemapProperty = scene.getProperty(CubemapProperty.NAME);
            this.screenspaceProgram.useCubemap(cubemapProperty.getCubemap());
        }
    }


    protected void renderScreenspace() {
        quadMesh.draw();
    }

    public void destroy() {
        quadMesh.destroy();
    }
}
