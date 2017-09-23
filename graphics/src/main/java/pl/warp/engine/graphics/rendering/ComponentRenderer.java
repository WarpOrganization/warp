package pl.warp.engine.graphics.rendering;

import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    public void init() {

    }

    public void initRendering() {

    }

    private void applyTransformations(Component component) {
        TransformProperty property = component.getProperty(TransformProperty.NAME);
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

    }

    public void enterChildren() {
        matrixStack.push();
    }

    public void leaveChildren() {
        matrixStack.pop();
    }

    public void destroy() {

    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }
}
