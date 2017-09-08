package pl.warp.engine.game.script;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.game.GameContext;

import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public abstract class GameScriptWithInput extends Script {

    private GameInputHandler inputHandler = null;

    public GameScriptWithInput(Component owner) {
        super(owner);
    }

    protected GameInputHandler getInputHandler(){
        return Objects.isNull(inputHandler) ? (inputHandler = new GameInputHandler((GameContext) getContext())) : inputHandler;
    }

}
