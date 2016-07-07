package pl.warp.engine.physics.property;

import pl.warp.engine.physics.property.logic.ColliderLogic;

/**
 * Created by hubertus on 7/3/16.
 */
public interface ColliderProperty {
    String COLLIDER_PROPERTY_NAME = "collider";

    ColliderLogic getLogic();
}
