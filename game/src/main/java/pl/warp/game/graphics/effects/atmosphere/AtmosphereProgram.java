package pl.warp.game.graphics.effects.atmosphere;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.program.MeshRendererProgram;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;

import java.util.List;

import static pl.warp.engine.graphics.program.MeshRendererProgram.*;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 12
 */
public class AtmosphereProgram extends Program {

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/";
    private static final String VERTEX_SHADER = "atmosphere/vert";
    private static final String FRAGMENT_SHADER = "atmosphere/frag";

    private static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_LIGHTS", MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES);

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "coneDirection", "coneAngle", "coneGradient", "color", "ambientColor", "attenuation", "gradient"};

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraRotationMatrix;
    private int unifCameraPos;
    private int unifColor;
    private int unifRadius;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int[][] unifSpotLightSources = new int[MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];

    public AtmosphereProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD, new ExternalProgramLoader(PROGRAM_PATH)));
        compile();
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
        this.unifCameraRotationMatrix = getUniformLocation("cameraRotationMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifColor = getUniformLocation("color");
        this.unifRadius = getUniformLocation("radius");
        this.unifLightEnabled = getUniformLocation("lightEnabled");
        this.unifSpotLightCount = getUniformLocation("numSpotLights");
    }

    private void loadSpotLightStructure() {
        for (int i = 0; i < MAX_SPOT_LIGHT_SOURCES; i++)
            for (int j = 0; j < SPOT_LIGHT_FIELD_NAMES.length; j++)
                this.unifSpotLightSources[i][j] =
                        getUniformLocation("spotLightSources[" + i + "]." + SPOT_LIGHT_FIELD_NAMES[j]);
    }


    public void useComponent(Component component) {
        if (component.hasEnabledProperty(AtmosphereProperty.ATMOSPHERE_PROPERTY_NAME)) {
            AtmosphereProperty property = component.getProperty(AtmosphereProperty.ATMOSPHERE_PROPERTY_NAME);
            setUniformV3(unifColor, property.getColor());
            setUniformf(unifRadius, property.getAtmosphereRadius());
        } else
            throw new IllegalArgumentException("Component needs an enabled atmosphere property in order to render as an atmosphere.");
    }

    private Vector3f cameraPos = new Vector3f();
    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifCameraRotationMatrix, camera.getRotationMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix().getMatrix());
        setUniformV3(unifCameraPos, camera.getPosition(cameraPos));
    }

    public void useModelMatrix(Matrix4f modelMatrix) {
        setUniformMatrix4(unifModelMatrix, modelMatrix);
    }

    public void useRotationMatrix(Matrix4f rotationMatrix) {
        setUniformMatrix4(unifRotationMatrix,  rotationMatrix);
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
