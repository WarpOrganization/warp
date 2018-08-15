package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.framebuffer.TextureFramebuffer;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.rendering.ui.program.UiProgram;
import net.warpgame.engine.graphics.rendering.ui.property.CanvasProperty;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceAlbedoHolder;
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

    private UiProgram uiProgram;
    private QuadMesh quad;
    private TextureFramebuffer destinationFramebuffer;

    private UiTest uiTest;
    private List<Component> canvas;

    public UiRenderer(ScreenspaceAlbedoHolder screenspaceAlbedoHolder, UiTest uiTest) {
        this.screenspaceAlbedoHolder = screenspaceAlbedoHolder;
        this.uiTest = uiTest;
        this.canvas = new ArrayList<>();
    }

    public void init(){
        this.quad = new QuadMesh();
        try {
            uiProgram = new UiProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile ui rendering program");
        }
        this.destinationFramebuffer = screenspaceAlbedoHolder.getAlbedoTextureFramebuffer();
    }

    public void update() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        prepareFramebuffer();
        prepareProgram();
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

    private void render() {


    }

    private void testRender() {
        if (uiTest.texture2D == null || uiTest.matrix3x2f == null) {
            return;
        }
        uiProgram.useTexture(uiTest.texture2D);
        uiTest.matrix3x2f.rotate((float)Math.PI/128);
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
