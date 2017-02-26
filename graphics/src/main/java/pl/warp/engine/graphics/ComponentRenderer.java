package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 21
 */
public class ComponentRenderer {

    private Renderer[] renderers;
    private MatrixStack matrixStack = new MatrixStack();

    public ComponentRenderer(Renderer[] renderers) {
        this.renderers = renderers;
    }

    public void init() {
        for (Renderer renderer : renderers)
            renderer.init();
    }

    public void initRendering(int delta) {
        for (Renderer renderer : renderers)
            renderer.initRendering(delta);
    }

    public void prepareComponent(Component component) {
        if (component.hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransformations(component);
    }


    private void applyTransformations(Component component) {
        TransformProperty property = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        applyTranslation(property);
        applyScale(property);
        applyRotation(property);
    }

    private void applyScale(TransformProperty scale) {
        matrixStack.scale(scale.getScale());
    }

    private void applyRotation(TransformProperty rotation) {
        matrixStack.rotate(rotation.getRotation());
    }

    private void applyTranslation(TransformProperty translation) {
        matrixStack.translate(translation.getTranslation());
    }

    public void renderComponent(Component component) {
        if (component.hasEnabledProperty(CustomRendererProperty.CUSTOM_RENDERER_PROPERTY_NAME)) {
            CustomRendererProperty rendererProperty = component.getProperty(CustomRendererProperty.CUSTOM_RENDERER_PROPERTY_NAME);
            CustomRenderer renderer = rendererProperty.getRenderer();
            if(renderer.isEnabled()) renderer.render(component, matrixStack);
        }
        for (Renderer renderer : renderers) {
            renderer.render(component, matrixStack);
        }
    }

    public void enterChildren() {
        matrixStack.push();
    }

    public void leaveChildren() {
        matrixStack.pop();
    }

    public void destroy() {
        for (Renderer renderer : renderers)
            renderer.destroy();
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }
}
