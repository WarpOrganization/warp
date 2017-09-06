package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Jaca777
 *         Created 2017-02-12 at 14
 */
public class FrigateScript extends GameScript {
    private static final float VELOCITY = 10f;
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);

    private Vector3f rotationCenter;

    public FrigateScript(GameComponent owner, Vector3f rotationCenter) {
        super(owner);
        this.rotationCenter = rotationCenter;
    }

    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty body;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private TransformProperty transform;

    @Override
    protected void init() {
    }


    private Vector3f vector3f = new Vector3f();
    private Vector3f forward = new Vector3f(-1, 0, 0);
    private Quaternionf quaternionf = new Quaternionf();

    @Override
    protected void update(int delta) {
        Vector3f absolutePosition = Transforms.getAbsolutePosition(getOwner(), vector3f);
        Vector3f toCenterVec = rotationCenter.sub(absolutePosition, absolutePosition).normalize();
        Vector3f dir = toCenterVec.cross(UP_VECTOR);
        quaternionf.identity();
        quaternionf.rotateTo(forward, dir);
        transform.getRotation().set(quaternionf);
        body.setVelocity(dir.mul(VELOCITY));
    }


}
