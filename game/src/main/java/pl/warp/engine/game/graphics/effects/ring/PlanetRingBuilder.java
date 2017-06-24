package pl.warp.engine.game.graphics.effects.ring;

import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Ring;
import pl.warp.engine.graphics.program.pool.ProgramPool;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.engine.game.graphics.effects.GameComponentBuilder;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class PlanetRingBuilder implements GameComponentBuilder {

    private GameComponent parent;
    private float startRadius = 1.5f;
    private float endRadius = 2.5f;
    private Texture1D colors;

    public PlanetRingBuilder(GameComponent parent, Texture1D colors) {
        this.parent = parent;
        this.colors = colors;
    }

    public PlanetRingBuilder setStartRadius(float startRadius) {
        this.startRadius = startRadius;
        return this;
    }

    public PlanetRingBuilder setEndRadius(float endRadius) {
        this.endRadius = endRadius;
        return this;
    }

    public PlanetRingBuilder setColors(Texture1D colors) {
        this.colors = colors;
        return this;
    }

    @Override
    public GameComponent build() {
        GameComponent gameComponent = new GameSceneComponent(parent);
        Ring ringMesh = new Ring(20, startRadius, endRadius);
        gameComponent.addProperty(new RenderableMeshProperty(ringMesh));
        gameComponent.addProperty(new CustomProgramProperty(getPlanetaryRingProgram()));
        gameComponent.addProperty(new PlanetRingProperty(startRadius, endRadius, colors, true));
        return gameComponent;
    }

    private PlanetRingProgram getPlanetaryRingProgram() {
        ProgramPool programPool = parent.getContext().getGraphics().getProgramPool();
        return programPool.getProgram(PlanetRingProgram.class).orElse(createRingProgram(programPool));
    }

    private PlanetRingProgram createRingProgram(ProgramPool programPool) {
        PlanetRingProgram program = new PlanetRingProgram();
        programPool.registerProgram(program);
        return program;
    }
}
