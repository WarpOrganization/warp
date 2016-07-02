package pl.warp.engine.graphics.shader.identity;

import pl.warp.engine.graphics.shader.Program;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 19
 */
public class IdentityProgram extends Program {

    private static InputStream FRAGMENT_SHADER = IdentityProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = IdentityProgram.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    private int attrVertex;
    private int attrTexCoord;

    public IdentityProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.attrVertex = getAttributeLocation("inTexCoord");
        this.attrTexCoord = getAttributeLocation("inVertex");
    }

    public int getAttrVertex() {
        return attrVertex;
    }

    public int getAttrTexCoord() {
        return attrTexCoord;
    }

    public void useTexture(int texture) {

    }
}
