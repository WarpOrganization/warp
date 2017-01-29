package pl.warp.ide.controller.component.positioner;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.ide.engine.IDEComponentProperty;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 12
 */
public class ComponentPositioner {
    private GameContext context;
    private Camera sceneCamera;
    private Camera positioningCamera;
    private GameComponent component;
    private GameComponent rotatingComponent;
    private ComponentPositioningScript componentPositioningScript;

    public ComponentPositioner(GameContext context) {
        this.context = context;
    }

    public void position(GameComponent component) {
        if (this.component != null) throw new IllegalStateException("Can't position two components at once");
        this.component = component;
        makeTransparet(component);
        createView(component);
        runScript(component);
    }

    private void runScript(GameComponent component) {
        componentPositioningScript = new ComponentPositioningScript(component, this::finalizePositioning);
    }

    private void makeTransparet(GameComponent component) {
        component.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(m -> m.getMaterial().setTransparency(0.5f));
    }

    private void createView(GameComponent component) {
        this.sceneCamera = context.getCamera();
        this.rotatingComponent = new GameSceneComponent(component);
        rotatingComponent.addProperty(new IDEComponentProperty());
        TransformProperty rotatingTransform = new TransformProperty();
        rotatingComponent.addProperty(rotatingTransform);
        createCamera();
    }

    private void createCamera() {
        GameComponent cameraComponent = new GameSceneComponent(rotatingComponent);
        TransformProperty cameraTransform = new TransformProperty();
        cameraTransform.move(new Vector3f(0, 0, 20.0f));
        cameraComponent.addProperty(cameraTransform);
        cameraComponent.addProperty(new IDEComponentProperty());
        positioningCamera = new QuaternionCamera(cameraComponent, cameraTransform, sceneCamera.getProjectionMatrix());
        cameraComponent.addProperty(new CameraProperty(positioningCamera));
        new ComponentPositioningCameraScript(cameraComponent, positioningCamera, rotatingComponent);
        context.getGraphics().setMainViewCamera(positioningCamera);
    }

    private void finalizePositioning() {
        componentPositioningScript.stop();
        makeSolid(component);
        component = null;
        rotatingComponent.destroy();
        context.getGraphics().setMainViewCamera(sceneCamera);
    }

    private void makeSolid(GameComponent component) {
        component.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(m -> m.getMaterial().setTransparency(1.0f));
    }
}
