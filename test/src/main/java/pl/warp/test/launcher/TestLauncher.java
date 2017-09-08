package pl.warp.test.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.warp.launcher.BasicUpdater;
import pl.warp.launcher.UpdateStatus;

/**
 * @author Hubertus
 *         Created 10.03.17
 */
public class TestLauncher extends Application{

    public static void main(String... args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "launcherWindow.fxml"));
        LauncherController controller = new LauncherController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Warp Demo Launcher");
        primaryStage.show();
        BasicUpdater basicUpdater = new BasicUpdater();
        UpdateStatus status = new UpdateStatus();
        //basicUpdater.onUpdate(new PathData("testData","."),false, status);
    }
}
