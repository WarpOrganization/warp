package pl.warp.game;

import pl.warp.engine.core.EngineContext;
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
public class GameContext extends EngineContext {
    private CameraRayTester rayTester;
    private Camera camera;
    private Graphics graphics;


    public CameraRayTester getRayTester() {
        return rayTester;
    }

    public Camera getCamera() {
        return camera;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public GameScene getScene() {
        return (GameScene) super.getScene();
    }

    protected void setRayTester(CameraRayTester rayTester) {
        this.rayTester = rayTester;
    }

    protected void setScene(GameScene scene) {
        super.setScene(scene);
    }

    @Override
    protected void setScriptManager(ScriptManager scriptManager) {
        super.setScriptManager(scriptManager);
    }

    @Override
    protected void setInput(Input input) {
        super.setInput(input);
    }

    protected void setCamera(Camera camera) {
        this.camera = camera;
    }

    protected void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}
