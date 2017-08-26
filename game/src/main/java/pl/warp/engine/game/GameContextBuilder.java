package pl.warp.engine.game;

import pl.warp.engine.ai.AIManager;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.common.input.Input;
import pl.warp.engine.core.script.ScriptManager;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.game.script.CameraRayTester;
import pl.warp.engine.graphics.Graphics;

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
