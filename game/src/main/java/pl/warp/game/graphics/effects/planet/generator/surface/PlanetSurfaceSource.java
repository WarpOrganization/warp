package pl.warp.game.graphics.effects.planet.generator.surface;

import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.rendering.CubemapTextureSource;
import pl.warp.engine.graphics.texture.Cubemap;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class PlanetSurfaceSource extends CubemapTextureSource<PlanetSurfaceGeneratorProgram> {

    public static final ByteBuffer[] INITIAL_CUBEMAP_DATA = {null, null, null, null, null, null};
    private int width, height;
    private Cubemap output;
    private Biome[] biomes;

    public PlanetSurfaceSource(int width, int height, Biome[] biomes) {
        super(new PlanetSurfaceGeneratorProgram());
        this.width = width;
        this.height = height;
        this.biomes = biomes;
    }

    @Override
    public void init(Graphics graphics) {
        this.output = new Cubemap(width, height, INITIAL_CUBEMAP_DATA);
        super.init(graphics);
    }

    @Override
    public Cubemap getOutput() {
        return output;
    }

    @Override
    protected void prepareProgram(PlanetSurfaceGeneratorProgram program) {
        super.prepareProgram(program);
        program.useBiomes(biomes);
    }
}
