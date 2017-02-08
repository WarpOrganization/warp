package pl.warp.engine.graphics.shader.program.particle.textured;

import org.joml.Matrix4f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.shader.GeometryProgram;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 14
 */
public class TexturedParticleProgram extends GeometryProgram {

    private static final int SPRITE_SHEET_SAMPLER = 0;

    public static final int POSITION_ATTR = 0;
    public static final int ROTATION_ATTR = 1;
    public static final int TEXTURE_INDEX_ATTR = 2;

    private static final String PROGRAM_NAME = "particle/textured";

    private int unifModelViewMatrix;
    private int unifProjectionMatrix;
    private int unifCameraRotationMatrix;

    private Matrix4f cameraMatrix;
    private Matrix4f modelMatrix;

    public TexturedParticleProgram() {
        super(PROGRAM_NAME);
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
        cameraMatrix.mul(modelMatrix, tmpResultMatrix);
        setUniformMatrix4(unifModelViewMatrix, tmpResultMatrix);
    }

    public void useSpriteSheet(Texture2DArray spriteSheet) {
        useTexture(spriteSheet, SPRITE_SHEET_SAMPLER);
    }
}
