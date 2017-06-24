package pl.warp.engine.game.scene;

import pl.warp.engine.core.EngineThread;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface GameSceneLoader {
    void loadScene();
    void loadGraphics(EngineThread graphicsThread);
    GameScene getScene();
    GameComponent getMainCameraComponent();
    void loadSound(EngineThread audioThread);
}
