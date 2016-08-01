package pl.warp.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hubertus on 7/12/16.
 */
public class BulletScript extends Script<Component> {

    private int life;
    private Listener<Component, CollisionEvent> collisionListener;
    private static final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3);
    private Texture2DArray explosionSpritesheet;
    private Component playerShip;

    public BulletScript(Component owner, int life, Texture2DArray explosionSpritesheet, Component playerShip) {
        super(owner);
        this.life = life;
        this.explosionSpritesheet = explosionSpritesheet;
        this.playerShip = playerShip;
    }

    @Override
    public void onInit() {
        collisionListener = SimpleListener.createListener(getOwner(), CollisionEvent.COLLISION_EVENT_NAME, this::onCollision);
    }

    @Override
    public void onUpdate(int delta) {
        life -= delta;
        if (life < 0)
            getOwner().destroy();
    }

    private void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (component != playerShip) {
            ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), new Vector2f(0), 0);
            ParticleFactory factory = new RandomSpreadingParticleFactory(0.02f, 300, true, true);
            component.getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            new GraphicsParticleSystemProperty(component, new ParticleSystem(animator, factory, 500, explosionSpritesheet));
            executorService.schedule(() -> destroy(component), 1, TimeUnit.SECONDS);
        }
    }

    private void destroy(Component componentHit) {
        componentHit.destroy();
        getOwner().destroy();
    }
}
