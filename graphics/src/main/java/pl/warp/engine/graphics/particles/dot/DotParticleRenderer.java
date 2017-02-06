package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.particles.ParticleRenderer;
import pl.warp.engine.graphics.shader.program.particle.dot.DotParticleProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static pl.warp.engine.graphics.particles.ParticleSystemRenderer.MAX_PARTICLES_NUMBER;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 18
 */
public class DotParticleRenderer implements ParticleRenderer<DotParticleSystem> {

    public static final float FADE_AFTER = 1000;
    private DotParticleProgram program;

    private int positionVBO;
    private int scaleVBO;
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
        this.scaleVBO = GL15.glGenBuffers();
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

        GL20.glEnableVertexAttribArray(DotParticleProgram.POSITION_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL20.glVertexAttribPointer(DotParticleProgram.POSITION_ATTR, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(DotParticleProgram.COLOR_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
        GL20.glVertexAttribPointer(DotParticleProgram.COLOR_ATTR, 4, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(DotParticleProgram.GRADIENT_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, gradientVBO);
        GL20.glVertexAttribPointer(DotParticleProgram.GRADIENT_ATTR, 1, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(DotParticleProgram.SCALE_ATTR);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, scaleVBO);
        GL20.glVertexAttribPointer(DotParticleProgram.SCALE_ATTR, 1, GL11.GL_FLOAT, false, 0, 0);

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
        GL11.glDepthMask(true); // <- REMOVE TO RELEASE THE KRAKEN
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer positions = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER * 3);
    private FloatBuffer colors = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER * 4);
    private FloatBuffer gradients = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER);
    private FloatBuffer scales = BufferUtils.createFloatBuffer(MAX_PARTICLES_NUMBER);

    private void updateVBOS(List<DotParticle> particles) {
        clearBuffers();
        int particleCounter = 1;
        for (DotParticle particle : particles) {
            if (particleCounter > MAX_PARTICLES_NUMBER) break;
            ParticleStage stage = particle.getStage();
            putPosition(particle.getPosition());
            putColor(stage.getColor());
            putGradient(stage.getGradient());
            putScale(particle.getScale());
            particleCounter++;
        }
        rewindBuffers();
        storeDataInVBOs();
    }

    private void clearBuffers() {
        positions.clear();
        scales.clear();
        colors.clear();
        gradients.clear();
    }


    private void putPosition(Vector3f position) {
        positions.put(position.x).put(position.y).put(position.z);
    }

    private void putColor(Vector4f color) {
        colors.put(color.x).put(color.y).put(color.z).put(color.w);
    }

    private void putGradient(float gradient) {
        gradients.put(gradient);
    }

    private void putScale(float rotation) {
        scales.put(rotation);
    }

    private void rewindBuffers() {
        positions.rewind();
        scales.rewind();
        colors.rewind();
        gradients.rewind();
    }

    private void storeDataInVBOs() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positions, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, scaleVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, scales, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colors, GL15.GL_DYNAMIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, gradientVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, gradients, GL15.GL_DYNAMIC_DRAW);
    }


    public void destroy() {
        GL15.glDeleteBuffers(new int[]{positionVBO, scaleVBO, colorVBO, gradientVBO, indexBuff});
        GL30.glDeleteVertexArrays(vao);
        program.delete();
    }

}
