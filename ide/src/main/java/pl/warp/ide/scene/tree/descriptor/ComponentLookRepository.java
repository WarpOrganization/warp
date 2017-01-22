package pl.warp.ide.scene.tree.descriptor;

import pl.warp.engine.core.scene.Component;
import pl.warp.ide.scene.tree.ComponentLook;

/**
 * Created by user on 2017-01-17.
 */
public interface ComponentLookRepository {
    ComponentLook getDesc(Component component);
}
