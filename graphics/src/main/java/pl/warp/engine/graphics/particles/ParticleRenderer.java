package pl.warp.engine.graphics.particles;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2016-08-08 at 20
 */
public interface ParticleRenderer<T extends ParticleSystem> {
    void render(T system, MatrixStack stack);

    void useCamera(Camera camera);
}
