package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.shader.MeshRendererProgram;

/**
 * @author Jaca777
 *         Created 2016-08-02 at 18
 */
public class CustomMeshProgramProperty extends Property<Component> {
    public static final String CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME = "customRendererProgram";
    private MeshRendererProgram program;

    public CustomMeshProgramProperty(MeshRendererProgram program) {
        super(CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME);
        this.program = program;
    }

    public MeshRendererProgram getProgram() {
        return program;
    }
}
