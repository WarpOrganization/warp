package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;

/**
 * @author Jaca777
 *         Created 2016-08-02 at 18
 */
public class GraphicsCustomRendererProgramProperty extends Property<Component> {
    public static final String CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME = "customRendererProgram";
    private ComponentRendererProgram program;

    public GraphicsCustomRendererProgramProperty(Component owner, ComponentRendererProgram program) {
        super(owner);
        this.program = program;
    }

    public ComponentRendererProgram getProgram() {
        return program;
    }
}
