package pl.warp.engine.graphics.shader.identity;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.Texture;
import pl.warp.engine.graphics.texture.Texture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 19
 */
public class IdentityProgram extends Program {

    private static InputStream FRAGMENT_SHADER = IdentityProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = IdentityProgram.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    public IdentityProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }
}
