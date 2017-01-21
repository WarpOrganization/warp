package pl.warp.ide.scene;

import javafx.scene.control.TreeItem;
import pl.warp.engine.core.scene.Component;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentTreeItem<T extends Component> extends TreeItem<ComponentItem<T>> {
    private ComponentDescriptor desc;

    public ComponentTreeItem(T component, ComponentDescriptor desc) {
        super(new ComponentItem<>(component, desc.createName(component)), desc.createImage());
        this.desc = desc;
    }

    public ComponentDescriptor getDesc() {
        return desc;
    }
}
