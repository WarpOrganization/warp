package pl.warp.engine.graphics.rendering.screenspace.program;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBuffer;
import pl.warp.engine.graphics.rendering.screenspace.light.LightSource;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class ScreenspaceProgram extends Program {

    private int sources;
    private int uLightNumber;
    private int[] uLightSourcePositions;
    private int[] uLightSourceColors;

    public ScreenspaceProgram(Config config) {
        super(new ProgramAssemblyInfo("identity"), new ExtendedGLSLProgramCompiler(
                new ConstantField()
                        .set("MAX_LIGHTS", config.getValue("graphics.rendering.maxlights")),
                LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER
        ));
        this.sources = config.getValue("graphics.rendering.maxlights");
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
        this.uLightNumber = getUniformLocation("lightNumber");
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

    public void useLights(Vector3f[] positions, LightSource[] sources) {
        setUniformi(uLightNumber, positions.length);
        for(int i = 0; i < positions.length; i++) {
            setUniformV3(uLightSourcePositions[i], positions[i]);
            setUniformV3(uLightSourceColors[i], sources[i].getColor());
        }
    }
}
