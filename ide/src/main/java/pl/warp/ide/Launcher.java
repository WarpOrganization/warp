package pl.warp.ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.warp.ide.controller.IDEController;
import pl.warp.ide.scene.ComponentDescriptor;
import pl.warp.ide.scene.SceneLoader;
import pl.warp.ide.scene.descriptor.CustomDescriptorRepository;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 11
 */
public class Launcher extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomDescriptorRepository descriptorRepository = loadCustomDescriptorRepository();
        IDEController controller = new IDEController(new SceneLoader(descriptorRepository));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "idewindow.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Warp Engine IDE");
        primaryStage.show();
    }

    private CustomDescriptorRepository loadCustomDescriptorRepository() {
        ComponentDescriptor scene = new ComponentDescriptor("Scene", () -> new ImageView(new Image(Launcher.class.getResourceAsStream("icons\\Scene.png"))));
        ComponentDescriptor drawable = new ComponentDescriptor("Drawable Component", () -> new ImageView(new Image(Launcher.class.getResourceAsStream("icons\\Drawable.png"))));
        ComponentDescriptor component = new ComponentDescriptor("Component", () -> new ImageView(new Image(Launcher.class.getResourceAsStream("icons\\Component.png"))));
        return new CustomDescriptorRepository(scene, drawable, component);
    }

    public static void main(String... args) throws Exception {
        Application.launch(Launcher.class, args);
    }
}
