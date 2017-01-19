package pl.warp.ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.warp.ide.controller.IDEController;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 11
 */
public class Launcher extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        IDEController controller = new IDEController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "idewindow.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Warp Engine IDE");
        primaryStage.show();
    }

    public static void main(String... args) throws Exception {
        Application.launch(Launcher.class, args);
    }
}
