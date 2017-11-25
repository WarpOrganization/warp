package pl.warp.engine.graphics.rendering.screenspace.program;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBuffer;
import pl.warp.engine.graphics.rendering.screenspace.light.LightSource;
import pl.warp.engine.graphics.rendering.screenspace.light.LightSourceProperty;

import java.util.List;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class ScreenspaceProgram extends Program {

    private int sources;
    private int uLightNumber;
    private int[] uLightSourcePositions;
    private int[] uLightSourceColors;
    private int uInverseProjection;
    private int uInverseCamera;
    private int uCameraPos;

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
        setTextureLocation("comp1", 0);
        setTextureLocation("comp2", 1);
        setTextureLocation("comp3", 2);
        setTextureLocation("comp4", 3);
        loadUniforms();
    }

    private void loadUniforms() {
        this.uInverseProjection = getUniformLocation("inverseProjection");
        this.uCameraPos = getUniformLocation("cameraPos");
        this.uLightNumber = getUniformLocation("lightNumber");
        this.uInverseCamera = getUniformLocation("inverseCamera");
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

    public void useLights(List<Vector3f> positions, List<LightSourceProperty> sources) {
        setUniformi(uLightNumber, positions.size());
        for(int i = 0; i < positions.size(); i++) {
            setUniformV3(uLightSourcePositions[i], positions.get(i));
            LightSource lightSource = sources.get(i).getLightSource();
            setUniformV3(uLightSourceColors[i], lightSource.getColor());
        }
    }

    private Matrix4f mat = new Matrix4f();
    private Vector3f cameraPos = new Vector3f();
    public void useCamera(Camera camera) {
        camera.getPosition(cameraPos);
        setUniformV3(uCameraPos, cameraPos);
        camera.getProjectionMatrix()
                .getMatrix()
                .invert(mat);
        setUniformMatrix4(uInverseProjection, mat);
        camera.getCameraMatrix().invert(mat);
        setUniformMatrix4(uInverseCamera, mat);
    }

}
