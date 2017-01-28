package pl.warp.ide.scene.tree.look;

import pl.warp.game.scene.GameComponent;
import pl.warp.ide.scene.tree.ComponentLook;

/**
 * Created by user on 2017-01-17.
 */
public interface ComponentLookRepository {
    ComponentLook getLook(GameComponent component);
}
