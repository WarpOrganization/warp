package pl.warp.engine.physics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;

/**
 * Created by hubertus on 7/6/16.
 */
public class SceneLeftEvent extends Event {

    public static final String SCENE_LEFT_EVENT_NAME = "componentLeftScene";

    private Component component;

    public SceneLeftEvent(Component component){
        super(SCENE_LEFT_EVENT_NAME);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
