package pl.warp.ide.scene.descriptor;

import pl.warp.engine.core.scene.Component;
import pl.warp.ide.scene.ComponentLook;

/**
 * Created by user on 2017-01-17.
 */
public interface ComponentLookRepository {
    ComponentLook getDesc(Component component);
}
