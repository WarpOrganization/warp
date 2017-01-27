package pl.warp.game.scene;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.SceneComponent;
import pl.warp.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */
public class GameSceneComponent extends SceneComponent implements GameComponent {
    private GameContext context;

    public GameSceneComponent(GameComponent parent) {
        super(parent);
        this.context = parent.getContext();
    }

    public GameSceneComponent(GameContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public GameContext getContext() {
        return context;
    }

    @Override
    public <T extends Property> T getProperty(Class<T> c) {
        return super.getProperty(c);
    }
}
