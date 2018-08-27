package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.framebuffer.TextureFramebuffer;
import net.warpgame.engine.graphics.rendering.ui.image.program.ImageProgramManager;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceAlbedoHolder;
import net.warpgame.engine.graphics.rendering.ui.text.program.TextProgramManager;
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
    private ImageProgramManager imageProgramManager;
    private TextProgramManager textProgramManager;

    private List<Component> canvas;

    public UiRenderer(ScreenspaceAlbedoHolder screenspaceAlbedoHolder, UiComponentRenderer uiComponentRenderer, ImageProgramManager imageProgramManager, TextProgramManager textProgramManager) {
        this.screenspaceAlbedoHolder = screenspaceAlbedoHolder;
        this.uiComponentRenderer = uiComponentRenderer;
        this.imageProgramManager = imageProgramManager;
        this.textProgramManager = textProgramManager;
        this.canvas = new ArrayList<>();
    }

    public void init(){
        this.destinationFramebuffer = screenspaceAlbedoHolder.getAlbedoTextureFramebuffer();
        uiComponentRenderer.init();
    }

    public void update() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        prepareFramebuffer();
        prepareProgram();
        canvas.forEach(this::render);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void prepareFramebuffer() {
        destinationFramebuffer.bindDraw();
    }

    private void prepareProgram() {
        imageProgramManager.update();
        textProgramManager.update();
    }

    private void render(Component canvas) {
        uiComponentRenderer.renderComponent(canvas);
    }

    public void addCanvas(CanvasProperty canvasProperty){
        canvas.add(canvasProperty.getOwner());
    }

    public void destroy() {
        uiComponentRenderer.destroy();
        imageProgramManager.destroy();
        textProgramManager.destroy();
    }
}
