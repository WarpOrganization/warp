package pl.warp.ide.controller.component;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.game.scene.GameComponent;

import java.text.DecimalFormat;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 12
 */
public class ComponentDescriptor {
    private GridPane descriptorPane;
    private int index;

    public ComponentDescriptor(GridPane descriptorPane) {
        this.descriptorPane = descriptorPane;
    }

    public void load(GameComponent component) {
        loadName(component);
        loadPosition(component);
        loadCamera(component);
    }

    public void unload() {
        index = 0;
        descriptorPane.getChildren().clear();
    }

    private void loadName(GameComponent component) {
        component.<NameProperty>getPropertyIfExists(NameProperty.NAME_PROPERTY_NAME)
                .ifPresent(name -> {
                    descriptorPane.add(new Label("Name:"), 0, index);
                    descriptorPane.add(new Label(name.getComponentName()), 1, index);
                    index++;
                });
    }

    private DecimalFormat coordinateFormat = new DecimalFormat("#.#");
    private Vector3f dest = new Vector3f();
    private void loadPosition(GameComponent component) {
        component.<TransformProperty>getPropertyIfExists(TransformProperty.TRANSFORM_PROPERTY_NAME)
                .ifPresent(transform -> {
                    Vector3f pos = Transforms.getAbsolutePosition(component, dest);
                    descriptorPane.add(new Label("Position:"), 0, index);
                    descriptorPane.add(new Label(formatPosition(pos)), 1, index);
                    index++;
                });
    }

    private String formatPosition(Vector3f pos) {
        return coordinateFormat.format(pos.x) + "; " + coordinateFormat.format(pos.y) + "; " + coordinateFormat.format(pos.z);
    }

    private void loadCamera(GameComponent component) {
        component.<CameraProperty>getPropertyIfExists(CameraProperty.CAMERA_PROPERTY_NAME)
                .ifPresent(camera -> {
                    String cameraClassName = camera.getCamera().getClass().getSimpleName();
                    descriptorPane.add(new Label("Camera:"), 0, index);
                    descriptorPane.add(new Label(cameraClassName), 1, index);
                    index++;
                });
    }
}
