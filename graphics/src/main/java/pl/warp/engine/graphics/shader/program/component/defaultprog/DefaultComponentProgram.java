package pl.warp.engine.graphics.shader.program.component.defaultprog;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;

import java.io.InputStream;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 13
 */
public class DefaultComponentProgram extends ComponentRendererProgram {

    private static final InputStream VERTEX_SHADER = DefaultComponentProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream FRAGMENT_SHADER = DefaultComponentProgram.class.getResourceAsStream("frag.glsl");

    private static final int MAIN_MATERIAL_TEXTURE_SAMPLER = 0;
    private static final int MATERIAL_BRIGHTNESS_TEXTURE = 1;

    private static final int MAX_SPOT_LIGHT_SOURCES = 25;
    private static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_LIGHTS", MAX_SPOT_LIGHT_SOURCES);

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "coneDirection", "coneAngle", "coneGradient", "color", "ambientColor", "attenuation", "gradient"};

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifMainTexture;
    private int unifMaterialBrightness;
    private int unifMaterialShininess;
    private int unifMaterialHasBrightnessTexture;
    private int unifMaterialBrightnessTexture;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int[][] unifSpotLightSources = new int[MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];

    public DefaultComponentProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, CONSTANT_FIELD);
        loadLocations();
    }

    private void loadLocations() {
        loadUniforms();
        loadSpotLightStructure();
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
        this.unifMaterialHasBrightnessTexture = getUniformLocation("material.hasBrightnessTexture");
        this.unifMaterialBrightnessTexture = getUniformLocation("material.brightnessTexture");
        this.unifLightEnabled = getUniformLocation("lightEnabled");
        this.unifSpotLightCount = getUniformLocation("numSpotLights");
    }

    private void loadSpotLightStructure() {
        for (int i = 0; i < MAX_SPOT_LIGHT_SOURCES; i++)
            for (int j = 0; j < SPOT_LIGHT_FIELD_NAMES.length; j++)
                this.unifSpotLightSources[i][j] =
                        getUniformLocation("spotLightSources[" + i + "]." + SPOT_LIGHT_FIELD_NAMES[j]);
    }

    private void setupSamplers() {
        setUniformi(unifMainTexture, MAIN_MATERIAL_TEXTURE_SAMPLER);
        setUniformi(unifMaterialBrightnessTexture, MATERIAL_BRIGHTNESS_TEXTURE);
    }

    @Override
    public void useComponent(Component component) {
        if (component.hasEnabledProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)) {
            GraphicsMaterialProperty property = component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME);
            useMaterial(property.getMaterial());
        }
    }

    private void useMaterial(Material material) {
        useTexture(material.getMainTexture(), MAIN_MATERIAL_TEXTURE_SAMPLER);
        setUniformf(unifMaterialBrightness, material.getBrightness());
        setUniformf(unifMaterialShininess, material.getShininess());
        if (material.hasBrightnessTexture()) {
            setUniformb(unifMaterialHasBrightnessTexture, true);
            useTexture(material.getBrightnessTexture(), MATERIAL_BRIGHTNESS_TEXTURE);
        } else setUniformb(unifMaterialHasBrightnessTexture, false);
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
