package pl.warp.engine.game.graphics.effects.gasplanet;

import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.execution.task.update.Updatable;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.program.MeshRendererProgram;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 01
 */
public class GasPlanetProgram extends MeshRendererProgram implements Updatable {

    private static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_LIGHTS", MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES);

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "coneDirection", "coneAngle", "coneGradient", "color", "ambientColor", "attenuation", "gradient"};

    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final String PROGRAM_PATH = "pl/warp/engine/game/graphics/effects/";
    private static final String VERTEX_SHADER = "gasplanet/vert";
    private static final String FRAGMENT_SHADER = "gasplanet/frag";

    private int time;

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifTime;
    private int unifColor;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int[][] unifSpotLightSources = new int[MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];

    public GasPlanetProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
        loadLocations();
        loadSpotLightStructure();
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
        this.unifLightEnabled = getUniformLocation("lightEnabled");
        this.unifSpotLightCount = getUniformLocation("numSpotLights");
    }

    private void loadSpotLightStructure() {
        for (int i = 0; i < MAX_SPOT_LIGHT_SOURCES; i++)
            for (int j = 0; j < SPOT_LIGHT_FIELD_NAMES.length; j++)
                this.unifSpotLightSources[i][j] =
                        getUniformLocation("spotLightSources[" + i + "]." + SPOT_LIGHT_FIELD_NAMES[j]);
    }

    @Override
    public void useComponent(Component component) {
        if (component.hasEnabledProperty(GasPlanetProperty.GAS_PLANET_PROPERTY_NAME)) {
            GasPlanetProperty property = component.getProperty(GasPlanetProperty.GAS_PLANET_PROPERTY_NAME);
            useTexture(property.getColors(), COLORS_TEXTURE_SAMPLER);
        } else throw new IllegalArgumentException("Component needs an enabled gasplanet planet property in order to render a gasplanet planet.");
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
        use();
        time += delta;
        setUniformi(unifTime, time);
    }

    public void useEnvironment(Environment environment) {
        setUniformb(unifLightEnabled, environment.isLightEnabled());
        List<SpotLight> spotLights = environment.getSpotLights();
        int j = 0;
        for (SpotLight spotLight : spotLights)
            if (spotLight.isEnabled())
                setSpotLight(unifSpotLightSources[j++], spotLight);
        setUniformi(unifSpotLightCount, j);
    }


    private void setSpotLight(int[] lightStruct, SpotLight light) {
        setUniformV3(lightStruct[SPOT_LIGHT_POSITION], light.getPosition());
        setUniformV3(lightStruct[SPOT_LIGHT_CONE_DIRECTION], light.getDirection());
        setUniformf(lightStruct[SPOT_LIGHT_CONE_ANGLE], light.getConeAngle());
        setUniformf(lightStruct[SPOT_LIGHT_CONE_GRADIENT], light.getConeGradient());
        setUniformV3(lightStruct[SPOT_LIGHT_COLOR], light.getColor());
        setUniformV3(lightStruct[SPOT_LIGHT_AMBIENT_COLOR], light.getAmbientColor());
        setUniformf(lightStruct[SPOT_LIGHT_ATTENUATION], light.getAttenuation());
        setUniformf(lightStruct[SPOT_LIGHT_GRADIENT], light.getGradient());
    }
}
