package pl.warp.engine.game.metadata.loader;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.game.metadata.ComponentMetadata;

/**
 * @author Jaca777
 *         Created 2016-12-13 at 19
 */
public class ComponentLoader {

    private PropertyLoader propertyLoader;
    private GameContext context;

    public ComponentLoader(PropertyLoader propertyLoader, GameContext context) {
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
/*        metadata.makePlanet(md -> {
            if (md instanceof ComponentMetadata) {
                Component child = loadComponentMetadata((ComponentMetadata) md);
                component.addChild(child);
            }
        });*/ //TODO
    }
}
