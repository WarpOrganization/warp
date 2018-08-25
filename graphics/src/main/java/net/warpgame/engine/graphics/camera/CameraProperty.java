package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.Transforms;
import org.joml.*;

import java.lang.Math;

/**
 * @author MarconZet
 * Created 24.08.2018
 */
public class CameraProperty extends Property {
    private Matrix4f projection;
    private boolean dirty;

    public enum CameraType {
        ORTHOGRAPHIC,
        PERSPECTIVE
    }

    private CameraType type;

    private float size;
    private float fov;
    private float aspect;

    private float zNear;
    private float zFar;

    private Vector2f viewportXY;//TODO viewport rectangle
    private Vector2f viewportWH;

    private float depth;

    public CameraProperty(float fov, float aspect, float zNear, float zFar){
        this(CameraType.PERSPECTIVE, 5, fov, aspect, zNear, zFar, new Vector2f(), new Vector2f(), 0);
    }

    public CameraProperty(float size, float zNear, float zFar){
        this(CameraType.ORTHOGRAPHIC, size, 60, 1, zNear, zFar, new Vector2f(), new Vector2f(), 0);
    }

    private CameraProperty(CameraType type, float size, float fov, float aspect, float zNear, float zFar, Vector2f viewportXY, Vector2f viewportWH, float depth) {
        this.type = type;
        this.size = size;
        this.fov = fov;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;
        this.viewportXY = viewportXY;
        this.viewportWH = viewportWH;
        this.depth = depth;
        this.projection = new Matrix4f();
    }

    @Override
    public void init() {
        super.init();
        updateProjectionMatrix();
    }

    private Matrix4f cameraMatrix = new Matrix4f();
    private Quaternionf rotation = new Quaternionf();
    private Matrix4f rotationMatrix = new Matrix4f();
    private Vector3f cameraPos = new Vector3f();

    public void update() {
        Transforms.getAbsoluteTransform(getOwner(), cameraMatrix).invert();
        Transforms.getAbsoluteRotation(getOwner(), rotation).get(rotationMatrix).invert();
        Transforms.getAbsolutePosition(getOwner(), cameraPos);
        if(dirty) updateProjectionMatrix();
    }

    private void updateProjectionMatrix(){
        switch (type){
            case ORTHOGRAPHIC: projection.ortho(-size, size, -size, size, zNear, zFar);
            case PERSPECTIVE: projection.perspective(fov*(float)Math.PI/180, aspect, zNear, zFar);
        }
        dirty = false;
    }

    public Vector3fc getCameraPos() {
        return cameraPos;
    }

    public Matrix4fc getCameraMatrix() {
        return cameraMatrix;
    }

    public Quaternionfc getRotation() {
        return rotation;
    }

    public Matrix4fc getRotationMatrix() {
        return rotationMatrix;
    }

    public void setType(CameraType type) {
        this.type = type;
        this.dirty = true;
    }

    public void setSize(float size) {
        this.size = size;
        this.dirty = true;
    }

    public void setFov(float fov) {
        this.fov = fov;
        this.dirty = true;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        this.dirty = true;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        this.dirty = true;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        this.dirty = true;
    }

    public void setViewportXY(Vector2f viewportXY) {
        this.viewportXY = viewportXY;
        this.dirty = true;
    }

    public void setViewportWH(Vector2f viewportWH) {
        this.viewportWH = viewportWH;
        this.dirty = true;
    }

    public void setDepth(float depth) {
        this.depth = depth;
        this.dirty = true;
    }

    public float getSize() {
        return size;
    }

    public float getFov() {
        return fov;
    }

    public float getAspect() {
        return aspect;
    }

    public float getzNear() {
        return zNear;
    }

    public float getzFar() {
        return zFar;
    }

    public Vector2fc getViewportXY() {
        return viewportXY;
    }

    public Vector2fc getViewportWH() {
        return viewportWH;
    }

    public float getDepth() {
        return depth;
    }

    public Matrix4fc getProjection() {
        return projection;
    }
}
