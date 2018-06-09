package net.warpgame.engine.physics.shapeconstructors;

import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class RigidBodySphereShapeConstructor extends RigidBodyShapeConstructor {

    private float radius;

    /**
     * Initializes shape constructor with given radius.
     * construct() method must be invoked before getShape() and calculateInertia() methods.
     */
    public RigidBodySphereShapeConstructor(float radius) {
        this.radius = radius;
    }

    @Override
    public void construct() {
        constructedShape = new btSphereShape(radius);
    }

    /**
     * getShape() and calculateInertia() methods will return updated results after invoking
     * construct() method.
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }
}
