package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.rendering.ui.program.UiProgramManager;
import net.warpgame.engine.graphics.rendering.ui.property.ImageProperty;
import net.warpgame.engine.graphics.rendering.ui.property.RectTransformProperty;
import net.warpgame.engine.graphics.window.Display;
import org.joml.*;

/**
 * @author MarconZet
 * Created 16.08.2018
 */
@Service
@Profile("graphics")
public class UiComponentRenderer {

    private Display display;
    private Matrix3x2fStack stack;
    private UiProgramManager uiProgramManager;
    private QuadMesh quad;

    public UiComponentRenderer(Config config, UiProgramManager uiProgramManager) {
        this.display = config.getValue("graphics.display");
        this.stack = new Matrix3x2fStack(config.getValue("graphics.rendering.ui.stackSize"));
        this.uiProgramManager = uiProgramManager;
    }

    public void init(){
        quad = new QuadMesh();
        uiProgramManager.init();
        Matrix4fc projectionMatrix = new Matrix4f().setOrtho2D(0, (float) display.getWidth(), 0, (float) display.getHeight());
        uiProgramManager.setProjectionMatrix(projectionMatrix);
    }

    public void renderComponent(Component component){
        RectTransformProperty rectTransform = component.getPropertyOrNull(Property.getTypeId(RectTransformProperty.class));
        if(rectTransform != null) {
            stack.pushMatrix();
            getTransformationMatrix(rectTransform, stack);
            ImageProperty image = component.getPropertyOrNull(Property.getTypeId(ImageProperty.class));
            if(image != null) {
                Matrix3x2fc fullTransformation = stack.scale((float) rectTransform.getWidth() / 2, (float) rectTransform.getHeight() / 2, new Matrix3x2f());
                uiProgramManager.prepareProgram(fullTransformation, image.getTexture());
                quad.draw();
            }
            component.forEachChildren(this::renderComponent);
            stack.popMatrix();
        }
    }

    private void getTransformationMatrix(RectTransformProperty rectTransform, Matrix3x2f destination){
        destination.translate(rectTransform.getPosition());
        destination.rotate(rectTransform.getRotation());
        destination.scale(rectTransform.getScale());
    }

    public void destroy() {
        quad.destroy();
    }
}
