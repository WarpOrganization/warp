package pl.warp.game.graphics.effects.star.corona;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.updater.Updatable;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.GeometryProgram;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture1D;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 01
 */
public class CoronaProgram extends GeometryProgram implements Updatable {
    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "corona/vert";
    private static final String GEOM_SHADER = "corona/geom";
    private static final String FRAGMENT_SHADER = "corona/frag";

    private int time;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifTime;
    private int unifTemperature;
    private int unifSize;
    private int unifBrightness;

    private Texture1D temperature;

    public CoronaProgram(Texture1D temperature) {
        super(VERTEX_SHADER, FRAGMENT_SHADER, GEOM_SHADER,
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
        this.unifTime = getUniformLocation("time");
        this.unifSize = getUniformLocation("size");
        this.unifTemperature = getUniformLocation("color");
    }

    public void useComponent(Component component) {
        useTexture(temperature, COLORS_TEXTURE_SAMPLER);
        if (component.hasEnabledProperty(CoronaProperty.CORONA_PROPERTY_NAME)) {
            CoronaProperty property = component.getProperty(CoronaProperty.CORONA_PROPERTY_NAME);
            setUniformf(unifTemperature, property.getTemperature());
        } else
            throw new IllegalArgumentException("Component needs an enabled star property in order to render a star.");
    }

    public void useSize(float size){
        setUniformf(unifSize, size);
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
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
    }

    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix4(unifRotationMatrix, stack.topRotationMatrix());
    }

    @Override
    public void update(int delta) {
        time = (time + delta) % Integer.MAX_VALUE;
        setUniformi(unifTime, time);
    }

    public void useEnvironment(Environment environment) {

    }
}
