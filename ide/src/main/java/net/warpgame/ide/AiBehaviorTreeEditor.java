package net.warpgame.ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.warpgame.engine.core.runtime.EngineRuntime;

public class AiBehaviorTreeEditor extends Application {

    @Override
    public void start(Stage mainStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("mainStage.fxml"));
        Parent root = fxmlLoader.load();
        mainStage.setTitle("AI Behavior Tree Editor");
        mainStage.setScene(new Scene(root));
        mainStage.show();
        MainStage controller = fxmlLoader.getController();
        controller.chooseFile();
    }

    public static void start(EngineRuntime engineRuntime)
    {
        launch();
    }

}
