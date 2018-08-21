package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.framebuffer.TextureFramebuffer;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.rendering.ui.program.UiProgram;
import net.warpgame.engine.graphics.rendering.ui.program.UiProgramManager;
import net.warpgame.engine.graphics.rendering.ui.property.CanvasProperty;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceAlbedoHolder;
import net.warpgame.engine.graphics.window.Display;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("graphics")
public class UiRenderer {
    private static final Logger logger = LoggerFactory.getLogger(UiRenderer.class);

    private ScreenspaceAlbedoHolder screenspaceAlbedoHolder;
    private TextureFramebuffer destinationFramebuffer;

    private UiComponentRenderer uiComponentRenderer;
    private UiProgram uiProgram;
    private UiProgramManager uiProgramManager;

    private List<Component> canvas;

    public UiRenderer(ScreenspaceAlbedoHolder screenspaceAlbedoHolder, UiComponentRenderer uiComponentRenderer, UiProgramManager uiProgramManager) {
        this.screenspaceAlbedoHolder = screenspaceAlbedoHolder;
        this.uiComponentRenderer = uiComponentRenderer;
        this.uiProgramManager = uiProgramManager;
        this.canvas = new ArrayList<>();
    }

    public void init(){
        try {
            uiProgram = new UiProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile ui rendering program");
        }
        this.destinationFramebuffer = screenspaceAlbedoHolder.getAlbedoTextureFramebuffer();
        uiProgramManager.setUiProgram(uiProgram);
        uiComponentRenderer.init();
    }

    public void update() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        prepareFramebuffer();
        prepareProgram();
        canvas.forEach(this::render);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);

    }

    private void prepareFramebuffer() {
        destinationFramebuffer.bindDraw();
    }

    private void prepareProgram() {
        uiProgram.use();
    }

    private void render(Component canvas) {
        uiComponentRenderer.renderComponent(canvas);
    }

    public void addCanvas(CanvasProperty canvasProperty){
        canvas.add(canvasProperty.getOwner());
    }

    public void destroy() {
        uiComponentRenderer.destroy();
    }
}
