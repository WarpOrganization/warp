package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.mesh.shapes.CharQuadMesh;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.rendering.ui.image.program.ImageProgramManager;
import net.warpgame.engine.graphics.rendering.ui.image.ImageProperty;
import net.warpgame.engine.graphics.rendering.ui.text.TextProperty;
import net.warpgame.engine.graphics.rendering.ui.text.program.TextProgramManager;
import net.warpgame.engine.graphics.resource.font.FontManager;
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
    private ImageProgramManager imageProgramManager;
    private TextProgramManager textProgramManager;
    private FontManager fontManager;
    private CharQuadMesh charQuad;
    private QuadMesh quad;

    public UiComponentRenderer(Config config, ImageProgramManager imageProgramManager, TextProgramManager textProgramManager, FontManager fontManager) {
        this.display = config.getValue("graphics.display");
        this.stack = new Matrix3x2fStack(config.getValue("graphics.rendering.ui.stackSize"));
        this.imageProgramManager = imageProgramManager;
        this.textProgramManager = textProgramManager;
        this.fontManager = fontManager;
    }

    public void init(){
        quad = new QuadMesh();
        charQuad = new CharQuadMesh();
        imageProgramManager.init();
        textProgramManager.init();
        Matrix4fc projectionMatrix = new Matrix4f().setOrtho2D(0, (float) display.getWidth(), 0, (float) display.getHeight());
        imageProgramManager.setProjectionMatrix(projectionMatrix);
        textProgramManager.setProjectionMatrix(projectionMatrix);
    }

    public void renderComponent(Component component){
        RectTransformProperty rectTransform = component.getPropertyOrNull(Property.getTypeId(RectTransformProperty.class));
        if(rectTransform != null) {
            stack.pushMatrix();
            getTransformationMatrix(rectTransform, stack);

            ImageProperty image = component.getPropertyOrNull(Property.getTypeId(ImageProperty.class));
            if(image != null) {
                renderImage(rectTransform, image);
            }

            TextProperty text = component.getPropertyOrNull(Property.getTypeId(TextProperty.class));
            if(text != null){
                renderText(text);
            }

            component.forEachChildren(this::renderComponent);
            stack.popMatrix();
        }
    }

    private void renderText(TextProperty text) {

    }

    private void renderImage(RectTransformProperty rectTransform, ImageProperty image) {
        Matrix3x2fc fullTransformation = stack.scale((float) rectTransform.getWidth() / 2, (float) rectTransform.getHeight() / 2, new Matrix3x2f());
        imageProgramManager.prepareProgram(fullTransformation, image.getTexture());
        quad.draw();
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
