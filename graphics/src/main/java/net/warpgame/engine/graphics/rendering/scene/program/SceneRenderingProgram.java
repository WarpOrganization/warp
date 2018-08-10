package net.warpgame.engine.graphics.rendering.scene.program;

import net.warpgame.engine.graphics.animation.AnimatedModel;
import net.warpgame.engine.graphics.animation.AnimatedModelProperty;
import net.warpgame.engine.graphics.camera.Camera;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import net.warpgame.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import net.warpgame.engine.graphics.utility.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2017-09-25 at 16
 */
public class SceneRenderingProgram extends Program {
    public static final int MAX_JOINTS = 50;
    private static final int MAX_WEIGHTS = 3;

    private static final int DIFFUSE_SAMPLER = 0;
    private static final int NORMAL_MAP_SAMPLER = 1;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifViewMatrix;
    private int unifCameraPos;
    private int unifMaterialShininess;
    private int unifMaterialRoughness;
    private int unifHasNormalMap;
    private int unifAnimated;
    private int unifJointTransforms[];

    public SceneRenderingProgram() {
        super(new ProgramAssemblyInfo("scene"),
                new ExtendedGLSLProgramCompiler(
                        new ConstantField()
                                .set("MAX_JOINTS", MAX_JOINTS)
                                .set("MAX_WEIGHTS", MAX_WEIGHTS),
                        LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER)
        );
        loadUniforms();
        loadSamplers();
    }

    protected void loadUniforms() {
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifViewMatrix = getUniformLocation("viewMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifMaterialShininess = getUniformLocation("materialShininess");
        this.unifMaterialRoughness = getUniformLocation("materialRoughness");
        this.unifHasNormalMap = getUniformLocation("hasNormalMap");
        this.unifAnimated = getUniformLocation("animated");
        this.unifJointTransforms = new int[MAX_JOINTS];
        for (int i = 0; i < MAX_JOINTS; i++) {
            this.unifJointTransforms[i] = getUniformLocation("jointTransforms[" + i + "]");
        }
    }


    protected void loadSamplers() {
        setTextureLocation("diffuseTexture", DIFFUSE_SAMPLER);
        setTextureLocation("normalMap", NORMAL_MAP_SAMPLER);
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
        setUniformf(unifMaterialShininess, material.getShininess());
        setUniformf(unifMaterialRoughness, material.getRoughness());
        setUniformb(unifHasNormalMap, material.hasNormalMap());
        if(material.hasNormalMap()) {
            useTexture(NORMAL_MAP_SAMPLER, material.getNormalMap());
        }
    }

    public void useAnimationData(AnimatedModelProperty animatedModelProperty) {
        if(animatedModelProperty == null) {
            setUniformb(unifAnimated, false);
        } else {
            setUniformb(unifAnimated, true);
            AnimatedModel animatedModel = animatedModelProperty.getAnimatedModel();
            Matrix4f[] jointTransforms = animatedModel.getJointTransforms();
            for(int i = 0; i < jointTransforms.length; i++) {
                setUniformMatrix4(unifJointTransforms[i], jointTransforms[i]);
            }
        }
    }
}
