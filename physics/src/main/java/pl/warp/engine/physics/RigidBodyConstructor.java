package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class RigidBodyConstructor implements Disposable {

    private final btCollisionShape shape;
    private final btRigidBody.btRigidBodyConstructionInfo constructionInfo;
    private static Vector3 localInertia = new Vector3();

    public RigidBodyConstructor(btCollisionShape shape, float mass) {
        this.shape = shape;
        if (mass > 0f)
            shape.calculateLocalInertia(mass, localInertia);
        else
            localInertia.set(0, 0, 0);
        this.constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, shape, localInertia);
    }

    public btRigidBody construct() {
        return new btRigidBody(constructionInfo);
    }

    @Override
    public void dispose() {
        shape.dispose();
        constructionInfo.dispose();
    }
}
