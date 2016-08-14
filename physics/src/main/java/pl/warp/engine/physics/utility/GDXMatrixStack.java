package pl.warp.engine.physics.utility;



import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class GDXMatrixStack {
    private static final int MATRIX = 0;
    private static final int R_MATRIX = 1;

    private static final int DEFAULT_DEPTH = 32;

    private int size;
    private Matrix4[][] matrixStack;
    private int top = 0;

    public GDXMatrixStack(int size) {
        this.size = size;
        this.matrixStack = new Matrix4[size][2];
        this.matrixStack[top][MATRIX] = new Matrix4();
        this.matrixStack[top][R_MATRIX] = new Matrix4();
        fill();
    }

    public GDXMatrixStack() {
        this(DEFAULT_DEPTH);
    }

    private void fill() {
        for (int i = 0; i < this.size; i++) {
            matrixStack[i][MATRIX] = new Matrix4();
            matrixStack[i][R_MATRIX] = new Matrix4();
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

    private Vector3 tempVector = new Vector3();

    /**
     * Translates the topMatrix matrix of the stack.
     *
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        tempVector.set(x, y, z);
        matrixStack[top][MATRIX].translate(tempVector);
    }

    /**
     * Translates the topMatrix matrix of the stack.
     *
     * @param vector
     */
    public void translate(Vector3 vector) {
        translate(vector.x, vector.y, vector.z);
    }


    public void rotate(Quaternion quaternion) {
        matrixStack[top][MATRIX].rotate(quaternion);
        matrixStack[top][R_MATRIX].rotate(quaternion);
    }

    /**
     * Scales the topMatrix matrix of the stack.
     *
     * @param x X scale.
     * @param y Y scale.
     * @param z Z scale.
     */
    public void scale(float x, float y, float z) {
        matrixStack[top][MATRIX].scale(x, y, z);
    }

    public void scale(Vector3 scale) {
        matrixStack[top][MATRIX].scale(scale.x, scale.y, scale.z);
    }

    /**
     * Multiplies the top stack by @matrix.
     * IT DOESN'T AFFECT THE ROTATION MATRIX.
     *
     * @param matrix
     */
    public void mul(Matrix4 matrix) {
        matrixStack[top][MATRIX].mul(matrix);
    }

    /**
     * @return The topMatrix matrix.
     */
    public Matrix4 topMatrix() {
        return matrixStack[top][MATRIX];
    }


    /**
     * @return A rotation matrix of the topMatrix matrix.
     */
    public Matrix4 topRotationMatrix() {
        return matrixStack[top][R_MATRIX];
    }
}
