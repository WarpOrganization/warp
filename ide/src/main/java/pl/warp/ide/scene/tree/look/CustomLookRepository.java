package pl.warp.ide.scene.tree.look;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.ide.scene.tree.ComponentLook;

import java.util.Arrays;
import java.util.List;


/**
 * Created by user on 2017-01-17.
 */
public class CustomLookRepository implements ComponentLookRepository {

    private List<ComponentTypeLook> componentTypeLooks;


    public CustomLookRepository(ComponentTypeLook... looks) {
        this.componentTypeLooks = Arrays.asList(looks);
    }

    @Override
    public ComponentLook getDesc(Component component) {
        return componentTypeLooks
                .stream()
                .filter(f -> f.applies(component))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No type descriptor found."))
                .getDescriptor();
    }


    private boolean isScene(Component component) {
        return component instanceof Scene;
    }
}
