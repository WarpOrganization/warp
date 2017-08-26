package pl.warp.engine.input;

import pl.warp.engine.core.execution.task.SimpleEngineTask;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 01
 */
public class InputTask extends SimpleEngineTask {

    private Input input;

    public InputTask(Input input) {
        this.input = input;
    }

    @Override
    public void update(int delta) {
        input.update();
    }
}
