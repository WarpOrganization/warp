package pl.warp.engine.game.scene;

import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.engine.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 18
 */
public class ListenableGameParent extends ListenableParent implements GameComponent {

    public ListenableGameParent(GameContext context) {
        super(context);
    }

    public ListenableGameParent(GameComponent parent) {
        super(parent);
    }
    @Override
    public GameContext getContext() {
        return (GameContext) super.getContext();
    }

    @Override
    public GameComponent getParent(){
        return (GameComponent) super.getParent();
    }

    @Override
    public GameComponent getChild(int index) {
        return (GameComponent) super.getChild(index);
    }
}
