package pl.warp.engine.graphics.particles.textured;

        import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.Particle;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 19
 */
public class TexturedParticle extends Particle {

    private Texture2DArray spritesheet;
    private int textureIndex;

    protected TexturedParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int totalTimeToLive, int timeToLive, int textureIndex, Texture2DArray spritesheet) {
        super(position, velocity, scale, rotation, totalTimeToLive, timeToLive);
        this.textureIndex = textureIndex;
        this.spritesheet = spritesheet;
    }

    public Texture2DArray getSpritesheet() {
        return spritesheet;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }
}
