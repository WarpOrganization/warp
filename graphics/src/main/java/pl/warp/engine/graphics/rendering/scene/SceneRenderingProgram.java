package pl.warp.engine.graphics.rendering.scene;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 22
 */
public class SceneRenderingProgram extends Program {

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifViewMatrix;


    public SceneRenderingProgram() {
        super("scene", ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifViewMatrix = getUniformLocation("viewMatrix");
    }


    public void useCamera(Camera camera) {
        setUniformMatrix4(unifViewMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
    }

    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix3(unifRotationMatrix, stack.topRotationMatrix());
    }

}
