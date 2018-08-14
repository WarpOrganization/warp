package net.warpgame.servertest.server;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.net.NetComponentRegistry;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.physics.RigidBodyConstructor;
import net.warpgame.engine.server.Client;
import net.warpgame.servertest.BulletCreatedEvent;
import net.warpgame.servertest.ShotEvent;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class GunListener extends Listener<ShotEvent> {

    private final NetComponentRegistry componentRegistry;
    private final RigidBodyConstructor rigidBodyConstructor;

    protected GunListener(Component owner,
                          NetComponentRegistry componentRegistry,
                          RigidBodyConstructor rigidBodyConstructor) {
        super(owner, Event.getTypeId(ShotEvent.class));
        this.componentRegistry = componentRegistry;
        this.rigidBodyConstructor = rigidBodyConstructor;
    }

    @Override
    public void handle(ShotEvent event) {
        if (checkLegality())
            createBullet(event);
    }

    private boolean checkLegality() {
        return true;
    }

    private void createBullet(ShotEvent event) {
        SceneComponent bullet = new SceneComponent(componentRegistry.getRootComponent(), event.getBulletComponentId());


        TransformProperty transformProperty = new TransformProperty();
        TransformProperty ownerTransformProperty = getOwner().getProperty(Property.getTypeId(TransformProperty.class));

        Vector3f forwardVector = new Vector3f(-6, 0, 0);
        forwardVector.rotate(ownerTransformProperty.getRotation());
        transformProperty.move(ownerTransformProperty.getTranslation(new Vector3f()).add(forwardVector));
        transformProperty.setRotation(ownerTransformProperty.getRotation());
        bullet.addProperty(transformProperty);


        FullPhysicsProperty physicsProperty = new FullPhysicsProperty(rigidBodyConstructor.construct(transformProperty));
        FullPhysicsProperty ownerPhysicsProperty = getOwner().getProperty(Property.getTypeId(FullPhysicsProperty.class));
        Vector3f initialVelocity = new Vector3f(-100, 0, 0).add(ownerPhysicsProperty.getVelocity());
        initialVelocity.rotate(ownerTransformProperty.getRotation());
        physicsProperty.setVelocity(initialVelocity);
        bullet.addProperty(physicsProperty);

        bullet.addScript(BulletDeathCountdownScript.class);

        getOwner().triggerEvent(new BulletCreatedEvent(bullet.getId(), Client.ALL));
    }
}
