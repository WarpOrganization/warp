package pl.warp.engine.graphics.shader.program.hdr;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.program.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.Texture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class HDRProgram extends Program {
    private static InputStream FRAGMENT_SHADER = HDRProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = HDRProgram.class.getResourceAsStream("vert.glsl");

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    public HDRProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }
}
