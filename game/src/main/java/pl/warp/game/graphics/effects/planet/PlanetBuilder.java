package pl.warp.game.graphics.effects.planet;

import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.texture.Cubemap;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 12
 */
public class PlanetBuilder {
    private GameComponent parent;
    private Cubemap planetSurfaceTexture;

    public PlanetBuilder(GameComponent parent, Cubemap planetSurfaceTexture) {
        this.parent = parent;
        this.planetSurfaceTexture = planetSurfaceTexture;
    }

    public GameComponent makePlanet() {
        GameComponent planet = new GameSceneComponent(parent);
        Mesh sphere = new Sphere(100, 100);
        planet.addProperty(new RenderableMeshProperty(sphere));
        planet.addProperty(new CustomProgramProperty(getPlanetPrgoram()));
        planet.addProperty(new PlanetProperty(planetSurfaceTexture));
        TransformProperty transformProperty = new TransformProperty();
        planet.addProperty(transformProperty);
        rotate(planet);
        return planet;
    }

    private static final float ROTATION_SPEED = 0.00004f;

    private void rotate(GameComponent component) {
        new GameScript<GameComponent>(component) {


            @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
            private TransformProperty transform;

            @Override
            protected void init() {

            }

            @Override
            protected void update(int delta) {
                transform.rotateY(ROTATION_SPEED * delta);
            }
        };
    }

    private PlanetProgram getPlanetPrgoram() {
        GameScene scene = parent.getContext().getScene();
        if (scene.hasEnabledProperty(PlanetContextProperty.PLANET_CONTEXT_PROPERTY)) {
            PlanetContextProperty property = scene.getProperty(PlanetContextProperty.PLANET_CONTEXT_PROPERTY);
            return property.getProgram();
        } else {
            PlanetProgram gasPlanetProgram = new PlanetProgram();
            parent.getContext().getScene().addProperty(new PlanetContextProperty(gasPlanetProgram));
            return gasPlanetProgram;
        }
    }

}
