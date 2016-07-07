package pl.warp.engine.graphics.shader.component.defaultprog;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.DirectionalSpotLight;
import pl.warp.engine.graphics.light.LightEnvironment;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;

import java.io.InputStream;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 13
 */
public class DefaultComponentProgram extends ComponentRendererProgram {

    private static final InputStream VERTEX_SHADER = DefaultComponentProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream FRAGMENT_SHADER = DefaultComponentProgram.class.getResourceAsStream("frag.glsl");
    private static final String[] OUT_NAMES = {"fragData"};

    private static final int MAIN_MATERIAL_TEXTURE_SAMPLER = 0;

    private static final int MAX_SPOT_LIGHT_SOURCES = 10;
    private static final int MAX_DIRECTIONAL_LIGHT_SOURCES = 25;

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "color", "ambientColor", "attenuation", "gradient"};
    private static final String[] DIRECTIONAL_LIGT_FIELD_NAMES =
            {"position", "direction", "directionGradient", "color", "ambientColor", "attenuation", "gradient"};

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifMainTexture;
    private int unifMaterialBrightness;
    private int unifMaterialShininess;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int unifDirectionalLightCount;
    private int[][] unifSpotLightSources = new int[MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];
    private int[][] unifDirectionalLightSources = new int[MAX_DIRECTIONAL_LIGHT_SOURCES][DIRECTIONAL_LIGT_FIELD_NAMES.length];

    public DefaultComponentProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        loadLocations();
    }

    private void loadLocations() {
        loadUniforms();
        loadSpotLightStructure();
        loadDirectionalLightStructure();
        setupSamplers();
    }

    private void loadUniforms() {
        this.unifProjectionMatrix = getUniformLocation("projectionMatrix");
        this.unifModelMatrix = getUniformLocation("modelMatrix");
        this.unifRotationMatrix = getUniformLocation("rotationMatrix");
        this.unifCameraMatrix = getUniformLocation("cameraMatrix");
        this.unifCameraPos = getUniformLocation("cameraPos");
        this.unifMainTexture = getUniformLocation("material.mainTexture");
        this.unifMaterialBrightness = getUniformLocation("material.brightness");
        this.unifMaterialShininess = getUniformLocation("material.shininess");
        this.unifLightEnabled = getUniformLocation("lightEnabled");
        this.unifSpotLightCount = getUniformLocation("numSpotLights");
        this.unifDirectionalLightCount = getUniformLocation("numDirectionalLights");
    }

    private void loadSpotLightStructure() {
        for (int i = 0; i < MAX_SPOT_LIGHT_SOURCES; i++)
            for (int j = 0; j < SPOT_LIGHT_FIELD_NAMES.length; j++)
                this.unifSpotLightSources[i][j] =
                        getUniformLocation("spotLightSources[" + i + "]." + SPOT_LIGHT_FIELD_NAMES[j]);
    }

    private void loadDirectionalLightStructure() {
        for (int i = 0; i < MAX_DIRECTIONAL_LIGHT_SOURCES; i++)
            for (int j = 0; j < DIRECTIONAL_LIGT_FIELD_NAMES.length; j++)
                this.unifDirectionalLightSources[i][j] =
                        getUniformLocation("directionalLightSources[" + i + "]." + DIRECTIONAL_LIGT_FIELD_NAMES[j]);
    }

    private void setupSamplers() {
        setUniformi(unifMainTexture, MAIN_MATERIAL_TEXTURE_SAMPLER);
    }

    @Override
    public void useMaterial(Material material) {
        useTexture(material.getMainTexture(), MAIN_MATERIAL_TEXTURE_SAMPLER);
        setUniformf(unifMaterialBrightness, material.getBrightness());
        setUniformf(unifMaterialShininess, material.getShininess());
    }

    @Override
    public void useCamera(Camera camera) {
        setUniformMatrix4(unifCameraMatrix, camera.getCameraMatrix());
        setUniformMatrix4(unifProjectionMatrix, camera.getProjectionMatrix());
        setUniformV3(unifCameraPos, camera.getPosition());
    }

    @Override
    public void useMatrixStack(MatrixStack stack) {
        setUniformMatrix4(unifModelMatrix, stack.topMatrix());
        setUniformMatrix4(unifRotationMatrix, stack.topRotationMatrix());
    }

    public void useLightEnvironment(LightEnvironment environment) {
        setUniformb(unifLightEnabled, environment.isLightEnabled());

        setUniformi(unifSpotLightCount, environment.getSpotLights().size());
        List<SpotLight> spotLights = environment.getSpotLights();
        for (int i = 0; i < spotLights.size(); i++)
            setSpotLight(unifSpotLightSources[i], spotLights.get(i));

        setUniformi(unifDirectionalLightCount, environment.getDirectionalSpotLights().size());
        List<DirectionalSpotLight> directionalSpotLights = environment.getDirectionalSpotLights();
        for (int i = 0; i < directionalSpotLights.size(); i++)
            setDirectionalLight(unifDirectionalLightSources[i], directionalSpotLights.get(i));
    }

    private void setSpotLight(int[] lightStruct, SpotLight light) {
        setUniformV3(lightStruct[SPOT_LIGHT_POSITION], light.getPosition());
        setUniformV3(lightStruct[SPOT_LIGHT_COLOR], light.getColor());
        setUniformV3(lightStruct[SPOT_LIGHT_AMBIENT_COLOR], light.getAmbientColor());
        setUniformf(lightStruct[SPOT_LIGHT_ATTENUATION], light.getAttenuation());
        setUniformf(lightStruct[SPOT_LIGHT_GRADIENT], light.getGradient());
    }

    private void setDirectionalLight(int[] lightStruct, DirectionalSpotLight light) {
        setUniformV3(lightStruct[DIRECTIONAL_LIGHT_POSITION], light.getPosition());
        setUniformV3(lightStruct[DIRECTIONAL_LIGHT_DIRECTION], light.getDirection());
        setUniformf(lightStruct[DIRECTIONAL_LIGHT_DIRECTION_GRADIENT], light.getDirectionGradient());
        setUniformV3(lightStruct[DIRECTIONAL_LIGHT_COLOR], light.getColor());
        setUniformV3(lightStruct[DIRECTIONAL_LIGHT_AMBIENT_COLOR], light.getAmbientColor());
        setUniformf(lightStruct[DIRECTIONAL_LIGHT_ATTENUATION], light.getAttenuation());
        setUniformf(lightStruct[DIRECTIONAL_LIGHT_GRADIENT], light.getGradient());
    }
}
