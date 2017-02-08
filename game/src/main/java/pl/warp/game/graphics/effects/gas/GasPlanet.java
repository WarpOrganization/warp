package pl.warp.game.graphics.effects.gas;

import pl.warp.engine.graphics.mesh.CustomMeshProgramProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class GasPlanet extends GameSceneComponent {
    private Texture1D colors;

    public GasPlanet(GameComponent parent, Texture1D colors) {
        super(parent);
        this.colors = colors;
        init();
    }

    private void init() {
        Mesh sphere = new Sphere(50, 50);
        this.addProperty(new GraphicsMeshProperty(sphere));
        this.addProperty(new CustomMeshProgramProperty(getGasProgram()));
        this.addProperty(new GasPlanetProperty(colors));
    }

    private GasPlanetProgram getGasProgram() {
        GameScene scene = getContext().getScene();
        if (scene.hasEnabledProperty(GasPlanetContextProperty.GAS_PLANET_CONTEXT_PROPERTY_NAME)) {
            GasPlanetContextProperty property = scene.getProperty(GasPlanetContextProperty.GAS_PLANET_CONTEXT_PROPERTY_NAME);
            return property.getProgram();
        } else {
            GasPlanetProgram gasPlanetProgram = new GasPlanetProgram();
            getContext().getGraphics().getThread().scheduleTask(new GasPlanetTask(gasPlanetProgram));
            getContext().getScene().addProperty(new GasPlanetContextProperty(gasPlanetProgram));
            return gasPlanetProgram;
        }
    }
}
