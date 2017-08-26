package pl.warp.engine.game.scene;

import pl.warp.engine.core.component.Scene;
import pl.warp.engine.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 19
 */
public class GameScene extends Scene implements GameComponent {


    public GameScene(GameContext context) {
        super(context);
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
