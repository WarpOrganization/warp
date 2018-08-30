package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.Transforms;
import org.joml.*;

import java.lang.Math;

/**
 * @author MarconZet
 * Created 24.08.2018
 */
public class CameraProperty extends Property {
    private Matrix4f projectionMatrix;
    private Matrix4f uiProjectionMatrix;
    private boolean dirty;

    public enum CameraType {
        ORTHOGRAPHIC,
        PERSPECTIVE

    }
    private CameraType cameraType;

    private float size;
    private float fov;

    private int width;
    private int height;
    private float aspect;

    private float zNear;
    private float zFar;

    private Vector2f viewportXY;//TODO viewport rectangle
    private Vector2f viewportWH;

    private float depth;


    public CameraProperty(CameraType type, float value, int width, int height, float zNear, float zFar){
        this.projectionMatrix = new Matrix4f();
        this.uiProjectionMatrix = new Matrix4f();
        this.dirty = true;
        this.cameraType = type;
        this.size = value;
        this.fov = value;
        this.width = width;
        this.height = height;
        this.aspect = (float)width/height;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public void init() {
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
        switch (cameraType){
            case ORTHOGRAPHIC: projectionMatrix.ortho(-size, size, -size, size, zNear, zFar);
            case PERSPECTIVE: projectionMatrix.perspective(fov*(float)Math.PI/180, aspect, zNear, zFar);
        }
        uiProjectionMatrix.ortho2D(0, width, 0, height);
        dirty = false;
    }

    public Vector2f getPositionOnCanvas(Component component){
        Matrix4f transform = Transforms.getAbsoluteTransform(component, new Matrix4f());
        Vector4f vector = new Vector4f(0, 0, 0, 1).mul(transform).mul(cameraMatrix).mul(projectionMatrix);
        if(vector.z < 0)
            return new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
        return new Vector2f((width >> 1) * (1 + vector.x/vector.w), (height >> 1) * (1 + vector.y/vector.w));
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

    public CameraType getCameraType() {
        return cameraType;
    }

    public void setCameraType(CameraType cameraType) {
        this.cameraType = cameraType;
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

    public Matrix4fc getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4fc getUiProjectionMatrix() {
        return uiProjectionMatrix;
    }


}
