package pl.warp.engine.core.component;


import pl.warp.engine.core.context.service.Service;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
@Service
public class SceneHolder {

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
