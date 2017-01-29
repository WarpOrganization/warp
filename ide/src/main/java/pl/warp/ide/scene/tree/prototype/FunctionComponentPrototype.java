package pl.warp.ide.scene.tree.prototype;

import pl.warp.game.scene.GameComponent;

import java.util.function.Function;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public class FunctionComponentPrototype extends ComponentPrototype{
    private Function<GameComponent, GameComponent> function;

    public FunctionComponentPrototype(String name, Function<GameComponent, GameComponent> function) {
        super(name);
        this.function = function;
    }


    @Override
    public GameComponent newInstance(GameComponent parent) {
        return function.apply(parent);
    }
}
