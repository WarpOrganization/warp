package pl.warp.engine.core.scene.input;

import pl.warp.engine.core.EngineTask;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 01
 */
public class InputTask extends EngineTask {

    private Input input;

    public InputTask(Input input) {
        this.input = input;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        input.update();
    }
}
