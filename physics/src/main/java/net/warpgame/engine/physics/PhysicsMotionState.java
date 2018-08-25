package net.warpgame.engine.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import net.warpgame.engine.core.property.TransformProperty;
import org.joml.Vector3fc;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class PhysicsMotionState extends btMotionState {

    private TransformProperty transformProperty;

    private Vector3f translationOffset = new Vector3f();
    private Vector3f negatedTranslationOffset = new Vector3f();
    private Quaternionf rotationOffset = new Quaternionf();
    private Quaternionf negatedRotationOffset = new Quaternionf();

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

    public void setOffset(Vector3f translationOffset, Quaternionf rotationOffset) {
        this.translationOffset.set(translationOffset);
        this.translationOffset.negate(negatedTranslationOffset);
        this.rotationOffset.set(rotationOffset);
        this.rotationOffset.invert(negatedRotationOffset);
    }

    private Vector3f convertedTranslation = new Vector3f();
    private Vector3 bulletTranslation = new Vector3();

    private Vector3f getTranslation(Matrix4 m) {
        m.getTranslation(bulletTranslation);
        return convertedTranslation
                .set(bulletTranslation.x, bulletTranslation.y, bulletTranslation.z)
                .add(negatedTranslationOffset);
    }

    private Quaternionf convertedRotation = new Quaternionf();
    private Quaternion bulletRotation = new Quaternion();

    private Quaternionf getRotation(Matrix4 m) {
        m.getRotation(bulletRotation);
        return convertedRotation
                .set(bulletRotation.x, bulletRotation.y, bulletRotation.z, bulletRotation.w)
                .mul(negatedRotationOffset);
    }

    private Vector3f helperTranslation = new Vector3f();

    private void translate(Matrix4 matrix, Vector3fc translation) {
        helperTranslation.set(translation);
        helperTranslation.add(translationOffset);
        matrix.setToTranslation(helperTranslation.x, helperTranslation.y, helperTranslation.z);
    }

    private Quaternionf helperRotation = new Quaternionf();

    private void rotate(Matrix4 matrix, Quaternionfc rotation) {
        helperRotation.set(rotation);
        helperRotation.mul(rotationOffset);
        bulletRotation.set(helperRotation.x, helperRotation.y, helperRotation.z, helperRotation.w);
        matrix.rotate(bulletRotation);
    }
}
