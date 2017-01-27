package pl.warp.game.scene;

import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 18
 */
public class ListenableGameParent extends ListenableParent implements GameComponent {

    private GameContext context;

    public ListenableGameParent(GameContext context) {
        super(context);
        this.context = context;
    }

    public ListenableGameParent(GameComponent parent) {
        super(parent);
        this.context = parent.getContext();
    }

    @Override
    public GameContext getContext() {
        return context;
    }
}
