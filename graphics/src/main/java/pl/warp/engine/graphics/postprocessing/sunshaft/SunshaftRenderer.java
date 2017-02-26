package pl.warp.engine.graphics.postprocessing.sunshaft;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.ComponentRenderer;
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

    private TextureFramebuffer occludedFramebuffer;
    private Texture2D occludedComponent;

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
            componentRenderFramebuffer.bindDraw();
            componentRenderFramebuffer.clean();
            renderer.enterChildren();
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
        quad.bind();
        this.occludedFramebuffer.bindDraw();
        this.occludedFramebuffer.clean();
        depthTestProgram.use();
        depthTestProgram.useSceneDepthSampler(sceneRenderer.getDepthTexture());
        depthTestProgram.useComponentDepthSampler(componentDepthTexture);
        depthTestProgram.useComponentTexture(componentRenderTexture);
        quad.draw();
    }

    private void renderSunshaft(Vector2f sunshaftCenter) {
        quad.bind();
        sunshaftFramebuffer.bindDraw();
        sunshaftFramebuffer.clean();
        sunshaftProgram.use();
        sunshaftProgram.useDiffuseTexture(occludedComponent);
        sunshaftProgram.useSunshaftSource(sunshaftSource);
        sunshaftProgram.useCenter(sunshaftCenter);
        quad.draw();
    }


    @Override
    public void init() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);

        this.depthTestProgram = new DepthTestProgram();
        this.sunshaftProgram = new SunshaftProgram();
        Display display = config.getDisplay();

        this.occludedComponent = new Texture2D(display.getWidth() / 2, display.getHeight() / 2, GL30.GL_RGBA32F, GL11.GL_RGBA, false, null);
        this.occludedFramebuffer = new TextureFramebuffer(occludedComponent);

        this.componentRenderTexture = new Texture2D(display.getWidth(), display.getHeight(), GL30.GL_RGBA32F, GL11.GL_RGBA, false, null);
        this.componentDepthTexture = new Texture2D(display.getWidth(), display.getHeight(), GL30.GL_DEPTH_COMPONENT32F, GL11.GL_DEPTH_COMPONENT, false, null);
        this.componentRenderFramebuffer = new DepthTextureFramebuffer(componentRenderTexture, componentDepthTexture);

        this.sunshaftTexture = new Texture2D(display.getWidth() / 2, display.getHeight() / 2, GL30.GL_RGBA32F, GL11.GL_RGBA, false, null);
        this.sunshaftFramebuffer = new TextureFramebuffer(sunshaftTexture);
        this.quad = new Quad();
        this.output = new WeightedTexture2D(sunshaftTexture, 1.0f, 1.0f);
    }

    @Override
    public void destroy() {
        componentRenderFramebuffer.delete();
        sunshaftFramebuffer.delete();
        quad.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        componentRenderFramebuffer.resize(newWidth / 2, newHeight / 2);
        occludedFramebuffer.resize(newWidth / 2, newHeight / 2);
        sunshaftFramebuffer.resize(newWidth / 2, newHeight / 2);
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
        vector2.set(vector4.x / vector4.w, vector4.y / vector4.w);
        return vector2;
    }
}
