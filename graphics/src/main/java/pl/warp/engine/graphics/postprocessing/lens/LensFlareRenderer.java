package pl.warp.engine.graphics.postprocessing.lens;

import org.joml.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.math.Transforms;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-07-25 at 12
 */
public class LensFlareRenderer implements Flow<Texture2D, Texture2D> {

    private Camera camera;
    private Environment environment;

    private Texture2D scene;
    private TextureFramebuffer framebuffer;

    public LensFlareRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void update(int delta) {
        for (Component component : environment.getLensFlareComponents()) {
            GraphicsLensFlareProperty property = component.getProperty(GraphicsLensFlareProperty.LENS_FLARE_PROPERTY_NAME);
            renderFlare(component, property.getFlare());
        }
    }

    private Vector3f tempVec3 = new Vector3f();
    private Matrix3f tempMat3 = new Matrix3f();
    private Vector2f tempVec2 = new Vector2f();

    private void renderFlare(Component component, LensFlare flare) {
        Vector3f flarePosition = Transforms.getActualPosition(component, tempVec3);
        ProjectionMatrix projectionMatrix = camera.getProjectionMatrix();
        Vector3f flareScreenPos = flarePosition.mul(projectionMatrix.getMatrix().get3x3(tempMat3));
        if (isInRange(flareScreenPos))
            renderFlare(flareScreenPos, flare);
    }

    private boolean isInRange(Vector3f pos) {
        return pos.x > -1.0f && pos.x < 1.0f &&
                pos.y > -1.0f && pos.y < 1.0f &&
                pos.z > 0.0f && pos.z < 1.0f ;
    }

    private void renderFlare(Vector3f flareScreenPositon, LensFlare flare) {
        Vector2f flare2DPos = tempVec2.set(flareScreenPositon.x, flareScreenPositon.y);
        float distance = flare2DPos.length();
        Vector2f direction = flare2DPos.normalize();
        for(SingleFlare singleFlare : flare.getFlares())
            renderSingleFlare(singleFlare, direction, distance);
    }

    private void renderSingleFlare(SingleFlare singleFlare, Vector2f direction, float distance) {
        float visiblity = getVisibility(distance);
        //TODO
    }

    private float getVisibility(float distance) {
        return 1.0f - (distance / 2f);
    }


    @Override
    public void init() {
        this.framebuffer = new TextureFramebuffer(scene);
    }

    @Override
    public void destroy() {

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
