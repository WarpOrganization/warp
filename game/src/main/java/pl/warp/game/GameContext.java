package pl.warp.game;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.script.ScriptManager;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.physics.RayTester;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameContext extends EngineContext {
    private RayTester rayTester;
    private Camera camera;


    public RayTester getRayTester() {
        return rayTester;
    }

    public Camera getCamera() {
        return camera;
    }

    protected void setRayTester(RayTester rayTester) {
        this.rayTester = rayTester;
    }

    @Override
    protected void setScene(Scene scene) {
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
}
