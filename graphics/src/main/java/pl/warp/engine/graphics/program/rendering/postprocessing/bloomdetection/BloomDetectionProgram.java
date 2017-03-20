package pl.warp.engine.graphics.program.rendering.postprocessing.bloomdetection;

import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-21 at 18
 */
public class BloomDetectionProgram extends Program {

    private static String PROGRAM_NAME = "postprocessing/bloomdetection";

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int unifThreshold;
    private int unifCutOff;

    public BloomDetectionProgram() {
        super(PROGRAM_NAME); compile();
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifThreshold = getUniformLocation("threshold");
        this.unifCutOff = getUniformLocation("curOff");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }

    public void setCutOffEnabled(boolean enabled) {
        setUniformb(unifCutOff, enabled);
    }

    public void setThreshold(float threshold) {
        setUniformf(unifThreshold, threshold);
    }

}
