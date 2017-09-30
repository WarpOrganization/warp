package pl.warp.engine.graphics.rendering.scene;

import org.apache.log4j.Logger;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.framebuffer.ScreenFramebuffer;


/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */

@Service
public class SceneRenderer {
    private static final Logger logger = Logger.getLogger(SceneRenderer.class);

    private SceneHolder sceneHolder;
    private ComponentRenderer renderer;
    private ScreenFramebuffer screenFramebuffer;

    public SceneRenderer(SceneHolder sceneHolder, ComponentRenderer renderer, ScreenFramebuffer screenFramebuffer) {
        this.sceneHolder = sceneHolder;
        this.renderer = renderer;
        this.screenFramebuffer = screenFramebuffer;
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
        if(sceneHolder.getScene() != null) {
            initRendering();
            render(sceneHolder.getScene());
        } else {
            logger.warn("Unable to render the scene. Scene = null");
        }
    }


    private void initRendering() {
        screenFramebuffer.bindDraw();
        screenFramebuffer.clean();
        renderer.initRendering();
    }

    public void render(Component component) {
        renderer.enterChildren();
        renderer.renderComponent(component);
        component.forEachChildren(this::render);
        renderer.leaveChildren();
    }


    public void destroy() {
        renderer.destroy();
        logger.info("Component renderers destroyed.");
        //...
        logger.info("Scene renderer destroyed.");
    }

    private void setupFramebuffer() {
      //...
    }

    public void onResize(int newWidth, int newHeight) {
       //...
    }
}
