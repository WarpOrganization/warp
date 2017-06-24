package pl.warp.engine.game.graphics.effects.ring;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
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
 *         Created 2016-08-05 at 17
 */
public class PlanetRingProgram extends MeshRendererProgram {
    private static final int COLORS_TEXTURE_SAMPLER = 0;

    private static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_LIGHTS", MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES);

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "coneDirection", "coneAngle", "coneGradient", "color", "ambientColor", "attenuation", "gradient"};

    private static final String PROGRAM_PATH = "pl/warp/engine/game/graphics/effects/";
    private static final String VERTEX_SHADER = "ring/vert";
    private static final String FRAGMENT_SHADER = "ring/frag";

    private int unifRenderShadow;
    private int unifPlanetRadius;
    private int unifPlanetPos;
    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifRingStart;
    private int unifRingEnd;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int[][] unifSpotLightSources = new int[MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];

    public PlanetRingProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
        loadLocations();
    }
    private void loadLocations() {
        loadUniforms();
        loadSpotLightStructure();
    }

    private void loadUniforms() {
        this.unifRenderShadow = getUniformLocation("renderShadow");
        this.unifPlanetRadius = getUniformLocation("planetRadius");
        this.unifPlanetPos = getUniformLocation("planetPos");
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifCameraMatrix = getUniformLocation("cameraMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifRingStart = getUniformLocation("ringStart");
        this.unifRingEnd = getUniformLocation("ringEnd");
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
        if (component.hasEnabledProperty(PlanetRingProperty.PLANETARY_RING_PROPERTY_NAME)) {
            PlanetRingProperty property = component.getProperty(PlanetRingProperty.PLANETARY_RING_PROPERTY_NAME);
            setUniformf(unifRingStart, property.getStartRadius());
            setUniformf(unifRingEnd, property.getEndRadius());
            useTexture(property.getRingColors(), COLORS_TEXTURE_SAMPLER);
            setUniformb(unifRenderShadow, property.getRenderShadow());
            if(property.getRenderShadow()) usePlanet(component.getParent());
        } else
            throw new IllegalStateException("Unable to render component without PlanetaryRingProperty enabled property.");
    }

    private Vector3f tmpVector = new Vector3f();

    private void usePlanet(Component planet) {
        TransformProperty transformProperty = planet.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        setUniformf(unifPlanetRadius, transformProperty.getScale().x);
        Transforms.getAbsolutePosition(planet, tmpVector); //TODO pass somehow
        setUniformV3(unifPlanetPos, tmpVector);
    }


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
