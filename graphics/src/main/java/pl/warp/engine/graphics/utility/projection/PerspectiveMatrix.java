package pl.warp.engine.graphics.utility.projection;

import org.joml.Matrix4f;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class PerspectiveMatrix implements ProjectionMatrix {
    private float fov;
    private float zNear, zFar;
    private float aspectRatio;
    private Matrix4f matrix;

    public PerspectiveMatrix(float fovInDegrees, float zNear, float zFar, int width, int height) {
        this.fov = toRadians(fovInDegrees);
        this.aspectRatio = ((float) width) / height;
        this.zFar = zFar;
        this.zNear = zNear;
        recalcMatrix();
    }

    private void recalcMatrix() {
        float ymax, xmax;
        ymax = (float) (zNear * Math.tan(fov));
        xmax = ymax * aspectRatio;
        this.matrix = frustum(-xmax, xmax, -ymax, ymax);
    }

    private float toRadians(float degrees) {
        return (float) (degrees * Math.PI / 360.0);
    }


    private Matrix4f frustum(float left, float right, float bottom, float top) {
        Matrix4f matrix = new Matrix4f();

        matrix.m00 = 2 * zNear / (right - left);
        matrix.m11 = 2 * zNear / (top - bottom);
        matrix.m22 = -(zFar + zNear) / (zFar - zNear);
        matrix.m23 = -1;
        matrix.m32 = -2 * zFar * zNear / (zFar - zNear);
        matrix.m20 = (right + left) / (right - left);
        matrix.m21 = (top + bottom) / (top - bottom);
        matrix.m33 = 0;

        return matrix;
    }

    public float getFovInRadians() {
        return fov;
    }

    public void setFov(float fovInDegrees) {
        this.fov = toRadians(fovInDegrees);
        recalcMatrix();
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        recalcMatrix();
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        recalcMatrix();
    }

    public void setDims(int w, int h) {
        this.aspectRatio = ((float) w) / h;
        recalcMatrix();
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }
}
