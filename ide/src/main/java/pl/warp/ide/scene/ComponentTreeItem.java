package pl.warp.ide.scene;

import javafx.scene.control.TreeItem;
import pl.warp.engine.core.scene.Component;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentTreeItem<T extends Component> extends TreeItem<ComponentItem<T>> {
    private ComponentLook desc;

    public ComponentTreeItem(T component, ComponentLook look) {
        super(new ComponentItem<>(component, look.createName(component)), look.createImage());
        this.desc = look;
    }

    public ComponentLook getDesc() {
        return desc;
    }
}
