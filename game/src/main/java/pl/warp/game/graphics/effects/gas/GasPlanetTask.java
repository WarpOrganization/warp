package pl.warp.game.graphics.effects.gas;

import pl.warp.engine.core.SimpleEngineTask;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 20
 */
public class GasPlanetTask extends SimpleEngineTask {

    private GasPlanetProgram program;

    public GasPlanetTask(GasPlanetProgram program) {
        this.program = program;
    }

    @Override
    public void update(int delta) {
        program.use();
        program.update(delta);
    }
}
