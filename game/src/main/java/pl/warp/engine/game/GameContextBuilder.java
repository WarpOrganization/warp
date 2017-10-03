package pl.warp.engine.game;

import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.input.Input;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameContextBuilder {
    private GameContext gameContext;

    public GameContextBuilder() {
        this.gameContext = new GameContext();
    }

//TODO physics rewrite
/*    public GameContextBuilder setRayTester(CameraRayTester rayTester) {
        gameContext.setRayTester(rayTester);
        return this;
    }*/

    public GameContext getGameContext() {
        return gameContext;
    }

    public GameContextBuilder setScene(GameScene scene) {
        gameContext.setScene(scene);
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

}
