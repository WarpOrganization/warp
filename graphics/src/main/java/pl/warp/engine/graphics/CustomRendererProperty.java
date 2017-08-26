package pl.warp.engine.graphics;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 01
 */
public class CustomRendererProperty extends Property<Component> {
    public static final String CUSTOM_RENDERER_PROPERTY_NAME = "customRendererProperty";

    private CustomRenderer renderer;

    public CustomRendererProperty(CustomRenderer renderer) {
        super(CUSTOM_RENDERER_PROPERTY_NAME);
        this.renderer = renderer;
    }

    public CustomRenderer getRenderer() {
        return renderer;
    }
}
