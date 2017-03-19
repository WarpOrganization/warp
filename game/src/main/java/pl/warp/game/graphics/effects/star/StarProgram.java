package pl.warp.game.graphics.effects.star;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.updater.Updatable;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.program.MeshRendererProgram;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture1D;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 01
 */
public class StarProgram extends MeshRendererProgram implements Updatable {

    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "star/vert";
    private static final String FRAGMENT_SHADER = "star/frag";

    private int time;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifTime;
    private int unifTemperature;
    private int unifBrightness;

    private Texture1D temperature;

    public StarProgram(Texture1D temperature) {
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
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifTime = getUniformLocation("time");
        this.unifTemperature = getUniformLocation("temperature");
        this.unifBrightness = getUniformLocation("brightness");
    }

    @Override
    public void useComponent(Component component) {
        useTexture(temperature, COLORS_TEXTURE_SAMPLER);
        if (component.hasEnabledProperty(StarProperty.STAR_PROPERTY_NAME)) {
            StarProperty property = component.getProperty(StarProperty.STAR_PROPERTY_NAME);
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

    @Override
    public void update(int delta) {
        use();
        time = (time + delta) % Integer.MAX_VALUE;
        setUniformi(unifTime, time);
    }

    @Override
    public void useEnvironment(Environment environment) {

    }
}
