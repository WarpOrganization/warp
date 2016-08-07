package pl.warp.engine.graphics.shader.program.identitymultisample;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.program.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-03 at 22
 */
public class IdentityMultisampleProgram extends Program {
    private static final String PROGRAM_NAME = "identitymultisample";

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;
    private static final int TEXTURE_SAMPLER = 0;

    private int unifSamples;

    public IdentityMultisampleProgram() {
        super(PROGRAM_NAME);
        this.unifSamples = getUniformLocation("samples");
    }

    public void useTexture(MultisampleTexture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
        setUniformi(unifSamples, texture.getSamples());
    }
}
