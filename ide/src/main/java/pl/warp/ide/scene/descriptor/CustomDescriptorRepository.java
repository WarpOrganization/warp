package pl.warp.ide.scene.descriptor;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.ide.scene.ComponentDescriptor;


/**
 * Created by user on 2017-01-17.
 */
public class CustomDescriptorRepository implements DescriptorRepository {
    private ComponentDescriptor sceneDesc;
    private ComponentDescriptor drawableModelDesc;
    private ComponentDescriptor componentDesc;


    public CustomDescriptorRepository(ComponentDescriptor sceneDesc,
                                      ComponentDescriptor drawableModelDesc,
                                      ComponentDescriptor componentDesc) {
        this.sceneDesc = sceneDesc;
        this.drawableModelDesc = drawableModelDesc;
        this.componentDesc = componentDesc;
    }

    @Override
    public ComponentDescriptor getDesc(Component component) {
        if (isDrawableModel(component)) return drawableModelDesc;
        else if (isScene(component)) return sceneDesc;
        else return componentDesc;
    }

    private boolean isDrawableModel(Component component) {
        return component.hasProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME);
    }

    private boolean isScene(Component component) {
        return component instanceof Scene;
    }
}
