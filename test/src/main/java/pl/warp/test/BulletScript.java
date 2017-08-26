package pl.warp.test;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.event.Listener;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.EventHandler;
import pl.warp.engine.game.script.GameScript;

/**
 * @author Hubertus
 *         Created 7/12/16
 */

public class BulletScript extends GameScript<GameComponent> {

    private int life;
    private Listener<Component, CollisionEvent> collisionListener;
    private Texture2DArray explosionSpritesheet;
    private Component shooterShip;

    public BulletScript(GameComponent owner, int life, Texture2DArray explosionSpritesheet, GameComponent playerShip) {
        super(owner);
        this.life = life;
        this.explosionSpritesheet = explosionSpritesheet;
        this.shooterShip = playerShip;

    }

    @Override
    protected void init() {

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
        if (component != shooterShip && component.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) {
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
