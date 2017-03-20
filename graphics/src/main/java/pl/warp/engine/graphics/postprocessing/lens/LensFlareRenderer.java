package pl.warp.engine.graphics.postprocessing.lens;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.mesh.VAO;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.program.rendering.lens.LensProgram;
import pl.warp.engine.graphics.texture.Texture2D;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensFlareRenderer implements Flow<Texture2D, Texture2D> {

    private static final Logger logger = Logger.getLogger(LensFlareRenderer.class);

    public static final int MAX_FLARES_NUMBER = 100;

    private Graphics graphics;
    private Environment environment;
    private RenderingConfig config;

    private Texture2D scene;
    private TextureFramebuffer framebuffer;

    private LensProgram program;
    private List<FlareData> data = new ArrayList<>();
    private int indexBuffer;
    private int offsetBuffer;
    private int scaleBuffer;
    private int textureIndexBuffer;
    private int flareColorBuffer;
    private VAO vao;

    public LensFlareRenderer(Environment environment, RenderingConfig config) {
        this.environment = environment;
        this.config = config;
    }

    @Override
    public void update() {
        int flareComponentsNumber = environment.getLensFlareComponents().size();
        if (data.size() != flareComponentsNumber)
            setDataBuffersNumber(flareComponentsNumber);
        List<Component> lensFlareComponents = environment.getLensFlareComponents();
        for (int i = 0; i < lensFlareComponents.size(); i++) {
            Component component = lensFlareComponents.get(i);
            GraphicsLensFlareProperty property = component.getProperty(GraphicsLensFlareProperty.LENS_FLARE_PROPERTY_NAME);
            renderFlare(i, component, property.getFlare());
        }
    }

    private void setDataBuffersNumber(int buffersNumber) {
        while (data.size() != buffersNumber) {
            if (buffersNumber > data.size()) createDataBuffer();
            else if (buffersNumber < data.size()) removeDataBuffer();
        }
    }

    private void createDataBuffer() {
        FlareData data = new FlareData(MAX_FLARES_NUMBER);
        this.data.add(data);
    }

    private void removeDataBuffer() {
        FlareData data = this.data.get(this.data.size());
        this.data.remove(data);
    }

    private void createVAO() {
        this.offsetBuffer = GL15.glGenBuffers();
        this.scaleBuffer = GL15.glGenBuffers();
        this.textureIndexBuffer = GL15.glGenBuffers();
        this.flareColorBuffer = GL15.glGenBuffers();
        this.vao = new VAO(new int[]{offsetBuffer, scaleBuffer, textureIndexBuffer, flareColorBuffer},
                indexBuffer,
                new int[]{1, 1, 1, 3},
                new int[]{GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_INT, GL11.GL_FLOAT});
    }


    private Vector4f tempVec4 = new Vector4f();
    private Vector3f tempVec3 = new Vector3f();
    private Vector2f tempVec2 = new Vector2f();

    private void renderFlare(int index, Component component, LensFlare flare) {
        Vector4f flarePosition = tempVec4.set(Transforms.getAbsolutePosition(component, tempVec3), 1.0f);
        Vector4f flareCameraPos = flarePosition.mul(graphics.getMainViewCamera().getCameraMatrix());
        Matrix4f projectionMatrix = graphics.getMainViewCamera().getProjectionMatrix().getMatrix();
        Vector4f flareProjectionPos = flareCameraPos.mul(projectionMatrix);
        Vector3f actualProjectionPos = tempVec3.set(flareProjectionPos.x, flareProjectionPos.y, flareProjectionPos.z)
                .div(flareProjectionPos.w);
        Vector2f flareScreenPos = tempVec2.set(actualProjectionPos.x, actualProjectionPos.y);
        if (isInRange(actualProjectionPos))
            renderFlare(index, flareScreenPos, flare);
    }

    private boolean isInRange(Vector3f pos) {
        return pos.x > -1.0f && pos.x < 1.0f &&
                pos.y > -1.0f && pos.y < 1.0f &&
                pos.z > 0.0f && pos.z < 1.0f;
    }

    private void renderFlare(int index, Vector2f sourceScreenPos, LensFlare flare) {
        SingleFlare[] flares = flare.getFlares();
        FlareData data = this.data.get(index);
        loadVAO(flares, data);
        renderVAO(flares.length, sourceScreenPos, flare);
    }

    private void loadVAO(SingleFlare[] flares, FlareData data) {
        data.clear();
        for (SingleFlare singleFlare : flares)
            data.store(singleFlare);
        data.rewind();
        setVAOData(data);
    }

    private void renderVAO(int size, Vector2f sourceScreenPos, LensFlare flare) {
        framebuffer.bindDraw();
        program.use();
        program.useSourcePos(sourceScreenPos);
        program.useTexture(flare.getLensTextures());
        vao.bind();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDrawElements(GL11.GL_POINTS, size, GL11.GL_UNSIGNED_INT, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        vao.unbind();
    }


    private void setVAOData(FlareData data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, offsetBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.getOffsets(), GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, scaleBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.getScales(), GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureIndexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.getTextureIndices(), GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, flareColorBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.getFlareColors(), GL15.GL_DYNAMIC_DRAW);
    }


    @Override
    public void init(Graphics g) {
        logger.info("Initializing lens flare renderer...");
        this.graphics = g;
        this.program = new LensProgram();
        program.useScreenSize(config.getDisplay().getWidth(), config.getDisplay().getHeight());
        createIndexBuffer();
        createVAO();
        GL11.glEnable(GL11.GL_BLEND);
        logger.info("Lens flare renderer initialized.");
    }

    private void createIndexBuffer() {
        this.indexBuffer = GL15.glGenBuffers();
        IntBuffer indices = BufferUtils.createIntBuffer(MAX_FLARES_NUMBER);
        for (int i = 0; i < MAX_FLARES_NUMBER; i++)
            indices.put(i);
        indices.rewind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    @Override
    public void destroy() {
        vao.destroy();
        framebuffer.delete();
        logger.info("Lens flare renderer destroyed.");
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        program.useScreenSize(newWidth, newHeight);
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
