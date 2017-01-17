package pl.warp.ide.scene;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import pl.warp.engine.core.scene.Component;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentItem<T extends Component> extends TreeItem<T> {
    private T component;
    private ComponentDescriptor desc;

    public ComponentItem(T component,ComponentDescriptor desc) {
        super(component, desc.getImage());
        this.component = component;
        this.desc = desc;
    }

    public T getComponent() {
        return component;
    }

    @Override
    public String toString() {
        return desc.getName();
    }
}
