package pl.warp.engine.graphics;

import org.apache.log4j.Logger;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.light.SceneLightObserver;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 16
 */
public class EnvironmentTask extends EngineTask {
    private static final Logger logger = Logger.getLogger(EnvironmentTask.class);

    private Scene scene;
    private Environment environment;
    private SceneLightObserver lightObserver;

    public EnvironmentTask(Scene scene, Environment environment) {
        this.scene = scene;
        this.environment = environment;
    }

    @Override
    protected void onInit() {
        this.lightObserver = new SceneLightObserver(scene, environment);
    }

    @Override
    protected void onClose() {
        lightObserver.destroy();
    }

    @Override
    public void update(int delta) {
        //TODO
    }
}
