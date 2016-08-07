package pl.warp.engine.graphics.shader.program.postprocessing.hdr;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgram;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.texture.Texture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class HDRProgram extends Program {
    private static final String PROGRAM_NAME = "postprocessing/hdr";

    private static final int SCENE_TEXTURE_SAMPLER = 0;
    private static final int BLOOM_TEXTURE_SAMPLER = 1;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int unifBloomLevel;
    private int unifExposure;

    public HDRProgram() {
        super(PROGRAM_NAME);
        loadUniforms();
        setupSamplers();
    }

    private void loadUniforms() {
        this.unifBloomLevel = getUniformLocation("bloomLevel");
        this.unifExposure = getUniformLocation("exposure");
    }

    private void setupSamplers() {
        setUniformi(getUniformLocation("sceneTex"), SCENE_TEXTURE_SAMPLER);
        setUniformi(getUniformLocation("bloomTex"), BLOOM_TEXTURE_SAMPLER);
    }

    public void useSceneTexture(Texture2D texture) {
        useTexture(texture, SCENE_TEXTURE_SAMPLER);
    }

    public void useBloomTexture(Texture2D texture) {
        useTexture(texture, BLOOM_TEXTURE_SAMPLER);
    }

    public void setBloomLevel(float bloomLevel) {
        setUniformf(unifBloomLevel, bloomLevel);
    }

    public void setExposure(float exposure) {
        setUniformf(unifExposure, exposure);
    }
}
