package pl.warp.engine.game.graphics.effects.planet.generator.surface;

import org.joml.Matrix3f;
import pl.warp.engine.graphics.pipeline.rendering.CubemapRenderingProgram;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class PlanetSurfaceGeneratorProgram extends CubemapRenderingProgram {

    private static final String PROGRAM_PATH = "pl/warp/engine/game/graphics/effects/planet/generator/";
    private static final String VERTEX_SHADER = "surface/vert";
    private static final String GEOMETRY_SHADER = "surface/geom";
    private static final String FRAGMENT_SHADER = "surface/frag";

    private static final int MAX_BIOME_COUNT = 30;
    protected static final ConstantField CONSTANT_FIELD = new ConstantField().set("BIOME_COUNT", PlanetSurfaceGeneratorProgram.MAX_BIOME_COUNT);

    private static final int BIOME_POS = 0;
    private static final int BIOME_HEIGHT = 1;
    private static final int BIOME_COLOR = 2;
    private static final String[] BIOME_FIELD_NAMES =
            {"pos", "height", "color"};

    private int[] unifMatrices;
    private int[][] unifBiomes;
    private int unifBiomeCount;

    public PlanetSurfaceGeneratorProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, GEOMETRY_SHADER,
                new ExtendedGLSLProgramCompiler(CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifMatrices = new int[6];
        for (int i = 0; i < 6; i++)
            this.unifMatrices[i] = getUniformLocation("matrices[" + i + "]");
        this.unifBiomes = new int[MAX_BIOME_COUNT][BIOME_FIELD_NAMES.length];
        this.unifBiomeCount = getUniformLocation("biomeCount");
        loadBiomes();
    }

    private void loadBiomes() {
        for(int i = 0; i < MAX_BIOME_COUNT; i++){
            for(int j = 0; j < BIOME_FIELD_NAMES.length; j++)
                unifBiomes[i][j] = getUniformLocation("biomes[" + i + "]." + BIOME_FIELD_NAMES[j]);
        }
    }

    @Override
    public void useMatrices(Matrix3f[] matrices) {
        for (int i = 0; i < 6; i++)
            setUniformMatrix3(unifMatrices[i], matrices[i]);
    }

    public void useBiomes(Biome[] biomes){
        for(int i = 0; i < biomes.length; i++){
            Biome biome = biomes[i];
            setUniformf(unifBiomes[i][BIOME_POS], biome.getPos());
            setUniformf(unifBiomes[i][BIOME_HEIGHT], biome.getHeight());
            setUniformV3(unifBiomes[i][BIOME_COLOR], biome.getColor());
        }
        setUniformi(unifBiomeCount, biomes.length);
    }


}
