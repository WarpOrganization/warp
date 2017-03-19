package pl.warp.engine.graphics.program.rendering.lens;

import org.joml.Vector2f;
import pl.warp.engine.graphics.program.GeometryProgram;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2016-07-29 at 13
 */
public class LensProgram extends GeometryProgram {
    private static final String PROGRAM_NAME = "lens";

    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_OFFSET = 0;
    public static final int ATTR_SCALE = 1;
    public static final int ATTR_TEXTURE_INDEX = 2;
    public static final int ATTR_FLARE_COLOR = 3;

    private int unifSourcePos;
    private int unifScreenSize;

    public LensProgram() {
        super(PROGRAM_NAME);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifSourcePos = getUniformLocation("sourcePos");
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

}
