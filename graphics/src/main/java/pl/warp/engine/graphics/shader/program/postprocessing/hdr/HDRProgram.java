package pl.warp.engine.graphics.shader.program.postprocessing.hdr;

import pl.warp.engine.graphics.postprocessing.WeightedTexture2D;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.extendedglsl.LocalProgramLoader;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class HDRProgram extends Program {
    private static final int MAX_SAMPLERS = 15;

    private static final String VERTEX_SHADER = "postprocessing/hdr/vert";
    private static final String FRAGMENT_SHADER = "postprocessing/hdr/frag";
    private static final int SCENE_TEXTURE_SAMPLER = 0;
    private static final int BLOOM_TEXTURE_SAMPLER = 1;

    private static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_SAMPLERS", MAX_SAMPLERS);

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int[] unifSamplers = new int[MAX_SAMPLERS];
    private int[] unifWeights = new int[MAX_SAMPLERS];
    private int unifSamplersNumber;
    private int unifExposure;

    public HDRProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, new ExtendedGLSLProgramCompiler(CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
        loadUniforms();
        setupSamplers();
    }

    private void loadUniforms() {
        this.unifExposure = getUniformLocation("exposure");
        for(int i = 0; i < MAX_SAMPLERS; i++){
            this.unifSamplers[i] = getUniformLocation("samplers[" + i + "]");
            this.unifWeights[i] = getUniformLocation("weights[" + i + "]");
            this.unifSamplersNumber = getUniformLocation("samplersNumber");
        }
    }

    private void setupSamplers() {
        for(int i = 0; i < MAX_SAMPLERS; i++){
            setUniformi(unifSamplers[i], i);
        }
    }

    public void useTextures(WeightedTexture2D[] textures) {
        setUniformi(unifSamplersNumber,  textures.length);
        for(int i = 0; i < textures.length; i++){
            WeightedTexture2D texture = textures[i];
            useTexture(texture.getTexture(), i);
            setUniformf(unifWeights[i], texture.getWeight());
        }
    }

    public void setExposure(float exposure) {
        setUniformf(unifExposure, exposure);
    }
}
