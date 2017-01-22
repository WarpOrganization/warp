package pl.warp.ide.scene;

import pl.warp.engine.core.EngineThread;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.camera.Camera;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface SceneLoader {
    void loadScene();
    boolean isLoaded();
    void loadGraphics(EngineThread graphicsThread);
    Scene getScene();
    Camera getCamera();
}
