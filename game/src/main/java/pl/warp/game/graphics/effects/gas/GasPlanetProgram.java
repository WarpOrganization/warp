package pl.warp.game.graphics.effects.gas;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.MeshRendererProgram;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.extendedglsl.ExternalProgramLoader;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 01
 */
public class GasPlanetProgram extends MeshRendererProgram {

    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "gas/vert";
    private static final String FRAGMENT_SHADER = "gas/frag";

    private int time;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifTime;
    private int unifColor;

    public GasPlanetProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
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
        this.unifColor = getUniformLocation("color");
        this.unifTime = getUniformLocation("time");
    }

    @Override
    public void useComponent(Component component) {
        if (component.hasEnabledProperty(GasPlanetProperty.GAS_PLANET_PROPERTY_NAME)) {
            GasPlanetProperty property = component.getProperty(GasPlanetProperty.GAS_PLANET_PROPERTY_NAME);
            useTexture(property.getColors(), COLORS_TEXTURE_SAMPLER);
        }
    }

    private Vector3f tmpVector = new Vector3f();

    @Override
    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformV3(unifCameraPos, camera.getPosition(tmpVector));
    }

    @Override
    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix4(unifRotationMatrix, stack.topRotationMatrix());
    }

    public void update(int delta) {
        time += delta;
        setUniformi(unifTime, time);
    }

    @Override
    public void useEnvironment(Environment environment) {

    }
}
