package pl.warp.engine.graphics.program.rendering.particle.dot;

import org.joml.Matrix4f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.program.GeometryProgram;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 14
 */
public class DotParticleProgram extends GeometryProgram {

    public static final int POSITION_ATTR = 0;
    public static final int COLOR_ATTR = 1;
    public static final int GRADIENT_ATTR = 2;
    public static final int SCALE_ATTR = 3;

    private static final String PROGRAM_NAME = "particle/dot";

    private int unifModelViewMatrix;
    private int unifProjectionMatrix;
    private int unifCameraRotationMatrix;

    private Matrix4f cameraMatrix;
    private Matrix4f modelMatrix;

    public DotParticleProgram() {
        super(PROGRAM_NAME); compile();
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifModelViewMatrix = getUniformLocation("modelViewMatrix");
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifCameraRotationMatrix = getUniformLocation("cameraRotationMatrix");
    }

    public void useMatrix(Matrix4f matrix) {
        this.modelMatrix = matrix;
        setModelViewMatrix();
        if (cameraMatrix != null) setModelViewMatrix();
    }

    public void useCamera(Camera camera) {
        this.cameraMatrix = camera.getCameraMatrix();
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformMatrix4(unifCameraRotationMatrix, camera.getRotationMatrix());
        if (modelMatrix != null) setModelViewMatrix();
    }

    private Matrix4f tmpResultMatrix = new Matrix4f();

    private void setModelViewMatrix() {
        Matrix4f modelViewMatrix = cameraMatrix.mul(modelMatrix, tmpResultMatrix);
        setUniformMatrix4(unifModelViewMatrix, modelViewMatrix);
    }
}
