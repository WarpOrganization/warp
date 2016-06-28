package pl.warp.engine.core.scene;

import org.joml.Vector3d;
import pl.warp.engine.core.EngineContext;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 16
 */
public class Scene {

    private EngineContext context;
    private Parent root;

    public Scene(EngineContext context, Parent root) {
        this.context = context;
        this.root = root;
    }

    public Component getComponentByName(String name) {
        return root.getChildByName(name); //TODO tags? Name is not a good idea.
    }

    public EngineContext getContext() {
        return context;
    }

    public Parent getRoot() {
        return root;
    }
}
