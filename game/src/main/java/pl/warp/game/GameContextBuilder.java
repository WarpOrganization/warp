package pl.warp.game;

import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.script.ScriptManager;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.game.scene.GameScene;
import pl.warp.game.script.CameraRayTester;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameContextBuilder {
    private GameContext gameContext;

    public GameContextBuilder() {
        this.gameContext = new GameContext();
    }

    public void setRayTester(CameraRayTester rayTester) {
        gameContext.setRayTester(rayTester);
    }

    public void setCamera(Camera camera) {
        gameContext.setCamera(camera);
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public void setScene(GameScene scene) {
        gameContext.setScene(scene);
    }

    public void setScriptManager(ScriptManager scriptManager) {
        gameContext.setScriptManager(scriptManager);
    }

    public void setInput(Input input) {
        gameContext.setInput(input);
    }

    public void setGraphics(Graphics graphics) {
        gameContext.setGraphics(graphics);
    }
}
