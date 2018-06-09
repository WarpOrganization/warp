package net.warpgame.engine.physics.shapeconstructors;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public abstract class RigidBodyShapeConstructor implements Disposable {

    protected btCollisionShape constructedShape;

    public abstract void construct();

    /**
     * Calculates inertia tensor based on given mass and previously constructed shape.
     *
     * @param mass - body mass
     * @return calculated inertia tensor
     */
    public Vector3 calculateInertia(float mass) {
        Vector3 inertia = new Vector3();
        constructedShape.calculateLocalInertia(mass, inertia);
        return inertia;
    }

    public btCollisionShape getShape() {
        return constructedShape;
    }

    public boolean isConstructed() {
        return constructedShape != null;
    }

    @Override
    public void dispose() {
        if (constructedShape != null)
            constructedShape.dispose();
    }
}
