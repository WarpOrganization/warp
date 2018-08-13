package net.warpgame.engine.graphics.rendering.scene;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.rendering.scene.gbuffer.GBufferFramebuffer;
import net.warpgame.engine.graphics.rendering.scene.gbuffer.GBufferManager;
import org.apache.log4j.Logger;


/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */

@Service
@Profile("graphics")
@EnableConfig
public class SceneRenderer {
    private static final Logger logger = Logger.getLogger(SceneRenderer.class);

    private SceneHolder sceneHolder;
    private ComponentRenderer renderer;
    private GBufferManager gBufferManager;
    private CameraHolder cameraHolder;
    private GBufferFramebuffer framebuffer;

    public SceneRenderer(SceneHolder sceneHolder, ComponentRenderer renderer, GBufferManager gBufferManager, CameraHolder cameraHolder) {
        this.sceneHolder = sceneHolder;
        this.renderer = renderer;
        this.gBufferManager = gBufferManager;
        this.cameraHolder = cameraHolder;
    }

    public void init() {
        logger.info("Initializing scene renderer...");
        logger.info("Initializing component renderer...");
        renderer.init();
        logger.info("Setting up scene renderer framebuffers.");
        setupFramebuffer();
        logger.info("Scene renderer initialized.");
    }

    public void update() {
        if (sceneHolder.getScene() != null && cameraHolder.getCamera() != null) {
            initRendering();
            render(sceneHolder.getScene(), false);
        } else {
            //TODO uncomment
            //logger.warn("Unable to render the scene. Scene = null or Camera = null.");
        }//TODO do stuff
    }

    private void initRendering() {
        framebuffer.bindDraw();
        framebuffer.clear();
        renderer.initRendering();
    }

    public void render(Component component, boolean dirty) {
        renderer.enterChildren();
        boolean componentDirty = renderer.renderComponentAndCheckIfDirty(component, dirty);
        component.forEachChildren(c -> render(c, componentDirty));
        renderer.leaveChildren();
    }


    public void destroy() {
        renderer.destroy();
        logger.info("Component renderers destroyed.");
        //...
        logger.info("Scene renderer destroyed.");
    }

    private void setupFramebuffer() {
        this.gBufferManager.initialize();
        this.framebuffer = new GBufferFramebuffer(gBufferManager.getGBuffer());
    }

}
