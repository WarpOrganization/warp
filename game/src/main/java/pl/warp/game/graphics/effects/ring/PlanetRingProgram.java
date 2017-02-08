package pl.warp.game.graphics.effects.ring;

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
 *         Created 2016-08-05 at 17
 */
public class PlanetRingProgram extends MeshRendererProgram {
    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "ring/vert";
    private static final String FRAGMENT_SHADER = "ring/frag";

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifRingStart;
    private int unifRingEnd;

    public PlanetRingProgram() {
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
        this.unifRingStart = getUniformLocation("ringStart");
        this.unifRingEnd = getUniformLocation("ringEnd");
    }

    @Override
    public void useComponent(Component component) {
        //useTexture(colorsTexture, COLORS_TEXTURE_SAMPLER);
        if (component.hasEnabledProperty(PlanetRingProperty.PLANETARY_RING_PROPERTY_NAME)) {
            PlanetRingProperty property = component.getProperty(PlanetRingProperty.PLANETARY_RING_PROPERTY_NAME);
            setUniformf(unifRingStart, property.getStartRadius());
            setUniformf(unifRingEnd, property.getEndRadius());
            useTexture(property.getRingColors(), COLORS_TEXTURE_SAMPLER);
        } else
            throw new IllegalStateException("Unable to render component without PlanetaryRingProperty enabled property.");
    }

    @Override
    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
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
