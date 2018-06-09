package net.warpgame.engine.physics.shapeconstructors;

import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class RigidBodyCapsuleShapeConstructor extends RigidBodyShapeConstructor {

    private float radius;
    private float height;

    /**
     * Initializes shape constructor with given radius and height.
     * construct() method must be invoked before getShape() and calculateInertia() methods.
     */
    public RigidBodyCapsuleShapeConstructor(float radius, float height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public void construct() {
        constructedShape = new btCapsuleShape(radius, height);
    }

    /**
     * getShape() and calculateInertia() methods will return updated results after invoking
     * construct() method.
     */
    public void setSize(float radius, float height) {
        this.radius = radius;
        this.height = height;
    }
}
