package pl.warp.game.graphics.effects.ring;

import pl.warp.engine.graphics.mesh.CustomMeshProgramProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Ring;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class PlanetRing extends GameSceneComponent {
    private float startRadius;
    private float endRadius;
    private Texture1D colors;

    public PlanetRing(GameComponent parent, float startRadius, float endRadius, Texture1D colors) {
        super(parent);
        this.startRadius = startRadius;
        this.endRadius = endRadius;
        this.colors = colors;
        init();
    }

    private void init() {
        Ring ringMesh = new Ring(20, startRadius, endRadius);
        this.addProperty(new GraphicsMeshProperty(ringMesh));
        this.addProperty(new CustomMeshProgramProperty(getPlanetaryRingProgram()));
        this.addProperty(new PlanetRingProperty(startRadius, endRadius, colors));
    }

    public PlanetRingProgram getPlanetaryRingProgram() {
        GameScene scene = getContext().getScene();
        if (scene.hasEnabledProperty(PlanetRingContextProperty.PLANETARY_RING_CONTEXT_PROPERTY_NAME)) {
            PlanetRingContextProperty property = scene.getProperty(PlanetRingContextProperty.PLANETARY_RING_CONTEXT_PROPERTY_NAME);
            return property.getProgram();
        } else {
            PlanetRingProgram program = new PlanetRingProgram();
            getContext().getScene().addProperty(new PlanetRingContextProperty(program));
            return program;
        }
    }
}
