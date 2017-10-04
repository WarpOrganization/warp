package pl.warp.engine.game;

import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.core.script.ScriptManager;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.input.Input;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameContext extends EngineContext {
//TODO physics rewrite
//    private CameraRayTester rayTester;
    private Graphics graphics;
    private Input input;

    public GameContext() {
        this.setScriptManager(getContext().findOne(ScriptManager.class).get());
    }
//TODO physics rewrite

//    public CameraRayTester getRayTester() {
//        return rayTester;
//    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public GameScene getScene() {
        return (GameScene) super.getScene();
    }
//TODO physics rewrite

//    protected void setRayTester(CameraRayTester rayTester) {
//        this.rayTester = rayTester;
//    }

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

}
