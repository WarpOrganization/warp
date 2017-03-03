package pl.warp.ide.controller.componenteditor;

import javafx.scene.control.TreeView;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.ide.controller.camera.ComponentCameraScript;
import pl.warp.ide.controller.positioner.ComponentPositioner;
import pl.warp.ide.controller.sceneeditor.IDEComponentProperty;

/**
 * @author Jaca777
 *         Created 2017-02-19 at 17
 */
public class ComponentEditorController {
    private TreeView<Property> propertiesTree;
    private PropertyEditor propertyEditor;
    private GameComponent mainComponent;
    private GameComponent cameraParent;
    private ComponentPositioner componentPositioner;

    public ComponentEditorController(TreeView<Property> propertiesTree, PropertyEditor propertyEditor, ComponentPositioner positioner) {
        this.propertiesTree = propertiesTree;
        this.propertyEditor = propertyEditor;
    }

    public void suspend() {
        if (cameraParent != null) cameraParent.destroy();
    }

    public void activateWith(GameComponent component) {
        this.mainComponent = component;
        centerCameraOn(component);
    }

    protected void centerCameraOn(GameComponent center) {
        if (cameraParent != null) cameraParent.destroy();
        GameContext context = center.getContext();
        cameraParent = new GameSceneComponent(center);
        GameComponent cameraComponent = new GameSceneComponent(cameraParent);
        TransformProperty cameraTransform = new TransformProperty();
        cameraTransform.move(new Vector3f(0, 0, 20.0f));
        cameraComponent.addProperty(cameraTransform);
        cameraComponent.addProperty(new IDEComponentProperty());
        QuaternionCamera camera = new QuaternionCamera(cameraComponent, cameraTransform, context.getCamera().getProjectionMatrix());
        cameraComponent.addProperty(new CameraProperty(camera));
        new ComponentCameraScript(cameraComponent, camera, cameraParent);
        context.getGraphics().setMainViewCamera(camera);
    }
}
