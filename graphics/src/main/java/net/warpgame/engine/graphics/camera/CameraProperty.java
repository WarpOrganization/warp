package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.Transforms;
import net.warpgame.engine.graphics.utility.projection.PerspectiveMatrix;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author MarconZet
 * Created 24.08.2018
 */
public class CameraProperty extends Property {
    private PerspectiveMatrix projection;

    private Matrix4f cameraMatrix = new Matrix4f();
    private Quaternionf rotation = new Quaternionf();
    private Matrix4f rotationMatrix = new Matrix4f();
    private Vector3f cameraPos = new Vector3f();


    public CameraProperty(PerspectiveMatrix projection) {
        this.projection = projection;
    }

    public PerspectiveMatrix getProjection() {
        return projection;
    }

    public void update() {
        Transforms.getAbsoluteTransform(getOwner(), cameraMatrix).invert();
        Transforms.getAbsoluteRotation(getOwner(), rotation).get(rotationMatrix).invert();
        Transforms.getAbsolutePosition(getOwner(), cameraPos);
    }

    public Vector3f getCameraPos() {
        return cameraPos;
    }

    public Matrix4f getCameraMatrix() {
        return cameraMatrix;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Matrix4f getRotationMatrix() {
        return rotationMatrix;
    }
}
