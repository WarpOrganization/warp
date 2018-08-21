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
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;

/**
 * @author MarconZet
 * Created 16.08.2018
 */
@Service
@Profile("graphics")
public class UiComponentRenderer {

    private Display display;
    private UiMatrixStack stack;
    private Matrix4f projectionMatrix;//after multiplying quad by projectionMatrix it has size of 2x2 pixels
    private UiProgramManager uiProgramManager;
    private QuadMesh quad;

    public UiComponentRenderer(Config config, UiMatrixStack stack, UiProgramManager uiProgramManager) {
        this.display = config.getValue("graphics.display");
        this.stack = stack;
        this.uiProgramManager = uiProgramManager;
    }

    public void init(){
        quad = new QuadMesh();
        projectionMatrix = new Matrix4f().setOrtho2D(0, (float)display.getWidth(), 0, (float)display.getHeight());
        uiProgramManager.getUiProgram().useProjectionMatrix(projectionMatrix);
    }

    public void renderComponent(Component component){
        RectTransformProperty rectTransform = component.getPropertyOrNull(Property.getTypeId(RectTransformProperty.class));
        if(rectTransform != null) {
            ImageProperty image = component.getPropertyOrNull(Property.getTypeId(ImageProperty.class));
            if(image != null) {
                uiProgramManager.getUiProgram().useTransformationMatrix(getTransformationMatrix(rectTransform));
                uiProgramManager.getUiProgram().useTexture(image.getTexture());
                quad.draw();
            }
            component.forEachChildren(this::renderComponent);
        }
    }

    private Matrix3x2f getTransformationMatrix(RectTransformProperty rectTransform){
        Matrix3x2f res = new Matrix3x2f();
        res.translate(rectTransform.getPosition());
        res.rotate(rectTransform.getRotation());
        res.scale(rectTransform.getScale());
        res.scale((float) rectTransform.getWidth()/2, (float) rectTransform.getHeight()/2);
        return res;
    }

    public void destroy() {
        quad.destroy();
    }
}
