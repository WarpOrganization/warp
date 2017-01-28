package pl.warp.game.script;

import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public abstract class GameScriptWithInput<T extends GameComponent> extends GameScript<T> {

    private GameInputHandler inputHandler;

    public GameScriptWithInput(T owner) {
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
