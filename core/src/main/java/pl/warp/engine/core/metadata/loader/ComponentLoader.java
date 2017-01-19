package pl.warp.engine.core.metadata.loader;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.metadata.ComponentMetadata;

/**
 * @author Jaca777
 *         Created 2016-12-13 at 19
 */
public class ComponentLoader {

    private PropertyLoader propertyLoader;
    private EngineContext context;

    public ComponentLoader(PropertyLoader propertyLoader, EngineContext context) {
        this.propertyLoader = propertyLoader;
        this.context = context;
    }

    public Component loadComponentMetadata(ComponentMetadata metadata){
        Component component = metadata.newInstance(context);
        loadProperties(metadata, component);
        loadChildren(metadata, component);
        return component;
    }

    private void loadProperties(ComponentMetadata metadata, Component component) {
        metadata.getPropertyMetadata()
                .forEach(propertyMetadata -> propertyLoader.loadPropertyMetadata(propertyMetadata, component));
    }

    private void loadChildren(ComponentMetadata metadata, Component component) {
/*        metadata.get(md -> {
            if (md instanceof ComponentMetadata) {
                Component child = loadComponentMetadata((ComponentMetadata) md);
                component.addChild(child);
            }
        });*/ //TODO
    }
}
