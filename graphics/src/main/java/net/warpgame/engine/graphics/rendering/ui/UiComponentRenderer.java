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
import org.joml.Vector2f;

/**
 * @author MarconZet
 * Created 16.08.2018
 */
@Service
@Profile("graphics")
public class UiComponentRenderer {

    private Display display;
    private UiMatrixStack stack;
    private UiProgramManager uiProgramManager;
    private QuadMesh quad;

    public UiComponentRenderer(Config config, UiMatrixStack stack, UiProgramManager uiProgramManager) {
        this.display = config.getValue("graphics.display");
        this.stack = stack;
        this.uiProgramManager = uiProgramManager;
    }

    public void init(){
        quad = new QuadMesh();
    }

    public void renderComponent(Component component){
        RectTransformProperty rectTransform = component.getPropertyOrNull(Property.getTypeId(RectTransformProperty.class));
        if(rectTransform != null) {
            ImageProperty image = component.getPropertyOrNull(Property.getTypeId(ImageProperty.class));
            if(image != null) {
                uiProgramManager.getUiProgram().useMatrix(getTransformationMatrix(rectTransform));
                uiProgramManager.getUiProgram().useTexture(image.getTexture());
                quad.draw();
            }
            component.forEachChildren(this::renderComponent);
        }
    }

    private Matrix3x2f getTransformationMatrix(RectTransformProperty rectTransform){
        Matrix3x2f res = new Matrix3x2f();
        Vector2f position = rectTransform.getPosition();
        res.translate(position.x * 2/ display.getWidth()-1, position.y *2/display.getHeight()-1);
        res.rotate(rectTransform.getRotation());
        res.scale(rectTransform.getScale());
        res.scale((float) rectTransform.getWidth() / display.getWidth(), (float) rectTransform.getHeight() / display.getHeight());
        return res;
    }

    public void destroy() {
        quad.destroy();
    }
}
