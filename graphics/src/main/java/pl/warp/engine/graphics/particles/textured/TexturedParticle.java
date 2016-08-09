package pl.warp.engine.graphics.particles.textured;

        import org.joml.Vector2f;
        import org.joml.Vector3f;
        import pl.warp.engine.graphics.particles.Particle;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 19
 */
public class TexturedParticle extends Particle {

    private int textureIndex;

    public TexturedParticle(Vector3f position, Vector3f velocity, Vector2f scale, float rotation, int totalTimeToLive, int timeToLive, int textureIndex) {
        super(position, velocity, scale, rotation, totalTimeToLive, timeToLive);
        this.textureIndex = textureIndex;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }
}
