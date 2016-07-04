package pl.warp.engine.graphics;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.properties.RotationProperty;
import pl.warp.engine.core.scene.properties.ScaleProperty;
import pl.warp.engine.core.scene.properties.TranslationProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.MultisampleFramebuffer;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.component.defaultprog.DefaultComponentProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import static org.lwjgl.opengl.GL30.GL_RGBA32F;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class SceneRenderer implements Source<MultisampleTexture2D> {

    private Scene scene;
    private Camera camera;
    private RenderingSettings settings;
    private MultisampleFramebuffer renderingFramebuffer;
    private MultisampleTexture2D outputTexture;
    private ComponentRenderer componentRenderer;
    private ComponentRendererProgram program;

    public SceneRenderer(Scene scene, Camera camera, RenderingSettings settings) {
        this.scene = scene;
        this.camera = camera;
        this.settings = settings;
    }

    public Scene getScene() {
        return scene;
    }

    private MatrixStack matrixStack = new MatrixStack();

    @Override
    public void update(long delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        program.use();
        program.useCamera(camera);
        render(scene.getRoot());
    }

    private void render(Component component) {
        matrixStack.push();
        applyTransformations(component);
        program.useMatrixStack(matrixStack);
        componentRenderer.render(component);
        component.getChildren().forEach(this::render);
        matrixStack.pop();
    }

    private void applyTransformations(Component component) { //Scale, then rotate, then translate
        if (component.hasProperty(ScaleProperty.SCALE_PROPERTY_NAME))
            applyScale(component.getProperty(ScaleProperty.SCALE_PROPERTY_NAME));
        if (component.hasProperty(RotationProperty.ROTATION_PROPERTY_NAME))
            applyRotation(component.getProperty(RotationProperty.ROTATION_PROPERTY_NAME));
        if (component.hasProperty(TranslationProperty.TRANSLATION_PROPERTY_NAME))
            applyTranslation(component.getProperty(TranslationProperty.TRANSLATION_PROPERTY_NAME));

    }

    private void applyScale(ScaleProperty scale) {
        matrixStack.scale(scale.getScale());
    }

    private void applyRotation(RotationProperty rotation) {
        matrixStack.rotate(rotation.getQuaternion());
    }

    private void applyTranslation(TranslationProperty translation) {
        matrixStack.translate(translation.getTranslation());
    }


    @Override
    public void init() {
        setupFramebuffer();
        this.program = new DefaultComponentProgram();
        this.componentRenderer = new ComponentRenderer(program);
    }

    private void setupFramebuffer() {
        this.outputTexture = new MultisampleTexture2D(settings.getWidth(), settings.getHeight(), GL_RGBA32F, settings.getRenderingSamples());
        this.renderingFramebuffer = new MultisampleFramebuffer(outputTexture);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MultisampleTexture2D getOutput() {
        return outputTexture;
    }
}
