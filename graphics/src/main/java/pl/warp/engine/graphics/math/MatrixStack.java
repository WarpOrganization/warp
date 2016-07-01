package pl.warp.engine.graphics.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import pl.warp.engine.graphics.utility.BufferTools;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MatrixStack {
    private static final int MATRIX = 0;
    private static final int R_MATRIX = 1;

    private static final int DEFAULT_DEPTH = 32;

    private int size;
    private Matrix4f[][] matrixStack;
    private int top = 0;

    public MatrixStack(int size) {
        this.size = size;
        this.matrixStack = new Matrix4f[size][2];
        this.matrixStack[top][MATRIX] = new Matrix4f();
        this.matrixStack[top][R_MATRIX] = new Matrix4f();
        fill();
    }

    public MatrixStack() {
        this(DEFAULT_DEPTH);
    }

    private void fill(){
        for(int i = 0; i < this.size; i++) {
            matrixStack[i][MATRIX] = new Matrix4f();
            matrixStack[i][R_MATRIX] = new Matrix4f();
        }
    }

    /**
     * Pushes a new matrix onto stack.
     */
    public void push() {
        matrixStack[++top][MATRIX].set(matrixStack[top - 1][MATRIX]);
        matrixStack[top][R_MATRIX].set(matrixStack[top - 1][R_MATRIX]);
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
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        tempVector.set(x,y,z);
        matrixStack[top][MATRIX].translate(tempVector);
    }

    /**
     * Translates the topMatrix matrix of the stack.
     * @param vector
     */
    public void translate(Vector3f vector) {
        translate(vector.x, vector.y, vector.z);
    }

    /**
     * Rotates the topMatrix matrix of the stack.
     * @param rad Rotation angle in radians.
     * @param x
     * @param y
     * @param z
     */
    public void rotate(float rad, float x, float y, float z) {
        tempVector.set(x,y,z);
        matrixStack[top][MATRIX].rotate(rad, tempVector);
        matrixStack[top][R_MATRIX].rotate(rad, tempVector);
    }

    public void rotate(Quaternionf quaternion) {
        matrixStack[top][MATRIX].rotate(quaternion);
        matrixStack[top][R_MATRIX].rotate(quaternion);
    }

    /**
     * Scales the topMatrix matrix of the stack.
     * @param x X scale.
     * @param y Y scale.
     * @param z Z scale.
     */
    public void scale(float x, float y, float z) {
        tempVector.set(x, y, z);
        matrixStack[top][MATRIX].scale(tempVector);
    }

    public void scale(Vector3f scale) {
        matrixStack[top][MATRIX].scale(scale);
    }

    /**
     * Multiplies the top stack by @matrix.
     * IT DOESN'T AFFECT THE ROTATION MATRIX.
     * @param matrix
     */
    public void mul(Matrix4f matrix){
        matrixStack[top][MATRIX].mul(matrix, matrixStack[top][MATRIX]);
    }
    /**
     * @return A direct FloatBuffer containing the topMatrix matrix.
     */
    public FloatBuffer topBuff() {
        return BufferTools.toDirectBuffer(topMatrix());
    }

    /**
     * @return The topMatrix matrix.
     */
    public Matrix4f topMatrix() {
        return matrixStack[top][MATRIX];
    }

    public void storeTopBuffer(FloatBuffer dest) {
        matrixStack[top][MATRIX].get(dest);
    }

    /**
     * @return A direct FloatBuffer containing a rotation matrix of the topMatrix matrix.
     */
    public FloatBuffer topRotationBuff() {
        return BufferTools.toDirectBuffer(topRotationMatrix());
    }

    /**
     * @return A rotation matrix of the topMatrix matrix.
     */
    public Matrix4f topRotationMatrix(){
        return matrixStack[top][R_MATRIX];
    }

    public void storeRotationBuffer(FloatBuffer dest) {
        matrixStack[top][R_MATRIX].get(dest);
    }

    /**
     * Immutable MatrixStack containing only one, identity matrix.
     */
    private static final MatrixStack IDENTITY_STACK = new MatrixStack(1){
        @Override
        public void push() {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.push()");
        }

        @Override
        public void pop() {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.pop()");
        }

        @Override
        public void translate(float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.translate(x,y,z)");
        }

        @Override
        public void translate(Vector3f vector) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.translate(vector)");
        }

        @Override
        public void scale(float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.scale(x,y,z)");
        }

        @Override
        public void rotate(float rad, float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.rotate(x,y,z)");
        }

        @Override
        public void mul(Matrix4f matrix) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.rotate(matrix)");
        }
    };

}
