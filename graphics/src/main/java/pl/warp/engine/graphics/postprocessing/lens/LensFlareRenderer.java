package pl.warp.engine.graphics.postprocessing.lens;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.math.Transforms;
import pl.warp.engine.graphics.mesh.VAO;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.shader.program.particle.ParticleProgram;
import pl.warp.engine.graphics.texture.Texture2D;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensFlareRenderer implements Flow<Texture2D, Texture2D> {

    public static final int MAX_FLARES = 100;

    private Camera camera;
    private Environment environment;

    private Texture2D scene;
    private TextureFramebuffer framebuffer;

    private List<VAO> vaos = new ArrayList<>();
    private int indexBuffer;

    public LensFlareRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void update(int delta) {
        int flareComponentsNumber = environment.getLensFlareComponents().size();
        if (vaos.size() != flareComponentsNumber)
            setVAOsNumber(flareComponentsNumber);
        for (Component component : environment.getLensFlareComponents()) {
            GraphicsLensFlareProperty property = component.getProperty(GraphicsLensFlareProperty.LENS_FLARE_PROPERTY_NAME);
            renderFlare(component, property.getFlare());
        }
    }

    private void setVAOsNumber(int vaosNumber) {
        while (vaos.size() != vaosNumber) {
            if (vaosNumber > vaos.size()) removeVAO();
            else if (vaosNumber < vaos.size()) createVAO();
        }
    }


    private void removeVAO() {
        VAO vaoToRemove = vaos.get(vaos.size());
        vaoToRemove.destroyExceptIndex();
        vaos.remove(vaoToRemove);
    }


    private void createVAO() {
        int offset = GL15.glGenBuffers();
        int scale = GL15.glGenBuffers();
        int textureIndex = GL15.glGenBuffers();
        VAO vao = new VAO(new int[]{offset, scale, textureIndex},
                indexBuffer,
                new int[]{1,1,1},
                new int[]{GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_INT});
        this.vaos.add(vao);
    }


    private Vector4f tempVec4 = new Vector4f();
    private Vector3f tempVec3 = new Vector3f();
    private Vector2f tempVec2 = new Vector2f();

    private void renderFlare(Component component, LensFlare flare) {
        Vector4f flarePosition = tempVec4.set(Transforms.getActualPosition(component, tempVec3), 1.0f);
        Vector4f flareCameraPos = flarePosition.mul(camera.getCameraMatrix());
        Matrix4f projectionMatrix = camera.getProjectionMatrix().getMatrix();
        Vector4f flareScreenPos = flareCameraPos.mul(projectionMatrix);
        if (isInRange(flareScreenPos))
            renderFlare(flareScreenPos, flare);
    }

    private boolean isInRange(Vector4f pos) {
        return pos.x > -1.0f && pos.x < 1.0f &&
                pos.y > -1.0f && pos.y < 1.0f &&
                pos.z > 0.0f && pos.z < 1.0f;
    }

    private void renderFlare(Vector4f flareScreenPosition, LensFlare flare) {
        Vector2f flare2DPos = tempVec2.set(flareScreenPosition.x, flareScreenPosition.y);
        float distance = flare2DPos.length();
        Vector2f direction = flare2DPos.normalize();
        for (SingleFlare singleFlare : flare.getFlares()) {
            renderSingleFlare(singleFlare, direction, distance);
        }
    }

    private void renderSingleFlare(SingleFlare singleFlare, Vector2f direction, float distance) {

    }


    @Override
    public void init() {
        this.framebuffer = new TextureFramebuffer(scene);
        createIndexBuffer();
    }

    private void createIndexBuffer() {
        this.indexBuffer = GL15.glGenBuffers();
        IntBuffer indices = BufferUtils.createIntBuffer(MAX_FLARES);
        for (int i = 0; i < MAX_FLARES; i++)
            indices.put(i);
        indices.rewind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    @Override
    public void destroy() {
        while (!vaos.isEmpty()) removeVAO();
        GL15.glDeleteBuffers(indexBuffer);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    @Override
    public Texture2D getOutput() {
        return scene;
    }

    @Override
    public void setInput(Texture2D input) {
        this.scene = input;
        this.framebuffer = new TextureFramebuffer(input);
    }
}
