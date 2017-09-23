package pl.warp.engine.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.TransformProperty;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class PhysicsMotionState extends btMotionState {

    private TransformProperty transformProperty;

    public PhysicsMotionState(TransformProperty transformProperty) {
        this.transformProperty = transformProperty;
    }

    @Override
    public void getWorldTransform(Matrix4 worldTrans) {
        translate(worldTrans, transformProperty.getTranslation());
        rotate(worldTrans, transformProperty.getRotation());
    }

    @Override
    public void setWorldTransform(Matrix4 worldTrans) {
        transformProperty.setTranslation(getTranslation(worldTrans));
        transformProperty.setRotation(getRotation(worldTrans));
    }

    private Vector3f convertedTranslation = new Vector3f();
    private Vector3 bulletTranslation = new Vector3();

    private Vector3f getTranslation(Matrix4 m) {
        m.getTranslation(bulletTranslation);
        return convertedTranslation.set(bulletTranslation.x, bulletTranslation.y, bulletTranslation.z);
    }

    private Quaternionf convertedRotation = new Quaternionf();
    private Quaternion bulletRotation = new Quaternion();

    private Quaternionf getRotation(Matrix4 m) {
        m.getRotation(bulletRotation);
        return convertedRotation.set(bulletRotation.x, bulletRotation.y, bulletRotation.z, bulletRotation.w);
    }

    private void translate(Matrix4 matrix, Vector3f translation) {
        matrix.setToTranslation(translation.x, translation.y, translation.z);
    }

    private void rotate(Matrix4 matrix, Quaternionf rotation) {
        bulletRotation.set(rotation.x, rotation.y, rotation.z, rotation.w);
        matrix.rotate(bulletRotation);
    }
}
