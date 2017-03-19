package pl.warp.engine.graphics.program.rendering.component.defaultprog;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.program.MeshRendererProgram;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.LocalProgramLoader;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 13
 */
public class DefaultMeshProgram extends MeshRendererProgram {

    private static final String VERTEX_SHADER = "component/defaultprog/vert";
    private static final String FRAGMENT_SHADER = "component/defaultprog/frag";

    private static final int DIFFUSE_MATERIAL_TEXTURE_SAMPLER = 0;
    private static final int MATERIAL_BRIGHTNESS_TEXTURE = 1;

    protected static final ConstantField CONSTANT_FIELD = new ConstantField().set("MAX_LIGHTS", MeshRendererProgram.MAX_SPOT_LIGHT_SOURCES);

    private static final String[] SPOT_LIGHT_FIELD_NAMES =
            {"position", "coneDirection", "coneAngle", "coneGradient", "color", "ambientColor", "attenuation", "gradient"};

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifMaterialBrightness;
    private int unifMaterialShininess;
    private int unifMaterialHasBrightnessTexture;
    private int unifMaterialTransparency;
    private int unifLightEnabled;
    private int unifSpotLightCount;
    private int[][] unifSpotLightSources = new int[MAX_SPOT_LIGHT_SOURCES][SPOT_LIGHT_FIELD_NAMES.length];

    protected DefaultMeshProgram(String vertexShaderName, String fragmentShaderName, ExtendedGLSLProgramCompiler compiler) {
        super(vertexShaderName, fragmentShaderName, compiler);
        loadLocations();
    }

    public DefaultMeshProgram() {
        this(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
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
        this.unifMaterialBrightness = getUniformLocation("material.brightness");
        this.unifMaterialShininess = getUniformLocation("material.shininess");
        this.unifMaterialHasBrightnessTexture = getUniformLocation("material.hasBrightnessTexture");
        this.unifMaterialTransparency = getUniformLocation("material.transparency");
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
        setTextureLocation("material.diffuseTexture", DIFFUSE_MATERIAL_TEXTURE_SAMPLER);
        setTextureLocation("material.brightnessTexture", MATERIAL_BRIGHTNESS_TEXTURE);
    }

    @Override
    public void useComponent(Component component) {
        if (component.hasEnabledProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)) {
            GraphicsMaterialProperty property = component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME);
            useMaterial(property.getMaterial());
        }
    }

    private void useMaterial(Material material) {
        useTexture(material.getDiffuseTexture(), DIFFUSE_MATERIAL_TEXTURE_SAMPLER);
        setUniformf(unifMaterialBrightness, material.getBrightness());
        setUniformf(unifMaterialShininess, material.getShininess());
        setUniformf(unifMaterialTransparency, material.getTransparency());
        setUniformb(unifMaterialHasBrightnessTexture, material.hasBrightnessTexture());
        if (material.hasBrightnessTexture())
            useTexture(material.getBrightnessTexture(), MATERIAL_BRIGHTNESS_TEXTURE);
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
