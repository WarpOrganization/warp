package net.warpgame.engine.graphics.rendering.antialiasing.smaa;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.framebuffer.ScreenFramebuffer;
import net.warpgame.engine.graphics.framebuffer.TextureFramebuffer;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.program.BlendPassProgram;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.program.EdgePassProgram;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.program.NeighbourhoodPassProgram;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceAlbedoHolder;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.graphics.window.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 * Created 2018-01-13 at 16
 */
@Service
@Profile("graphics")
public class SMAARenderer {

    private SMAAResourceLoader resourceLoader;
    private Display display;
    private ScreenspaceAlbedoHolder screenspaceAlbedoHolder;
    private SMAAShadingSettings smaaShadingSettings;

    private Texture2D searchTex;
    private Texture2D areaTex;

    private Texture2D edgeTex;
    private Texture2D blendTex;

    private TextureFramebuffer edgeFramebuffer;
    private TextureFramebuffer blendFramebuffer;
    private ScreenFramebuffer destinationFramebuffer;

    private BlendPassProgram blendPassProgram;
    private EdgePassProgram edgePassProgram;
    private NeighbourhoodPassProgram neighbourhoodPassProgram;

    private QuadMesh quadMesh;


    public SMAARenderer(
            SMAAResourceLoader resourceLoader,
            ScreenspaceAlbedoHolder screenspaceAlbedoHolder,
            SMAAShadingSettings smaaShadingSettings,
            Config config,
            ScreenFramebuffer destinationFramebuffer) {
        this.resourceLoader = resourceLoader;
        this.display = config.getValue("graphics.display");
        this.screenspaceAlbedoHolder = screenspaceAlbedoHolder;
        this.smaaShadingSettings = smaaShadingSettings;
        this.destinationFramebuffer = destinationFramebuffer;
    }

    public void initialize() {
        initTextures();
        initFramebuffers();
        initShaders();
        this.quadMesh = new QuadMesh();
    }

    private void initTextures() {
        this.edgeTex = new Texture2D(
                display.getWidth(),
                display.getHeight(),
                GL30.GL_RG8,
                GL30.GL_RG,
                false,
                null
        );
        this.blendTex = new Texture2D(
                display.getWidth(),
                display.getHeight(),
                GL11.GL_RGBA8,
                GL11.GL_RGBA,
                false,
                null
        );
        setParams(this.edgeTex);
        setParams(this.blendTex);
        this.searchTex = resourceLoader.loadSearchTexture();
        this.areaTex = resourceLoader.loadAreaTexture();
    }

    private void setParams(Texture2D tex) {
        tex.bind();
        tex.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        tex.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        tex.setParameter(GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        tex.setParameter(GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

    }

    private void initFramebuffers() {
        this.edgeFramebuffer = new TextureFramebuffer(edgeTex);
        this.blendFramebuffer = new TextureFramebuffer(blendTex);
    }

    private void initShaders() {
        this.blendPassProgram = new BlendPassProgram(smaaShadingSettings);
        this.edgePassProgram = new EdgePassProgram(smaaShadingSettings);
        this.neighbourhoodPassProgram = new NeighbourhoodPassProgram(smaaShadingSettings);
    }

    public void update() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Texture2D albedoTex = screenspaceAlbedoHolder.getAlbedoTex();
        drawEdgePass(albedoTex);
        drawBlendPass();
        drawNeighbourhoodPass(albedoTex);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void drawEdgePass(Texture2D albedoTex) {
        edgeFramebuffer.bindDraw();
        edgeFramebuffer.clear();
        edgePassProgram.use();
        edgePassProgram.useAlbedoTexture(albedoTex);
        quadMesh.draw();
        edgeTex.genMipmap();
    }

    private void drawBlendPass() {
        blendFramebuffer.bindDraw();
        blendFramebuffer.clear();
        blendPassProgram.use();
        blendPassProgram.useTextures(edgeTex, areaTex, searchTex);
        quadMesh.draw();
        blendTex.genMipmap();
    }

    private void drawNeighbourhoodPass(Texture2D albedoTex) {
        destinationFramebuffer.bindDraw();
        destinationFramebuffer.clear();
        neighbourhoodPassProgram.use();
        neighbourhoodPassProgram.useTextures(albedoTex, blendTex);
        quadMesh.draw();
    }

    public void destroy() {
        blendPassProgram.delete();
        edgeFramebuffer.delete();
        edgeTex.delete();
        blendTex.delete();
        searchTex.delete();
        areaTex.delete();
        blendPassProgram.delete();
        edgePassProgram.delete();
        neighbourhoodPassProgram.delete();
    }
}
