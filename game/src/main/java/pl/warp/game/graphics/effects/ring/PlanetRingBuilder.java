package pl.warp.game.graphics.effects.ring;

import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Ring;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.graphics.effects.GameComponentBuilder;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

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

    public PlanetRingProgram getPlanetaryRingProgram() {
        GameScene scene = parent.getContext().getScene();
        if (scene.hasEnabledProperty(PlanetRingContextProperty.PLANETARY_RING_CONTEXT_PROPERTY_NAME)) {
            PlanetRingContextProperty property = scene.getProperty(PlanetRingContextProperty.PLANETARY_RING_CONTEXT_PROPERTY_NAME);
            return property.getProgram();
        } else {
            PlanetRingProgram program = new PlanetRingProgram();
            parent.getContext().getScene().addProperty(new PlanetRingContextProperty(program));
            return program;
        }
    }
}
