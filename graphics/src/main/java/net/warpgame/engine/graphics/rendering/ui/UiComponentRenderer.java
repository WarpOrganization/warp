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
    private Matrix3x2Stack stack;
    private UiProgramManager uiProgramManager;
    private QuadMesh quad;

    public UiComponentRenderer(Config config, Matrix3x2Stack stack, UiProgramManager uiProgramManager) {
        this.display = config.getValue("graphics.display");
        this.stack = stack;
        this.uiProgramManager = uiProgramManager;
    }

    public void renderComponent(Component component){
        quad = new QuadMesh();//TODO no need to create new quad every frame
        RectTransformProperty rectTransform = component.getProperty(Property.getTypeId(RectTransformProperty.class));
        ImageProperty image = component.getProperty(Property.getTypeId(ImageProperty.class));
        uiProgramManager.getUiProgram().useMatrix(getTransformationMatrix(rectTransform));
        uiProgramManager.getUiProgram().useTexture(image.getTexture());
        quad.draw();
    }

    private Matrix3x2f getTransformationMatrix(RectTransformProperty rectTransformProperty){
        Matrix3x2f res = new Matrix3x2f();
        Vector2f position = rectTransformProperty.getPosition();
        res.translate(position.x * 2/ display.getWidth()-1, position.y *2/display.getHeight()-1);
        res.rotate(rectTransformProperty.getRotation());
        res.scale(rectTransformProperty.getScale());
        return res;
    }
}
