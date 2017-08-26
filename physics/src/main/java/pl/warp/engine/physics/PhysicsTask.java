package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.event.Listener;
import pl.warp.engine.core.component.SimpleListener;
import pl.warp.engine.core.component.listenable.ChildAddedEvent;
import pl.warp.engine.core.component.listenable.ChildRemovedEvent;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 7/4/16
 */

public class PhysicsTask extends EngineTask {

    private static Logger logger = Logger.getLogger(PhysicsTask.class);

    private CollisionListener collisionListener;

    private CollisionHandler collisionHandler;
    private CollisionStrategy collisionStrategy;
    private Component parent;
    private PhysicsWorld world;
    private RayTester rayTester;

    private Listener<Component, ChildAddedEvent> sceneEnteredListener;
    private Listener<Component, ChildRemovedEvent> sceneLeftEventListener;


    public PhysicsTask(CollisionStrategy collisionStrategy, Component parent, RayTester rayTester) {

        this.collisionStrategy = collisionStrategy;
        this.parent = parent;
        this.rayTester = rayTester;
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
        collisionListener = new CollisionListener(world);
        rayTester.init(world);

        parent.forEachChildren(component -> {
            if (component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)) {
                PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                ColliderProperty colliderProperty = new ColliderProperty(new BasicCollider(new btBoxShape(
                        new Vector3(physicalBodyProperty.getXLength(), physicalBodyProperty.getYLength(), physicalBodyProperty.getZLength())),
                        component, new Vector3f(0f), CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
                component.addProperty(colliderProperty);
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
        finalizeMovement();
        collisionHandler.updateCollisions();
        collisionHandler.performRayTests();
        rayTester.update();
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

    public RayTester getRayTester() {
        return rayTester;
    }
}
