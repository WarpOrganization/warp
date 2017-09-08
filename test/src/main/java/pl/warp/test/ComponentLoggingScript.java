package pl.warp.test;

import pl.warp.engine.core.component.ComponentDeathEvent;
import pl.warp.engine.core.property.NameProperty;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.core.script.EventHandler;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.OwnerProperty;
import pl.warp.engine.core.script.updatescheduler.DelayScheduling;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 12
 */

@DelayScheduling(delayInMillis = ComponentLoggingScript.DELAY)
public class ComponentLoggingScript extends Script {

    public static final int DELAY = 500;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private TransformProperty transform;

    @OwnerProperty(name = NameProperty.NAME_PROPERTY_NAME)
    private NameProperty name;

    public ComponentLoggingScript(GameComponent owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        log("Component name: " + name.getComponentName());
    }

    @Override
    public void onUpdate(int delta) {
        log("Component translation: " + transform.getTranslation());
        log("Component scale: " + transform.getScale());
        log("Component rotation: " + transform.getRotation());
    }

    @EventHandler(eventName = ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME)
    private void onDeath(ComponentDeathEvent event) {
        log("Component died :(");
    }

    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    private void onCollision(CollisionEvent event) {
        log("Component collided with" + event.getSecondComponent() + " at speed " + event.getRelativeSpeed());
    }
}
