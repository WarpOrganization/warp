package pl.warp.engine.graphics.rendering.scene;

import org.joml.Vector3f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 22
 */
public class SceneRenderingProgram extends Program {

    private static final int DIFFUSE_SAMPLER = 0;
    private static final int DISPLACEMENT_SAMPLER = 1;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifViewMatrix;
    private int unifCameraPos;
    private int unifDisplacementEnabled;
    private int unifDisplacementFactor;


    public SceneRenderingProgram() {
        super("scene", ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        loadUniforms();
        loadSamplers();
    }


    private void loadUniforms() {
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifViewMatrix = getUniformLocation("viewMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifDisplacementFactor = getUniformLocation("displacementFactor");
        this.unifDisplacementEnabled = getUniformLocation("displacementEnabled");
    }

    private void loadSamplers() {
        setTextureLocation("diffuseTexture", DIFFUSE_SAMPLER);
        setTextureLocation("displacementMap", DISPLACEMENT_SAMPLER);
    }


    private Vector3f tempCameraPos = new Vector3f();

    public void useCamera(Camera camera) {
        setUniformMatrix4(unifViewMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformV3(unifCameraPos, camera.getPosition(tempCameraPos));
    }

    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix3(unifRotationMatrix, stack.topRotationMatrix());
    }

    public void useMaterial(Material material) {
        useTexture(DIFFUSE_SAMPLER, material.getDiffuseTexture());
        if(material.hasDisplacementMap()) {
            useTexture(DISPLACEMENT_SAMPLER, material.getDisplacementMap());
            setUniformf(unifDisplacementFactor, material.getDisplacementFactor());
            setUniformb(unifDisplacementEnabled, true);
        } else {
            setUniformb(unifDisplacementEnabled, false);
        }
    }

}
