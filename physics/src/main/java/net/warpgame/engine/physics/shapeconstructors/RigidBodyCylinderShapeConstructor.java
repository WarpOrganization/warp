package net.warpgame.engine.physics.shapeconstructors;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class RigidBodyCylinderShapeConstructor extends RigidBodyShapeConstructor {

    private Vector3 size;

    /**
     * Initializes shape constructor with given shape half extents.
     * Passing vector (1, 2, 3) will create shape with total size (2, 4, 6)
     * construct() method must be invoked before getShape() and calculateInertia() methods.
     */
    public RigidBodyCylinderShapeConstructor(Vector3f halfExtents) {
        this.size.set(halfExtents.x, halfExtents.y, halfExtents.z);
    }

    /**
     * Initializes shape constructor with given shape half extents.
     * Passing values (1, 2, 3) will create shape with total size (2, 4, 6)
     * construct() method must be invoked before getShape() and calculateInertia() methods.
     */
    public RigidBodyCylinderShapeConstructor(float xSize, float ySize, float zSize) {
        this.size.set(xSize, ySize, zSize);
    }

    @Override
    public void construct() {
        constructedShape = new btCylinderShape(size);
    }

    /**
     * Updates shape constructor with given shape half extents.
     * Passing vector (1, 2, 3) will create shape with total size (2, 4, 6).
     * getShape() and calculateInertia() methods will return updated results after invoking
     * construct() method.
     */
    public void setSize(Vector3f halfExtents) {
        this.size.set(halfExtents.x, halfExtents.y, halfExtents.z);
    }

    /**
     * Updates shape constructor with given shape half extents.
     * Passing values (1, 2, 3) will create shape with total size (2, 4, 6).
     * getShape() and calculateInertia() methods will return updated results after invoking
     * construct() method.
     */
    public void setSize(float xSize, float ySize, float zSize) {
        this.size.set(xSize, ySize, zSize);
    }
}
