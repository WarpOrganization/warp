package pl.warp.test;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.annotation.OwnerProperty;

/**
 * @author Hubertus
 *         Created 7/12/16
 */

public class BulletScript extends Script {

    private int life;

    @OwnerProperty(name = BulletProperty.BULLET_PROPERTY_NAME)
    private BulletProperty bulletProperty;

    public BulletScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        this.life = bulletProperty.getTtl();
    }

    @Override
    public void onUpdate(int delta) {
        life -= delta;
        if (life < 0)
            if (getOwner().hasParent())
                getOwner().destroy(); // We are not mean, we won't kill orphans. Nobility quickfix.
    }
//TODO physics rewrite

//    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
//    private synchronized void onCollision(CollisionEvent event) {
//        Component component = event.getSecondComponent();
//        if (component.hasEnabledProperty(Bulletproof.BULLETPROOF_PROPERTY_NAME)) return;
//        if (component != bulletProperty.getPlayerShip() && component.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) {
//            destroy(component);
//        }
//    }


    private void destroy(Component componentHit) {
//        componentHit.triggerEvent(new KabooomScript.KabooomEvent());
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
