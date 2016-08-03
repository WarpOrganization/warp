package pl.warp.game.program.star;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgram;
import pl.warp.engine.graphics.shader.program.component.defaultprog.DefaultComponentProgram;
import pl.warp.engine.graphics.shader.program.cubemap.CubemapProgram;
import pl.warp.engine.graphics.texture.Cubemap;

import java.io.InputStream;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 01
 */
public class StarProgram extends ComponentRendererProgram {

    private static final InputStream VERTEX_SHADER = StarProgram.class.getResourceAsStream("vert.glsl");
    private static final InputStream FRAGMENT_SHADER = StarProgram.class.getResourceAsStream("frag.glsl");

    private int unifProjectionMatrix;
    private int unifModelMatrix;
    private int unifRotationMatrix;
    private int unifCameraMatrix;
    private int unifCameraPos;
    private int unifTemperature;

    public StarProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
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
        this.unifTemperature = getUniformLocation("temperature");
    }

    @Override
    public void useComponent(Component component) {

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

    @Override
    public void useEnvironment(Environment environment) {

    }
}
