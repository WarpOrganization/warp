package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.graphics.program.MeshRendererProgram;

/**
 * @author Jaca777
 *         Created 2016-08-02 at 18
 */
public class CustomProgramProperty extends Property<Component> {
    public static final String CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME = "customRendererProgram";
    private MeshRendererProgram program;

    public CustomProgramProperty(MeshRendererProgram program) {
        super(CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME);
        this.program = program;
    }

    public MeshRendererProgram getProgram() {
        return program;
    }
}
