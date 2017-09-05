package pl.warp.engine.game;

import pl.warp.engine.ai.AIManager;
import pl.warp.engine.core.context.Context;
import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.game.script.CameraRayTester;
import pl.warp.engine.game.script.GameScriptManager;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.input.Input;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameContext extends EngineContext {

    private Context context;
    private CameraRayTester rayTester;
    private Graphics graphics;
    private AIManager aiManager;
    private Input input;

    public GameContext() {
        this.context = Context.create();
        this.setScriptManager(context.findOne(GameScriptManager.class).get());
    }

    public CameraRayTester getRayTester() {
        return rayTester;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public AIManager getAIManager() {
        return aiManager;
    }

    @Override
    public GameScene getScene() {
        return (GameScene) super.getScene();
    }

    protected void setRayTester(CameraRayTester rayTester) {
        this.rayTester = rayTester;
    }

    public void setScene(GameScene scene) {
        super.setScene(scene);
    }

    protected void setInput(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    protected void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    protected void setEventDispatcher(EventDispatcher eventDispatcher) {
        super.setEventDispatcher(eventDispatcher);
    }

    protected void setAiManager(AIManager aiManager) {
        this.aiManager = aiManager;
    }

}
