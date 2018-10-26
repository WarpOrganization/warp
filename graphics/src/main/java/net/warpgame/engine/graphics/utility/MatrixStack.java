package net.warpgame.engine.graphics.utility;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MatrixStack {

    private static final int DEFAULT_DEPTH = 32;

    private int size;
    private Matrix4f[] composeMatrixStack;
    private Matrix3f[] rotationMatrixStack;
    private int top = 0;

    public MatrixStack(int size) {
        this.size = size;
        this.composeMatrixStack = new Matrix4f[size];
        this.rotationMatrixStack = new Matrix3f[size];
        fill();
    }

    public MatrixStack() {
        this(DEFAULT_DEPTH);
    }

    private void fill() {
        for (int i = 0; i < this.size; i++) {
            composeMatrixStack[i] = new Matrix4f();
            rotationMatrixStack[i] = new Matrix3f();
        }
    }

    /**
     * Pushes a new matrix onto stack.
     */
    public void push() {
        composeMatrixStack[++top].set(composeMatrixStack[top - 1]);
        rotationMatrixStack[top].set(rotationMatrixStack[top - 1]);
    }

    /**
     * Pops the value from the stack.
     */
    public void pop() {
        top--;
    }

    private Vector3f tempVector = new Vector3f();

    /**
     * Translates the topMatrix matrix of the stack.
     *
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        tempVector.set(x, y, z);
        composeMatrixStack[top].translate(tempVector);
    }

    /**
     * Translates the topMatrix matrix of the stack.
     *
     * @param vector
     */
    public void translate(Vector3fc vector) {
        translate(vector.x(), vector.y(), vector.z());
    }

    /**
     * Rotates the topMatrix matrix of the stack.
     *
     * @param rad Rotation angle in radians.
     * @param x
     * @param y
     * @param z
     */
    public void rotate(float rad, float x, float y, float z) {
        tempVector.set(x, y, z);
        composeMatrixStack[top].rotate(rad, tempVector);
        rotationMatrixStack[top].rotate(rad, tempVector);
    }

    public void rotate(Quaternionfc quaternion) {
        composeMatrixStack[top].rotate(quaternion);
        rotationMatrixStack[top].rotate(quaternion);
    }

    /**
     * Scales the topMatrix matrix of the stack.
     *
     * @param x X scale.
     * @param y Y scale.
     * @param z Z scale.
     */
    public void scale(float x, float y, float z) {
        tempVector.set(x, y, z);
        composeMatrixStack[top].scale(tempVector);
    }

    public void scale(Vector3fc scale) {
        composeMatrixStack[top].scale(scale);
    }

    /**
     * Multiplies the top stack by @matrix.
     * IT DOESN'T AFFECT THE ROTATION MATRIX.
     *
     * @param matrix
     */
    public void mul(Matrix4fc matrix) {
        composeMatrixStack[top].mul(matrix, composeMatrixStack[top]);
    }

    public void setTop(Matrix4fc topMatrix) {
        topMatrix().set(topMatrix);
    }

    public void setTopRotation(Matrix3fc rotationMatrix) {
        topRotationMatrix().set(rotationMatrix);
    }

    /**
     * @return A direct FloatBuffer containing the topMatrix matrix.
     */
    public FloatBuffer topBuff() {
        return topMatrix().get(BufferUtils.createFloatBuffer(16));
    }

    /**
     * @return The topMatrix matrix.
     */
    public Matrix4f topMatrix() {//TODO Matrix4fc ??
        return composeMatrixStack[top];
    }

    public void storeTopBuffer(FloatBuffer dest) {
        composeMatrixStack[top].get(dest);
    }

    /**
     * @return A direct FloatBuffer containing a rotation matrix of the topMatrix matrix.
     */
    public FloatBuffer topRotationBuff() {
        return topRotationMatrix().get(BufferUtils.createFloatBuffer(9));
    }

    /**
     * @return A rotation matrix of the topMatrix matrix.
     */
    public Matrix3f topRotationMatrix() {//TODO Matrix3fc ??
        return rotationMatrixStack[top];
    }

    public void storeRotationBuffer(FloatBuffer dest) {
        rotationMatrixStack[top].get(dest);
    }

}
