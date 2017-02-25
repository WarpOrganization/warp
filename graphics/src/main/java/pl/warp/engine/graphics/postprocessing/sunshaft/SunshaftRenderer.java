package pl.warp.engine.graphics.postprocessing.sunshaft;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.ComponentRenderer;
import pl.warp.engine.graphics.GLErrors;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.SceneRenderer;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.DepthTextureFramebuffer;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.postprocessing.WeightedTexture2D;
import pl.warp.engine.graphics.shader.program.postprocessing.depthtest.DepthTestProgram;
import pl.warp.engine.graphics.shader.program.postprocessing.sunshaft.SunshaftProgram;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 21
 */
public class SunshaftRenderer implements Flow<Texture2D, WeightedTexture2D> {

    private WeightedTexture2D output;

    private SceneRenderer sceneRenderer;
    private SunshaftSource sunshaftSource;
    private RenderingConfig config;
    private ComponentRenderer renderer;
    private Camera camera;

    private DepthTextureFramebuffer componentRenderFramebuffer;
    private Texture2D componentRenderTexture;
    private Texture2D componentDepthTexture;

    private DepthTestProgram depthTestProgram;

    private SunshaftProgram sunshaftProgram;
    private TextureFramebuffer sunshaftFramebuffer;
    private Texture2D sunshaftTexture;

    private Quad quad;

    public SunshaftRenderer(SceneRenderer sceneRenderer, SunshaftSource sunshaftSource, RenderingConfig config, ComponentRenderer renderer, Camera camera) {
        this.sceneRenderer = sceneRenderer;
        this.sunshaftSource = sunshaftSource;
        this.config = config;
        this.renderer = renderer;
        this.camera = camera;
    }

    @Override
    public void update(int delta) {
        if (sunshaftSource.getSource() != null) {
            renderer.enterChildren();
            componentRenderFramebuffer.bindDraw();
            renderComponent();
            Vector2f componentPos = getComponentScreenPos();
            occlude();
            renderSunshaft(componentPos);
            renderer.leaveChildren();
        }
    }

    private void renderComponent() {
        prepare();
        renderer.renderComponent(sunshaftSource.getSource());

    }

    private void prepare() {
        applyTransform(sunshaftSource.getSource());
    }

    private void applyTransform(Component component) {
        if (component.hasParent()) applyTransform(component.getParent());
        renderer.prepareComponent(component);
    }

    private void occlude() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        depthTestProgram.use();
        depthTestProgram.useComponentDepthSampler(componentDepthTexture);
        depthTestProgram.useSceneDepthSampler(sceneRenderer.getOutput());
        quad.draw();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void renderSunshaft(Vector2f sunshaftCenter) {
        sunshaftFramebuffer.bindDraw();
        sunshaftProgram.use();
        sunshaftProgram.useDiffuseTexture(componentRenderTexture);
        sunshaftProgram.useSunshaftSource(sunshaftSource);
        sunshaftProgram.useCenter(sunshaftCenter);
        quad.draw();
    }


    @Override
    public void init() {
        GLErrors.checkOGLErrors();
        this.depthTestProgram = new DepthTestProgram();
        GLErrors.checkOGLErrors();
        this.sunshaftProgram = new SunshaftProgram();
        GLErrors.checkOGLErrors();
        Display display = config.getDisplay();
        this.componentRenderTexture = new Texture2D(display.getWidth(), display.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        GLErrors.checkOGLErrors();
        this.componentDepthTexture = new Texture2D(display.getWidth(), display.getHeight(), GL11.GL_DEPTH_COMPONENT, GL11.GL_DEPTH_COMPONENT, false, null);
        this.componentRenderFramebuffer = new DepthTextureFramebuffer(componentRenderTexture, componentDepthTexture);
        GLErrors.checkOGLErrors();
        this.sunshaftTexture = new Texture2D(display.getWidth(), display.getHeight(), GL30.GL_RGB32F, GL11.GL_RGB, false, null);
        this.sunshaftFramebuffer = new TextureFramebuffer(sunshaftTexture);
        GLErrors.checkOGLErrors();
        this.quad = new Quad();
        this.output = new WeightedTexture2D(sunshaftTexture, 1.0f);
        GLErrors.checkOGLErrors();
    }

    @Override
    public void destroy() {
        componentRenderFramebuffer.delete();
        sunshaftFramebuffer.delete();
        quad.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        componentRenderFramebuffer.resize(newWidth, newHeight);
        sunshaftFramebuffer.resize(newWidth, newHeight);
    }

    @Override
    public WeightedTexture2D getOutput() {
        return output;
    }

    @Override
    public void setInput(Texture2D input) {
        //ignore input
    }

    private Vector4f vector4 = new Vector4f();
    private Vector2f vector2 = new Vector2f();

    public Vector2f getComponentScreenPos() {
        vector4.set(0, 0, 0, 1);
        vector4.mul(renderer.getMatrixStack().topMatrix());
        vector4.mul(camera.getCameraMatrix());
        vector4.mul(camera.getProjectionMatrix().getMatrix());
        vector2.set(vector4.x, vector4.y);
        return vector2;
    }
}
