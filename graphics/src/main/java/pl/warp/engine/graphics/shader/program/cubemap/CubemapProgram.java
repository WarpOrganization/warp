package pl.warp.engine.graphics.shader.program.cubemap;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 20
 */
public class CubemapProgram extends Program {

    private static final int CUBEMAP_SAMPLER = 0;
    private static final String PROGRAM_NAME = "cubemap";

    public static final int ATTR_VERTEX = 0;

    private int unifPerspMatrix;
    private int unifRotMatrix;
    private int unifBrightness;

    public CubemapProgram() {
        super(PROGRAM_NAME);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifPerspMatrix = getUniformLocation("perspMatrix");
        this.unifRotMatrix = getUniformLocation("rotMatrix");
        this.unifBrightness = getUniformLocation("brightness");
    }


    public void useCamera(Camera camera) {
        setUniformMatrix4(unifPerspMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformMatrix4(unifRotMatrix, camera.getRotationMatrix());
    }

    public void useCubemap(Cubemap cubemap) {
        useTexture(cubemap, CUBEMAP_SAMPLER);
    }

    public void useBrightness(float brightness) {
        setUniformf(unifBrightness, brightness);
    }
}
