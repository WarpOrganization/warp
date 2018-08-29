package net.warpgame.engine.graphics.rendering.screenspace.program;

import net.warpgame.engine.graphics.camera.CameraProperty;
import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import net.warpgame.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import net.warpgame.engine.graphics.rendering.scene.gbuffer.GBuffer;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSource;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import net.warpgame.engine.graphics.texture.Cubemap;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class ScreenspaceProgram extends Program {

    private static final int CUBEMAP_SAMPLER = 4;

    private int sources;
    private int uLightNumber;
    private int[] uLightSourcePositions;
    private int[] uLightSourceColors;
    private int uInverseProjection;
    private int uInverseCamera;
    private int uCameraPos;
    private int uCameraRotation;

    public ScreenspaceProgram(int maxLights) {
        super(new ProgramAssemblyInfo("screenspace"), new ExtendedGLSLProgramCompiler(
                new ConstantField()
                        .set("MAX_LIGHTS", maxLights),
                LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER
        ));
        this.sources = maxLights;
        init();
    }

    public void init() {
        loadSamplers();
        loadUniforms();
    }

    protected void loadSamplers() {
        setTextureLocation("comp1", 0);
        setTextureLocation("comp2", 1);
        setTextureLocation("comp3", 2);
        setTextureLocation("comp4", 3);
        setTextureLocation("cube",4);
    }

    private void loadUniforms() {
        this.uInverseProjection = getUniformLocation("inverseProjection");
        this.uCameraPos = getUniformLocation("cameraPos");
        this.uLightNumber = getUniformLocation("lightNumber");
        this.uInverseCamera = getUniformLocation("inverseCamera");
        this.uCameraRotation = getUniformLocation("rotationCamera");
        this.uLightSourceColors = new int[sources];
        this.uLightSourcePositions = new int[sources];
        for (int i = 0; i < sources; i++) {
            this.uLightSourcePositions[i] = getUniformLocation("sources[" + i + "].pos");
            this.uLightSourceColors[i] = getUniformLocation("sources[" + i + "].color");
        }
    }

    public void useGBuffer(GBuffer gBuffer) {
        for (int i = 0; i < 4; i++) {
            useTexture(i, gBuffer.getTextureName(i), GL11.GL_TEXTURE_2D);
        }
    }

    public void useCubemap(Cubemap cubemap){
        useTexture(CUBEMAP_SAMPLER, cubemap);
    }

    public void useLights(List<Vector3f> positions, List<LightSourceProperty> sources) {
        setUniformi(uLightNumber, positions.size());
        for(int i = 0; i < positions.size(); i++) {
            setUniformV3(uLightSourcePositions[i], positions.get(i));
            LightSource lightSource = sources.get(i).getLightSource();
            setUniformV3(uLightSourceColors[i], lightSource.getColor());
        }
    }

    private Matrix4f mat = new Matrix4f();

    public void useCamera(CameraProperty camera) {
        Vector3fc cameraPos = camera.getCameraPos();
        setUniformV3(uCameraPos, cameraPos);
        camera.getProjectionMatrix()
                .invert(mat);
        setUniformMatrix4(uInverseProjection, mat);
        camera.getCameraMatrix().invert(mat);
        setUniformMatrix4(uInverseCamera, mat);
        camera.getRotationMatrix().get(mat);
        setUniformMatrix4(uCameraRotation, mat);
    }

}
