package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.physics.collider.PointCollider;

import java.util.*;

/**
 * Created by hubertus on 7/14/16.
 */
public class PhysicsWorld {

    private TreeMap<Integer, Component> componentTreeMap;
    private btCollisionWorld collisionWorld;
    private List<btPersistentManifold> activeCollisons;
    private List<PointCollider> rayTests;
    private List<PointCollider> destroyedRayTests;

    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase dbvtBroadphase;
    private btDefaultCollisionConfiguration defaultCollisionConfiguration;

    private int counter = Integer.MIN_VALUE;


    public PhysicsWorld() {
        componentTreeMap = new TreeMap<>();
        activeCollisons = new ArrayList<>();
        rayTests = new ArrayList<>();
        destroyedRayTests = new ArrayList<>();

        defaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(defaultCollisionConfiguration);
        dbvtBroadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, dbvtBroadphase, defaultCollisionConfiguration);
    }

    public btCollisionWorld getCollisionWorld() {
        return collisionWorld;
    }

    public void addToCollisionWorld(btCollisionObject object) {
        collisionWorld.addCollisionObject(object);
    }

    public void removeFromCollisionWorld(btCollisionObject object) {
        collisionWorld.removeCollisionObject(object);
    }

    public void addToComponentMap(Component component) {
        componentTreeMap.put(counter, component);
        counter++;
    }

    public void removeFromComponentMap(int key) {
        componentTreeMap.remove(key);
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void addRayTest(PointCollider collider) {
        rayTests.add(collider);
    }

    public void removeRayTest(PointCollider collider) {
        rayTests.remove(collider);
    }

    public synchronized Component getComponent(int key) {
        return componentTreeMap.get(key);
    }

    public List<PointCollider> getRayTestColliders() {
        return rayTests;
    }

    public List<btPersistentManifold> getActiveCollisions() {
        return activeCollisons;
    }

    public List<PointCollider> getDestroyedRayTests() {
        return destroyedRayTests;
    }

    public synchronized void cleanRayTests() {
        rayTests.removeAll(destroyedRayTests);
        destroyedRayTests.clear();
    }

    public void addDestroyedRayTest(PointCollider collider) {
        destroyedRayTests.add(collider);
    }

    public void dispose() {
        dispatcher.dispose();
        dbvtBroadphase.dispose();
        defaultCollisionConfiguration.dispose();
        collisionWorld.dispose();
    }
}
