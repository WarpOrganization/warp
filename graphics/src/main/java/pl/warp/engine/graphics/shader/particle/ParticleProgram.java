package pl.warp.engine.graphics.shader.particle;

import org.joml.Matrix4f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.GeometryProgram;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 14
 */
public class ParticleProgram extends GeometryProgram {

    public static final int POSITION_ATTR = 0;
    public static final int ROTATION_ATTR = 1;
    public static final int TEXTURE_INDEX_ATTR = 2;

    private static final InputStream FRAGMENT_SHADER = ParticleProgram.class.getResourceAsStream("frag.glsl");
    private static final InputStream VERTEX_SHADER = ParticleProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream GEOMETRY_SHADER = ParticleProgram.class.getResourceAsStream("geom.glsl");
    private static final String[] OUT_NAMES = {"fragColor"};

    private int unifModelViewMatrix;
    private int unifProjectionMatrix;
    private int unifCameraRotationMatrix;

    private Matrix4f cameraMatrix;
    private Matrix4f modelMatrix;

    public ParticleProgram() {
        super(GEOMETRY_SHADER, VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifModelViewMatrix = getUniformLocation("modelViewMatrix");
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifCameraRotationMatrix = getUniformLocation("cameraRotationMatrix");
    }

    public void useMatrixStack(MatrixStack stack) {
        this.modelMatrix = stack.topMatrix();
        setModelViewMatrix();
        if(cameraMatrix != null) setModelViewMatrix();
    }

    public void useCamera(Camera camera) {
        this.cameraMatrix = camera.getCameraMatrix();
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix());
        setUniformMatrix4(unifCameraRotationMatrix, camera.getRotationMatrix());
        if(modelMatrix != null) setModelViewMatrix();
    }

    private Matrix4f tmpResultMatrix = new Matrix4f();
    private void setModelViewMatrix() {
        cameraMatrix.mul(modelMatrix, tmpResultMatrix);
        setUniformMatrix4(unifModelViewMatrix, tmpResultMatrix);
    }

}
