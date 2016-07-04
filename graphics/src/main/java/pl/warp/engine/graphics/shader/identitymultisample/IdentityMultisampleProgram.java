package pl.warp.engine.graphics.shader.identitymultisample;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.Texture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-03 at 22
 */
public class IdentityMultisampleProgram extends Program {
    private static InputStream FRAGMENT_SHADER = IdentityMultisampleProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = IdentityMultisampleProgram.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;
    private static final int TEXTURE_SAMPLER = 0;

    private int unifSamples;

    public IdentityMultisampleProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.unifSamples = getUniformLocation("samples");
    }

    public void useTexture(MultisampleTexture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
        setUniformi(unifSamples, texture.getSamples());
    }
}
