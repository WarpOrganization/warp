package pl.warp.test;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.EventHandler;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;
import pl.warp.engine.physics.event.CollisionEvent;

/**
 * @author Hubertus
 *         Created 7/12/16
 */

public class BulletScript extends GameScript {

    private int life;

    @OwnerProperty(name = BulletProperty.BULLET_PROPERTY_NAME)
    private BulletProperty bulletProperty;

    public BulletScript(GameComponent owner) {
        super(owner);
    }

    @Override
    protected void init() {
        this.life = bulletProperty.getTtl();
    }

    @Override
    protected void update(int delta) {
        life -= delta;
        if (life < 0)
            if (getOwner().hasParent())
                getOwner().destroy(); // We are not mean, we won't kill orphans. Nobility quickfix.
    }

    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    private synchronized void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (component.hasEnabledProperty(Bulletproof.BULLETPROOF_PROPERTY_NAME)) return;
        if (component != bulletProperty.getPlayerShip() && component.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) {
            destroy(component);
        }
    }


    private void destroy(Component componentHit) {
        componentHit.triggerEvent(new KabooomScript.KabooomEvent());
        if (getOwner().hasParent()) getOwner().destroy();
    }

    //TODO REMOVE AS SOON AS DISABLING COLLIDER WORKS
    public static class Bulletproof extends Property {
        public static final String BULLETPROOF_PROPERTY_NAME = "bulletproof";

        public Bulletproof() {
            super(BULLETPROOF_PROPERTY_NAME);
        }
    }

}
