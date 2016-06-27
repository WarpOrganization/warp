package pl.warp.engine.graphics.shaders;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 19
 */
public class IdentityShader extends Program {

    private static InputStream FRAGMENT_SHADER = IdentityShader.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = IdentityShader.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    private int attrVertex;
    private int attrTexCoord;

    public IdentityShader() {
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
}
