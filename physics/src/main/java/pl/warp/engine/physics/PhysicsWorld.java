package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.physics.collider.PointCollider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by hubertus on 7/14/16.
 */
public class PhysicsWorld {

    private TreeMap<Integer, Component> componentTreeMap;
    private btCollisionWorld collisionWorld;
    private Set<btPersistentManifold> activeCollisons;
    private ArrayList<PointCollider> rayTests;

    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase dbvtBroadphase;
    private btDefaultCollisionConfiguration defaultCollisionConfiguration;

    private int counter = Integer.MIN_VALUE;


    public PhysicsWorld() {
        componentTreeMap = new TreeMap<>();
        activeCollisons = new HashSet<>();
        rayTests = new ArrayList<>();

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

    public int getCounter(){
        return counter;
    }

    public void addRayTest(PointCollider collider) {
        rayTests.add(collider);
    }

    public void removeRayTest(PointCollider collider) {
        rayTests.remove(collider);
    }

    public Component getComponent(int key) {
        return componentTreeMap.get(key);
    }

    public ArrayList<PointCollider> getRayTestColliders() {
        return rayTests;
    }

    public Set<btPersistentManifold> getActiveCollisions() {
        return activeCollisons;
    }

    public void dispose() {
        dispatcher.dispose();
        dbvtBroadphase.dispose();
        defaultCollisionConfiguration.dispose();
        collisionWorld.dispose();
    }
}
