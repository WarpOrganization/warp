package pl.warp.engine.graphics.particles;

import org.joml.Matrix4f;
import pl.warp.engine.graphics.camera.Camera;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 20
 */
public interface ParticleRenderer<T extends Particle> {
    void render(ParticleSystem<T> system, Matrix4f transformation);

    void useCamera(Camera camera);

    void destroy();

    void initialize();
}
