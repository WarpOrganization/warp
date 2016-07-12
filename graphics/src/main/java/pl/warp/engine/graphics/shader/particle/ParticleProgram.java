package pl.warp.engine.graphics.shader.particle;

import org.joml.Matrix4f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.identitymultisample.IdentityMultisampleProgram;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 14
 */
public class ParticleProgram extends Program {

    public static final int POSITION_ATTR = 0;
    public static final int ROTATION_ATTR = 1;
    public static final int TEXTURE_INDEX_ATTR = 2;

    private static final InputStream FRAGMENT_SHADER = ParticleProgram.class.getResourceAsStream("frag.glsl");
    private static final InputStream VERTEX_SHADER = ParticleProgram.class.getResourceAsStream("vert.glsl");
    private static final String[] OUT_NAMES = {};

    private int unifModelViewMatrix;
    private int projectionMatrix;

    private Matrix4f cameraMatrix;
    private Matrix4f modelMatrix;

    public ParticleProgram() {
        super(FRAGMENT_SHADER, VERTEX_SHADER, OUT_NAMES);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifModelViewMatrix = getUniformLocation("modelViewMatrix");
        this.projectionMatrix = getUniformLocation("projectionMatrix");
    }

    public void useMatrixStack(MatrixStack stack) {
        this.modelMatrix = stack.topMatrix();
        setModelViewMatrix();
    }

    public void useCamera(Camera camera) {
        this.cameraMatrix = camera.getCameraMatrix();
        setUniformMatrix4(projectionMatrix, camera.getProjectionMatrix());
    }

    private Matrix4f tmpResultMatrix = new Matrix4f();

    private void setModelViewMatrix() {
        modelMatrix.mul(cameraMatrix, tmpResultMatrix);
        setUniformMatrix4(unifModelViewMatrix, tmpResultMatrix);
    }

}
