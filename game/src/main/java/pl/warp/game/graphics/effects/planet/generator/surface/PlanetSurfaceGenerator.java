package pl.warp.game.graphics.effects.planet.generator.surface;

import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class PlanetSurfaceGenerator {

    private PlanetSurfaceSource surfaceSource;
    private Biome[] biomes;

    public PlanetSurfaceGenerator(int width, int height, Biome[] biomes) {
        this.surfaceSource = new PlanetSurfaceSource(width, height, biomes);
    }

    public Cubemap generate(Graphics graphics){
        surfaceSource.init(graphics);
        surfaceSource.update();
        return surfaceSource.getOutput();
    }
}
