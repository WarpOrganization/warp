package pl.warp.engine.graphics.program.rendering.identity;

import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 19
 */
public class IdentityProgram extends Program {

    private static final String PROGRAM_NAME = "identity";

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int unifExposure;

    public IdentityProgram() {
        super(PROGRAM_NAME);
        this.unifExposure = getUniformLocation("exposure");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }

    public void setExposure(float exposure) {
        setUniformf(unifExposure, exposure);
    }
}
