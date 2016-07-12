package pl.warp.engine.graphics.shader.particle;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.identitymultisample.IdentityMultisampleProgram;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 14
 */
public class ParticleProgram extends Program {

    public static final int POSITION_ATTR = 0;
    public static final int ROTATION_ATTR = 1;
    public static final int TEXTURE_INDEX_ATTR = 2;

    private static final InputStream FRAGMENT_SHADER = ParticleProgram.class.getResourceAsStream("frag.glsl");
    private static final InputStream VERTEX_SHADER = ParticleProgram.class.getResourceAsStream("vert.glsl");
    private static final String[] OUT_NAMES = {};

    public ParticleProgram() {
        super(FRAGMENT_SHADER, VERTEX_SHADER, OUT_NAMES);
    }

    public void useMatrixStack(MatrixStack stack) {

    }

    public void useCamera(Camera camera) {

    }
}
