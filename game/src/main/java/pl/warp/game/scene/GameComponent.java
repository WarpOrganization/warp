package pl.warp.game.scene;

import pl.warp.engine.core.scene.Component;
import pl.warp.game.GameContext;

import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 19
 */
public interface GameComponent extends Component {
    @Override
    GameContext getContext();

    @Override
    GameComponent getParent();

    @Override
    GameComponent getChild(int index);

    default void forEachGameChildren(Consumer<GameComponent> f){
        forEachChildren((Consumer)f);
    }
}
