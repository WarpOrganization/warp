package pl.warp.ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.ide.controller.IDEController;
import pl.warp.ide.input.JavaFxInput;
import pl.warp.ide.scene.tree.ComponentLook;
import pl.warp.ide.scene.tree.SceneTreeLoader;
import pl.warp.ide.scene.tree.look.ComponentTypeLook;
import pl.warp.ide.scene.tree.look.CustomLookRepository;
import pl.warp.ide.scene.tree.look.DefaultNameSupplier;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 11
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomLookRepository descriptorRepository = loadCustomLookRepository();
        IDEController controller = new IDEController(new SceneTreeLoader(descriptorRepository), new JavaFxInput());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "idewindow.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Warp Engine IDE");
        primaryStage.show();
    }

    private CustomLookRepository loadCustomLookRepository() {
        ComponentLook sceneLook = new ComponentLook(
                new DefaultNameSupplier("Scene"),
                () -> getImage("icons\\Scene.png"));
        ComponentLook cameraLook = new ComponentLook(
                new DefaultNameSupplier("Camera"),
                () -> getImage("icons\\Camera.png"));
        ComponentLook drawableLook = new ComponentLook(
                new DefaultNameSupplier("Drawable Component"),
                () -> getImage("icons\\Drawable.png"));
        ComponentLook componentLook = new ComponentLook(
                new DefaultNameSupplier("Component"),
                () -> getImage("icons\\Component.png"));
        return new CustomLookRepository(
                new ComponentTypeLook(this::isScene, sceneLook),
                new ComponentTypeLook(this::isCamera, cameraLook),
                new ComponentTypeLook(this::isDrawableModel, drawableLook),
                new ComponentTypeLook(c -> true, componentLook));
    }

    private Boolean isScene(Component component) {
        return component instanceof pl.warp.engine.core.scene.Scene;
    }

    private Boolean isCamera(Component component) {
        return Camera.class.isAssignableFrom(component.getClass());
    }

    private boolean isDrawableModel(Component component) {
        return component.hasProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME);
    }

    private ImageView getImage(String name) {
        return new ImageView(new Image(Launcher.class.getResourceAsStream(name)));
    }

    public static void main(String... args) throws Exception {
        Application.launch(Launcher.class, args);
    }
}
