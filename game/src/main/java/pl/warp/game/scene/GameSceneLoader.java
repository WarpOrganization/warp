package pl.warp.game.scene;

import pl.warp.engine.core.EngineThread;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface GameSceneLoader {
    void loadScene();
    void loadGraphics(EngineThread graphicsThread);
    GameScene getScene();
    GameComponent getCameraComponent();
}
