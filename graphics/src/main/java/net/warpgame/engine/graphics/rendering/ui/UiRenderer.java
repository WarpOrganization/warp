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
    private UiComponentRenderer uiComponentRenderer;
    private Display display;

    private UiProgram uiProgram;
    private UiProgramManager uiProgramManager;


    private QuadMesh quad;
    private TextureFramebuffer destinationFramebuffer;
    private List<Component> canvas;

    private UiTest uiTest;

    public UiRenderer(ScreenspaceAlbedoHolder screenspaceAlbedoHolder, UiComponentRenderer uiComponentRenderer, UiTest uiTest, Config config, UiProgramManager uiProgramManager) {
        this.screenspaceAlbedoHolder = screenspaceAlbedoHolder;
        this.uiComponentRenderer = uiComponentRenderer;
        this.uiTest = uiTest;
        this.uiProgramManager = uiProgramManager;
        this.canvas = new ArrayList<>();
        this.display = config.getValue("graphics.display");
    }

    public void init(){
        this.quad = new QuadMesh();
        try {
            uiProgram = new UiProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile ui rendering program");
        }
        this.destinationFramebuffer = screenspaceAlbedoHolder.getAlbedoTextureFramebuffer();
        uiProgramManager.setUiProgram(uiProgram);
    }

    public void update() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        prepareFramebuffer();
        prepareProgram();
        canvas.forEach(this::render);
        testRender();
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

    private void testRender() {
        if (uiTest.texture2D == null || uiTest.matrix3x2f == null) {
            return;
        }
        uiProgram.useTexture(uiTest.texture2D);
        uiProgram.useMatrix(uiTest.matrix3x2f);
        quad.draw();
    }

    public void destroy() {
        quad.destroy();
    }

    public void addCanvas(CanvasProperty canvasProperty){
        canvas.add(canvasProperty.getOwner());
    }
}
