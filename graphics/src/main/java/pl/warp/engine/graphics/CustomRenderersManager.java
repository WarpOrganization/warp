package pl.warp.engine.graphics;

import pl.warp.engine.graphics.camera.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 16
 */
public class CustomRenderersManager {

    public CustomRenderersManager(Graphics graphics) {
        this.graphics = graphics;
    }

    private Graphics graphics;
    private List<CustomRenderer>  customRenderers = new ArrayList<>();

    public void registerRenderer(CustomRenderer renderer) {
        graphics.getThread().scheduleOnce(() -> {
            renderer.init();
            renderer.enable();
            customRenderers.add(renderer);
        });

    }

    public void resize(int newWidth, int newHeight){
        customRenderers.forEach(r -> r.resize(newWidth, newHeight));
    }

    public void setMainViewCamera(Camera camera){
        customRenderers.forEach(r -> r.setMainViewCamera(camera));
    }
}
