package pl.warp.game.graphics.effects.gas;

import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.GameScript;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class GasPlanet extends GameSceneComponent {

    public static final float ROTATION_SPEED = 0.00004f;

    private Texture1D colors;
    private TransformProperty transformProperty;

    public GasPlanet(GameComponent parent, Texture1D colors) {
        super(parent);
        this.colors = colors;
        init();
    }

    private void init() {
        Mesh sphere = new Sphere(50, 50);
        this.addProperty(new RenderableMeshProperty(sphere));
        this.addProperty(new CustomProgramProperty(getGasProgram()));
        this.addProperty(new GasPlanetProperty(colors));
        this.transformProperty = new TransformProperty();
        this.addProperty(transformProperty);
        rotate();
    }

    private void rotate() {
        new GameScript<GameComponent>(this) {

            @Override
            protected void init() {

            }

            @Override
            protected void update(int delta) {
                transformProperty.rotateY(ROTATION_SPEED * delta);
            }
        };
    }

    private GasPlanetProgram getGasProgram() {
        GameScene scene = getContext().getScene();
        if (scene.hasEnabledProperty(GasPlanetContextProperty.GAS_PLANET_CONTEXT_PROPERTY_NAME)) {
            GasPlanetContextProperty property = scene.getProperty(GasPlanetContextProperty.GAS_PLANET_CONTEXT_PROPERTY_NAME);
            return property.getProgram();
        } else {
            GasPlanetProgram gasPlanetProgram = new GasPlanetProgram();
            getContext().getGraphics().getThread().scheduleTask(new UpdaterTask(gasPlanetProgram));
            getContext().getScene().addProperty(new GasPlanetContextProperty(gasPlanetProgram));
            return gasPlanetProgram;
        }
    }
}
