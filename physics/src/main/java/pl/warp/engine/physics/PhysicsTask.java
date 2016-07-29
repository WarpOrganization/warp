package pl.warp.engine.physics;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicsTask extends EngineTask {

    private static Logger logger = Logger.getLogger(PhysicsTask.class);

    private CollisionListener collisionListener;

    private CollisionHandler collisionHandler;
    private CollisionStrategy collisionStrategy;
    private Component parent;
    private PhysicsWorld world;

    private Listener<Component, ChildAddedEvent> sceneEnteredListener;
    private Listener<Component, ChildRemovedEvent> sceneLeftEventListener;


    public PhysicsTask(CollisionStrategy collisionStrategy, Component parent) {

        this.collisionStrategy = collisionStrategy;
        this.parent = parent;
    }

    @Override
    protected void onInit() {
        logger.info("initializing physics");
        new SharedLibraryLoader().load("gdx");
        Bullet.init();
        sceneEnteredListener = SimpleListener.createListener(parent, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, this::handleSceneEntered);
        sceneLeftEventListener = SimpleListener.createListener(parent, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, this::handleSceneLeft);

        world = new PhysicsWorld();
        collisionStrategy.init(world);
        collisionHandler = new CollisionHandler(world, collisionStrategy);

        collisionListener = new CollisionListener(world.getActiveCollisions());

        parent.forEachChildren(component -> {
            if (component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)) {
                new ColliderProperty(component, new BasicCollider(new btBoxShape(new Vector3(2.1465f, 0.6255f, 2.833f)), component, new Vector3f(-0.067f, 0, 0), CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
                handleSceneEntered(new ChildAddedEvent(component));
            }
        });
    }

    @Override
    protected void onClose() {
        world.dispose();
    }


    @Override
    public void update(int delta) {
        collisionHandler.updateCollisions();
        collisionHandler.performRayTests();
        finalizeMovement();
        //world.getActiveCollisions().clear();
    }

    public void handleSceneEntered(ChildAddedEvent event) {
        if (event.getAddedChild().hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME)) {
            ColliderProperty tmp = event.getAddedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            synchronized (world) {
                tmp.getCollider().addToWorld(world);
            }
        }
    }


    public void handleSceneLeft(ChildRemovedEvent event) {
        if (event.getRemovedChild().hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME)) {
            ColliderProperty tmp = event.getRemovedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            tmp.getCollider().removeFromWorld();
        }
    }

    private void finalizeMovement() {
        parent.forEachChildren(component -> {
            if (isCollidable(component) && isPhysicalBody(component)) {
                TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                ColliderProperty colliderProperty = component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
                transformProperty.move(physicalBodyProperty.getNextTickTranslation());
                transformProperty.rotate(physicalBodyProperty.getNextTickRotation().x, physicalBodyProperty.getNextTickRotation().y, physicalBodyProperty.getNextTickRotation().z);
                colliderProperty.getCollider().setTransform(transformProperty.getTranslation(), transformProperty.getRotation());
            }
        });
    }

    private boolean isCollidable(Component component) {
        return component.hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
    }

    private boolean isPhysicalBody(Component component) {
        return component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
    }

}
