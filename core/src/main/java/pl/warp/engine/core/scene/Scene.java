package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 16
 */
public class Scene {

    private EngineContext context;
    private Component root;

    public Scene(EngineContext context, Component root) {
        this.context = context;
        this.root = root;
    }

    public Set<Component> getComponentsWithTag(String tag) {
        return root.getChildrenWithTag(tag);
    }


    public EngineContext getContext() {
        return context;
    }

    public Component getRoot() {
        return root;
    }
}
