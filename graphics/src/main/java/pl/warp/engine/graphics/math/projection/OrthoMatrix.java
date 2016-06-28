package pl.warp.engine.graphics.math.projection;


import org.joml.Matrix4f;

/**
 * @author Jaca777
 *         Created 2015-08-02 at 13
 */
public class OrthoMatrix implements ProjectionMatrix {
    private float right, left;
    private float top, bottom;
    private float far, near;

    private Matrix4f matrix;

    public OrthoMatrix(float right, float left, float top, float bottom, float far, float near) {
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        this.far = far;
        this.near = near;
        this.matrix = new Matrix4f();
        recalcMatrix();
    }

    private void recalcMatrix() {
        this.matrix.identity();
        this.matrix.m00 = 2f / (right - left);
        this.matrix.m11 = 2f / (top - bottom);
        this.matrix.m22 = 2f / (far - near);
        this.matrix.m30 = -((right + left) / (right - left));
        this.matrix.m31 = -((top + bottom) / (top - bottom));
        this.matrix.m32 = -((far + near) / (far - near));
    }

    public void setRight(float right) {
        this.right = right;
        recalcMatrix();
    }

    public void setLeft(float left) {
        this.left = left;
        recalcMatrix();
    }

    public void setTop(float top) {
        this.top = top;
        recalcMatrix();
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
        recalcMatrix();
    }

    public void setFar(float far) {
        this.far = far;
        recalcMatrix();
    }

    public void setNear(float near) {
        this.near = near;
        recalcMatrix();
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public float getRight() {
        return right;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    public float getFar() {
        return far;
    }

    public float getNear() {
        return near;
    }
}
