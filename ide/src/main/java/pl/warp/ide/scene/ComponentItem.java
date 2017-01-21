package pl.warp.ide.scene;

import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2017-01-21 at 16
 */
public class ComponentItem<T extends Component>  {
    private T component;
    private String name;

    public ComponentItem(T component, String name) {
        this.component = component;
        this.name = name;
    }

    public T getComponent() {
        return component;
    }

    public void setComponent(T component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
