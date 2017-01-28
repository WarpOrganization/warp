package pl.warp.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.window.Display;


/**
 * @author Jaca777
 *         Created 2017-01-21 at 21
 */
public class TestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dialog<RenderingConfig> dialog = new Dialog<>();
        dialog.setTitle("Warp Game Engine");
        dialog.setHeaderText("Graphics settings");

        ButtonType runButtonType = new ButtonType("Run", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(runButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Slider renderingSamples = new Slider(1, 8, 4);
        setupSlider(renderingSamples);

        Slider bloomIters = new Slider(0, 6, 5);
        setupSlider(bloomIters);

        Slider bloomLevel = new Slider(0.0, 2.0, 1.0);
        bloomLevel.setShowTickMarks(true);
        bloomLevel.setShowTickLabels(true);
        bloomLevel.setMajorTickUnit(1.0);

        Slider exposure = new Slider(0.0, 8.0, 1.85);
        exposure.setShowTickMarks(true);
        exposure.setShowTickLabels(true);
        exposure.setMajorTickUnit(2.0);

        TextField displayWidth = new TextField("1280");
        TextField displayHeight = new TextField("920");

        grid.add(new Label("Rendering samples:"), 0, 0);
        grid.add(renderingSamples, 1, 0);
        grid.add(new Label("Affects performance"), 2, 0);
        grid.add(new Label("Bloom iterations:"), 0, 1);
        grid.add(bloomIters, 1, 1);
        grid.add(new Label("Affects performance"), 2, 1);
        grid.add(new Label("Bloom level:"), 0, 2);
        grid.add(bloomLevel, 1, 2);
        grid.add(new Label("Exposure:"), 0, 3);
        grid.add(exposure, 1, 3);
        grid.add(new Label("Display width:"), 0, 4);
        grid.add(displayWidth, 1, 4);
        grid.add(new Label("Affects performance"), 2, 4);
        grid.add(new Label("Display height:"), 0, 5);
        grid.add(displayHeight, 1, 5);
        grid.add(new Label("Affects performance"), 2, 5);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == runButtonType) {
                int width = Integer.parseInt(displayWidth.getText());
                int height = Integer.parseInt(displayHeight.getText());
                RenderingConfig config = new RenderingConfig(60, new Display(false, width, height));
                config.setBloomIterations((int) bloomIters.getValue());
                config.setBloomLevel((float) bloomLevel.getValue());
                config.setExposure((float) exposure.getValue());
                config.setRenderingSamples((int) renderingSamples.getValue());
                return config;
            } else return null;
        });

        dialog.getDialogPane().setContent(grid);

        RenderingConfig config = dialog.showAndWait().orElseThrow(RuntimeException::new);
        Test.runTest(config);

    }

    private void setupSlider(Slider bloomIters) {
        bloomIters.setShowTickMarks(true);
        bloomIters.setShowTickLabels(true);
        bloomIters.setMajorTickUnit(1.0);
        bloomIters.setMinorTickCount(0);
        bloomIters.setSnapToTicks(true);
    }

    public static void main(String... args) {
        Application.launch(TestApp.class, args);
    }
}