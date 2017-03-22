package pl.warp.game.graphics.effects.planet;

import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 12
 */
public class PlanetBuilder {
    private GameComponent planet;

    public PlanetBuilder(GameComponent parent) {
        this.planet = new GameSceneComponent(parent);

    }

    public GameComponent makePlanet() {
   /*     Mesh sphere = new Sphere(50, 50);
        planet.addProperty(new RenderableMeshProperty(sphere));
        planet.addProperty(new CustomProgramProperty(getGasProgram()));
        planet.addProperty(new GasPlanetProperty(colors));
        TransformProperty transformProperty = new TransformProperty();
        planet.addProperty(transformProperty);*/
        return null;
    }


}
