package pl.warp.game;

import pl.warp.engine.ai.AIManager;
import pl.warp.engine.core.scene.EventDispatcher;
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

    public GameContextBuilder setRayTester(CameraRayTester rayTester) {
        gameContext.setRayTester(rayTester);
        return this;
    }

    public GameContextBuilder setCamera(Camera camera) {
        gameContext.setCamera(camera);
        return this;
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public GameContextBuilder setScene(GameScene scene) {
        gameContext.setScene(scene);
        return this;
    }

    public GameContextBuilder setScriptManager(ScriptManager scriptManager) {
        gameContext.setScriptManager(scriptManager);
        return this;
    }

    public GameContextBuilder setInput(Input input) {
        gameContext.setInput(input);
        return this;
    }

    public GameContextBuilder setGraphics(Graphics graphics) {
        gameContext.setGraphics(graphics);
        return this;
    }

    public GameContextBuilder setEventDispatcher(EventDispatcher eventDispatcher) {
        gameContext.setEventDispatcher(eventDispatcher);
        return this;
    }

    public GameContextBuilder setAIManager(AIManager aiManager) {
        gameContext.setAiManager(aiManager);
        return this;
    }
}
