package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.particles.ParticleRenderer;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;
import pl.warp.engine.graphics.shader.program.particle.dot.DotParticleProgram;
import pl.warp.engine.graphics.shader.program.particle.textured.TexturedParticleProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static pl.warp.engine.graphics.particles.ParticleSystemRenderer.MAX_PARTICLES_NUMBER;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 18
 */
public class DotParticleRenderer implements ParticleRenderer<DotParticleSystem> {

    private DotParticleProgram program;

    private int positionVBO;
    private int rotationVBO;
    private int colorVBO;
    private int gradientVBO;
    private int indexBuff;
    private int vao;

    public DotParticleRenderer() {
        this.program = new DotParticleProgram();
        initBuffers();
    }

    private void initBuffers() {
        this.positionVBO = GL15.glGenBuffers();
        this.rotationVBO = GL15.glGenBuffers();
        this.colorVBO = GL15.glGenBuffers();
        this.gradientVBO = GL15.glGenBuffers();
        createIndexBuffer();
        createVAO();
    }

    private void createIndexBuffer() {
        IntBuffer indices = BufferUtils.createIntBuffer(MAX_PARTICLES_NUMBER);
        for (int i = 0; i < MAX_PARTICLES_NUMBER; i++)
            indices.put(i);
        indices.rewind();
        this.indexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    private void createVAO() {
        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL20.glEnableVertexAttribArray(TexturedParticleProgram.POSITION_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL20.glVertexAttribPointer(TexturedParticleProgram.POSITION_ATTR, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(TexturedParticleProgram.ROTATION_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rotationVBO);
        GL20.glVertexAttribPointer(TexturedParticleProgram.ROTATION_ATTR, 1, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(TexturedParticleProgram.TEXTURE_INDEX_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
        GL20.glVertexAttribPointer(TexturedParticleProgram.TEXTURE_INDEX_ATTR, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(TexturedParticleProgram.TEXTURE_INDEX_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, gradientVBO);
        GL20.glVertexAttribPointer(TexturedParticleProgram.TEXTURE_INDEX_ATTR, 1, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void useCamera(Camera camera) {
        program.use();
        program.useCamera(camera);
    }

    @Override
    public void render(DotParticleSystem system, MatrixStack stack) {
        List<DotParticle> particles = system.getParticles();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        program.use();
        program.useMatrixStack(stack);
        GL30.glBindVertexArray(vao);
        updateVBOS(particles);
        GL11.glDrawElements(GL11.GL_POINTS, Math.min(particles.size(), MAX_PARTICLES_NUMBER), GL11.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer positions = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER * 3);
    private FloatBuffer rotations = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER);
    private FloatBuffer colors = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER * 3);
    private FloatBuffer gradients = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER);

    private void updateVBOS(List<DotParticle> particles) {
        clearBuffers();
        int particleCounter = 1;
        for (DotParticle particle : particles) {
            if (particleCounter > MAX_PARTICLES_NUMBER) break;
            putPosition(particle.getPosition());
            putRotation(particle.getRotation());
            putColor(particle.getColor());
            putGradient(particle.getGradient());
            particleCounter++;
        }
        rewindBuffers();
        storeDataInVBOs();
    }

    private void clearBuffers() {
        positions.clear();
        rotations.clear();
        colors.clear();
        gradients.clear();
    }


    private void putPosition(Vector3f position) {
        positions.put(position.x).put(position.y).put(position.z);
    }

    private void putRotation(float rotation) {
        rotations.put(rotation);
    }

    private void putColor(Vector3f color) {
        colors.put(color.x).put(color.y).put(color.z);
    }

    private void putGradient(float gradient) {
        rotations.put(gradient);
    }

    private void rewindBuffers() {
        positions.rewind();
        rotations.rewind();
        colors.rewind();
        gradients.rewind();
    }

    private void storeDataInVBOs() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positions, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rotationVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, rotations, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorVBO, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, gradientVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, gradientVBO, GL15.GL_DYNAMIC_DRAW);
    }


    public void destroy() {
        GL15.glDeleteBuffers(new int[]{positionVBO, rotationVBO, colorVBO, gradientVBO, indexBuff});
        GL30.glDeleteVertexArrays(vao);
        program.delete();
    }

}
