package pl.warp.engine.graphics.program.rendering.component.terrain;

import org.joml.Vector2f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.graphics.program.extendedglsl.LocalProgramLoader;
import pl.warp.engine.graphics.program.rendering.component.defaultprog.DefaultMeshProgram;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.terrain.TerrainProperty;

/**
 * @author Jaca777
 *         Created 2017-03-04 at 11
 */
public class TerrainProgram extends DefaultMeshProgram {

    private static final String VERTEX_SHADER = "component/terrain/vert";
    private static final String FRAGMENT_SHADER = "component/terrain/frag";

    private static final int NORMAL_MAP_SAMPLER = 2;
    private int unifScale;

    public TerrainProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
        this.unifScale = getUniformLocation("scale");
        setTextureLocation("material.normalMap", NORMAL_MAP_SAMPLER);
    }

    private Vector2f scale = new Vector2f();
    @Override
    public void useComponent(Component component) {
        super.useComponent(component);
        TerrainProperty terrain = component.getProperty(TerrainProperty.TERRAIN_PROPERTY_NAME);
        this.scale.set(terrain.getTerrainSize());
        setUniformV2(unifScale, scale);
        useTexture(terrain.getNormalMap(), NORMAL_MAP_SAMPLER);
    }
}
