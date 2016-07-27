package pl.warp.engine.graphics.shader.program.cubemap;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.program.component.defaultprog.DefaultComponentProgram;
import pl.warp.engine.graphics.texture.Cubemap;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 20
 */
public class CubemapProgram extends Program {

    private static final int CUBEMAP_SAMPLER = 0;
    private static final InputStream VERTEX_SHADER = CubemapProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream FRAGMENT_SHADER = CubemapProgram.class.getResourceAsStream("frag.glsl");

    public static final int ATTR_VERTEX = 0;

    private int unifPerspMatrix;
    private int unifRotMatrix;

    public CubemapProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifPerspMatrix = getUniformLocation("perspMatrix");
        this.unifRotMatrix = getUniformLocation("rotMatrix");
    }


    public void useCamera(Camera camera) {
        setUniformMatrix4(unifPerspMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformMatrix4(unifRotMatrix, camera.getRotationMatrix());
    }

    public void useCubemap(Cubemap cubemap) {
        useTexture(cubemap, CUBEMAP_SAMPLER);
    }
}
