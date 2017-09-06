package pl.warp.engine.game.script;

import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public abstract class GameScriptWithInput extends GameScript {

    private GameInputHandler inputHandler;

    public GameScriptWithInput(GameComponent owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        inputHandler = new GameInputHandler(getContext());
        super.onInit();
    }

    protected GameInputHandler getInputHandler(){
        return inputHandler;
    }

}
