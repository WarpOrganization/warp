package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.Environment;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.shader.particle.ParticleProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 13
 */
public class ParticleRenderer implements Renderer {

    public static final int MAX_PARTICLES_AMOUNT = 200;

    private Camera camera;
    private Environment environment;

    private ParticleProgram program;

    private int positionVBO;
    private int rotationVBO;
    private int textureIndexVBO;
    private int indexBuff;
    private int vao;

    public ParticleRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void init() {
        this.program = new ParticleProgram();
        initBuffers();
    }

    private void initBuffers() {
        this.positionVBO = GL15.glGenBuffers();
        this.rotationVBO = GL15.glGenBuffers();
        this.textureIndexVBO = GL15.glGenBuffers();
        createIndexBuffer();
        createVAO();
    }

    private void createIndexBuffer() {
        IntBuffer indices = BufferUtils.createIntBuffer(MAX_PARTICLES_AMOUNT);
        for (int i = 0; i < MAX_PARTICLES_AMOUNT; i++)
            indices.put(i);
        this.indexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    private void createVAO() {
        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL20.glEnableVertexAttribArray(ParticleProgram.POSITION_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL20.glVertexAttribPointer(ParticleProgram.POSITION_ATTR, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(ParticleProgram.ROTATION_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rotationVBO);
        GL20.glVertexAttribPointer(ParticleProgram.ROTATION_ATTR, 1, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(ParticleProgram.TEXTURE_INDEX_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureIndexVBO);
        GL20.glVertexAttribPointer(ParticleProgram.TEXTURE_INDEX_ATTR, 1, GL11.GL_UNSIGNED_INT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void initRendering(int delta) {
        program.useCamera(camera);
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsParticleEnvironmentProperty.PARTICLE_ENVIRONMENT_PROPERTY_NAME)) {
            GraphicsParticleEnvironmentProperty emitterProperty =
                    component.getProperty(GraphicsParticleEnvironmentProperty.PARTICLE_ENVIRONMENT_PROPERTY_NAME);
            ParticleEnvironment environment = emitterProperty.getEnvironment();
            renderParticles(environment.getParticles(), stack);
        }
    }

    private void renderParticles(List<Particle> particles, MatrixStack stack) {
        program.useMatrixStack(stack);
        program.useCamera(camera);
        updateVBOS(particles);
        GL30.glBindVertexArray(vao);
        GL11.glDrawElements(GL11.GL_POINTS, particles.size(), GL11.GL_UNSIGNED_INT, 0);
    }

    private FloatBuffer positions = BufferUtils.createFloatBuffer(MAX_PARTICLES_AMOUNT * 3);
    private FloatBuffer rotations = BufferUtils.createFloatBuffer(MAX_PARTICLES_AMOUNT);
    private IntBuffer textureIndices = BufferUtils.createIntBuffer(MAX_PARTICLES_AMOUNT);

    private void updateVBOS(List<Particle> particles) {
        clearBuffers();
        int particleCounter = 0;
        for (Particle particle : particles) {
            if (particleCounter > MAX_PARTICLES_AMOUNT) break;
            putPosition(particle.getPosition());
            putRotation(particle.getRotation());
            putTextureIndex(particle.getTextureIndex());
            particleCounter++;
        }
        storeDataInVBOs();
    }

    private void clearBuffers() {
        positions.clear();
        rotations.clear();
        textureIndices.clear();
    }


    private void putPosition(Vector3f position) {
        positions.put(position.x).put(position.y).put(position.z);
    }

    private void putRotation(float rotation) {
        rotations.put(rotation);
    }

    private void putTextureIndex(int textureIndex) {
        textureIndices.put(textureIndex);
    }

    private void storeDataInVBOs() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positions, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rotationVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, rotations, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureIndexVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureIndices, GL15.GL_DYNAMIC_DRAW);
    }


    @Override
    public void destroy() {
        GL15.glDeleteBuffers(new int[]{positionVBO, textureIndexVBO, rotationVBO, indexBuff});
        program.delete();
    }
}
