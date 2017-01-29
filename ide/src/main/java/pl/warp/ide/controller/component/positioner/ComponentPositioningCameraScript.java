package pl.warp.ide.controller.component.positioner;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 12
 */
public class ComponentPositioningCameraScript extends GameScript<GameComponent> {
    private Camera camera;

    public ComponentPositioningCameraScript(GameComponent owner, Camera camera) {
        super(owner);
        this.camera = camera;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {

    }
}
