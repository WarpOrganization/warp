package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;

/**
 * @author Hubertus
 * Created 22.09.2017
 */
//TODO implement contact events
public class ContactHandler extends ContactListener {

    private ComponentRegistry componentRegistry;

    public ContactHandler(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void onContactStarted(btPersistentManifold manifold) {
        Component c1 = componentRegistry.getComponent(manifold.getBody0().getUserValue());
        Component c2 = componentRegistry.getComponent(manifold.getBody1().getUserValue());
        c1.triggerEvent(new ContactStartedEvent(c2));
        c2.triggerEvent(new ContactStartedEvent(c1));
    }

    @Override
    public void onContactEnded(btPersistentManifold manifold) {
        Component c1 = componentRegistry.getComponent(manifold.getBody0().getUserValue());
        Component c2 = componentRegistry.getComponent(manifold.getBody1().getUserValue());
        c1.triggerEvent(new ContactEndedEvent(c2));
        c2.triggerEvent(new ContactEndedEvent(c1));

    }
}
