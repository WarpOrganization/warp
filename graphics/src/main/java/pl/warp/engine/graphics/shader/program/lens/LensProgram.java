package pl.warp.engine.graphics.shader.program.lens;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.graphics.shader.GeometryProgram;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-29 at 13
 */
public class LensProgram extends GeometryProgram {
    private static InputStream FRAGMENT_SHADER = LensProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream GEOMETRY_SHADER = LensProgram.class.getResourceAsStream("geom.glsl");
    private static InputStream VERTEX_SHADER = LensProgram.class.getResourceAsStream("vert.glsl");

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_OFFSET = 0;
    public static final int ATTR_SCALE = 1;
    public static final int ATTR_TEXTURE_INDEX = 2;

    private int unifSourcePos;
    private int unifSourceColor;
    private int unifScreenSize;

    public LensProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, GEOMETRY_SHADER);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifSourcePos = getUniformLocation("sourcePos");
        this.unifSourceColor = getUniformLocation("sourceColor");
        this.unifScreenSize = getUniformLocation("screenSize");
    }

    public void useTexture(Texture2DArray texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }

    public void useSourcePos(Vector2f sourcePos) {
        setUniformV2(unifSourcePos, sourcePos);
    }

    public void useScreenSize(int width, int height){
        setUniformV2(unifScreenSize, width, height);
    }

    public void useSourceColor(Vector3f color) {
        setUniformV3(unifSourceColor, color);
    }
}
