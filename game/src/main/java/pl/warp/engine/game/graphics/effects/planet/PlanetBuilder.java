package pl.warp.engine.game.graphics.effects.planet;

import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.script.OwnerProperty;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.game.graphics.effects.planet.generator.surface.Biome;
import pl.warp.engine.game.graphics.effects.planet.generator.surface.PlanetSurfaceGenerator;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.program.pool.ProgramPool;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 12
 */
public class PlanetBuilder {

    private GameComponent parent;
    private Biome[] biomes;

    public PlanetBuilder(GameComponent parent, Biome[] biomes) {
        this.parent = parent;
        this.biomes = biomes;
    }

    public GameComponent makePlanet() {
        GameComponent planet = new GameSceneComponent(parent);
        Mesh sphere = new Sphere(100, 100);
        planet.addProperty(new RenderableMeshProperty(sphere));
        planet.addProperty(new CustomProgramProperty(getPlanetProgram()));
        planet.addProperty(new PlanetProperty(generateSurface()));
        TransformProperty transformProperty = new TransformProperty();
        planet.addProperty(transformProperty);
        rotate(planet);
        return planet;
    }

    private Cubemap generateSurface() {
        PlanetSurfaceGenerator generator = new PlanetSurfaceGenerator(4096, 4096, biomes);
        return generator.generate(parent.getContext().getGraphics());
    }

    private static final float ROTATION_SPEED = 0.00004f;

    private void rotate(GameComponent component) {
        new Script(component) {


            @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
            private TransformProperty transform;

            @Override
            public void onInit() {

            }

            @Override
            public void onUpdate(int delta) {
                transform.rotateY(ROTATION_SPEED * delta);
            }
        };
    }

    private PlanetProgram getPlanetProgram() {
        ProgramPool programPool = parent.getContext().getGraphics().getProgramPool();
        return programPool.getProgram(PlanetProgram.class).orElse(createPlanetProgram(programPool));
    }

    private PlanetProgram createPlanetProgram(ProgramPool programPool) {
        PlanetProgram program = new PlanetProgram();
        programPool.registerProgram(program);
        return program;
    }


}
