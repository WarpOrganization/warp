package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 01
 */
public class CustomRendererProperty extends Property<Component> {
    public static final String CUSTOM_RENDERER_PROPERTY_NAME = "customRendererProperty";

    private Renderer renderer;

    public CustomRendererProperty(Renderer renderer) {
        super(CUSTOM_RENDERER_PROPERTY_NAME);
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
