package pl.warp.game;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class CameraScript extends Script<Camera> {

    private static final float SPEED = 0.01f;

    private PhysicalBodyProperty parentBody;
    private TransformProperty parentTransform;
    private TransformProperty cameraTransform;

    public CameraScript(Camera owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        this.parentBody = getOwner().getParent().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        this.cameraTransform = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        this.parentTransform = getOwner().getParent().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    private Vector3f prevVelocity = new Vector3f();

    @Override
    public void onUpdate(int delta) {
        Quaternionf rotation = new Quaternionf(parentTransform.getRotation()).invert();
        Vector3f currentVelocity = new Vector3f(parentBody.getVelocity()).rotate(rotation);
        Vector3f velDelta = new Vector3f();
        currentVelocity.sub(prevVelocity, velDelta);
        this.prevVelocity = currentVelocity;
        applyForce(velDelta);
    }

    private Vector3f actualDistance = new Vector3f();

    private void applyForce(Vector3f velocityDelta) {
        Vector3f diff = new Vector3f();
        velocityDelta.sub(actualDistance, diff);
        if (diff.length() > 1) {
            Vector3f delta = diff.normalize().mul(SPEED);
            actualDistance.add(delta);
            cameraTransform.move(delta);
        }


    }


}
