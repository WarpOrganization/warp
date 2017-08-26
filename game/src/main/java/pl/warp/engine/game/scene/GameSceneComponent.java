package pl.warp.engine.game.scene;

import pl.warp.engine.core.component.SceneComponent;
import pl.warp.engine.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameSceneComponent extends SceneComponent implements GameComponent {

    public GameSceneComponent(GameComponent parent) {
        super(parent);
    }

    public GameSceneComponent(GameContext context) {
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
