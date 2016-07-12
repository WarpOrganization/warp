package pl.warp.engine.physics.event;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;

/**
 * Created by hubertus on 7/12/16.
 */
public class CollisionEvent extends Event {

    public static final String COLLISION_EVENT_NAME = "collisionEvent";
    private Component secondComponent;
    private float relativeSpeed;


    public CollisionEvent(Component secondComponent, float relativeSpeed) {
        super(COLLISION_EVENT_NAME);
        this.secondComponent = secondComponent;
        this.relativeSpeed = relativeSpeed;
    }

    public float getRelativeSpeed() {
        return relativeSpeed;
    }

    public Component getSecondComponent() {
        return secondComponent;
    }
}
