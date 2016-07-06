package pl.warp.engine.physics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;

/**
 * Created by hubertus on 7/6/16.
 */
public class SceneEnteredEvent extends Event {

    public static final String SCENE_ENTERED_EVENT_NAME = "componentEnteredScene";

    private Component component;

    public SceneEnteredEvent(Component component) {
        super(SCENE_ENTERED_EVENT_NAME);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
