package pl.warp.game.program.gas;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.texture.Texture1D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-08-05 at 17
 */
public class PlanetaryRingProgram extends ComponentRendererProgram {
    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final InputStream VERTEX_SHADER = PlanetaryRingProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream FRAGMENT_SHADER = PlanetaryRingProgram.class.getResourceAsStream("frag.glsl");

    private Texture1D ringColorsTexture;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;

    public PlanetaryRingProgram(Texture1D ringColorsTexture) {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        this.ringColorsTexture = ringColorsTexture;
        loadLocations();
    }

    private void loadLocations() {
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifCameraMatrix = getUniformLocation("cameraMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
    }

    @Override
    public void useComponent(Component component) {
       //useTexture(colorsTexture, COLORS_TEXTURE_SAMPLER);
    }

    @Override
    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformV3(unifCameraPos, camera.getPosition());
    }

    @Override
    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix4(unifRotationMatrix, stack.topRotationMatrix());
    }
    @Override
    public void useEnvironment(Environment environment) {

    }
}
