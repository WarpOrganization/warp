package pl.warp.game.graphics.effects.star.corona;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.updater.Updatable;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture1D;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 01
 */
public class CoronaProgram extends Program implements Updatable {
    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "corona/vert";
    private static final String FRAGMENT_SHADER = "corona/frag";

    private int time;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraRotationMatrix;
    private int unifTime;
    private int unifTemperature;
    private int unifBrightness;

    private Texture1D temperature;

    public CoronaProgram(Texture1D temperature) {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
        this.temperature = temperature;
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
        this.unifCameraRotationMatrix = getUniformLocation("cameraRotationMatrix");
        this.unifTime = getUniformLocation("time");
        this.unifTemperature = getUniformLocation("temperature");
        this.unifBrightness = getUniformLocation("brightness");
    }

    public void useComponent(Component component) {
        useTexture(temperature, COLORS_TEXTURE_SAMPLER);
        if (component.hasEnabledProperty(CoronaProperty.CORONA_PROPERTY_NAME)) {
            CoronaProperty property = component.getProperty(CoronaProperty.CORONA_PROPERTY_NAME);
            setUniformf(unifTemperature, property.getTemperature());
            setUniformV3(unifBrightness, getBrightness(property.getTemperature()));
        } else
            throw new IllegalArgumentException("Component needs an enabled star property in order to render a star.");
    }

    private Vector3f tempBrightness = new Vector3f();

    private Vector3f getBrightness(float temperature) {
        return tempBrightness.set(temperature * (0.0534f / 255.0f) - (43.0f / 255.0f),
                temperature * (0.0628f / 255.0f) - (77.0f / 255.0f),
                temperature * (0.0735f / 255.0f) - (115.0f / 255.0f));
    }

    private Vector3f tmpVector = new Vector3f();

    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifCameraRotationMatrix, camera.getRotationMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
    }

    public void useModelMatrix(Matrix4f modelMatrix) {
        setUniformMatrix4(unifModelMatrix, modelMatrix);
    }

    public void useRotationMatrix(Matrix4f rotationMatrix) {
        setUniformMatrix4(unifRotationMatrix,  rotationMatrix);
    }

    @Override
    public void update(int delta) {
        use();
        time = ((time + delta)) % Integer.MAX_VALUE;
        setUniformf(unifTime, time / 1000.0f); //seconds
    }

}
