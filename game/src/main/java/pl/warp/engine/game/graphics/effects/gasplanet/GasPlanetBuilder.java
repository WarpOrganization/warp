package pl.warp.engine.game.graphics.effects.gasplanet;

import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.execution.task.update.UpdaterTask;
import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.game.script.OwnerProperty;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.program.pool.ProgramPool;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.engine.game.graphics.effects.GameComponentBuilder;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 19
 */
public class GasPlanetBuilder implements GameComponentBuilder {


    private GameComponent parent;
    private Texture1D colors;

    public GasPlanetBuilder(GameComponent parent, Texture1D colors) {
        this.parent = parent;
        this.colors = colors;
    }

    public GasPlanetBuilder setColors(Texture1D colors) {
        this.colors = colors;
        return this;
    }

    @Override
    public GameComponent build() {
        GameComponent gasPlanet = new GameSceneComponent(parent);
        Mesh sphere = new Sphere(50, 50);
        gasPlanet.addProperty(new RenderableMeshProperty(sphere));
        gasPlanet.addProperty(new CustomProgramProperty(getGasPlanetProgram()));
        gasPlanet.addProperty(new GasPlanetProperty(colors));
        TransformProperty transformProperty = new TransformProperty();
        gasPlanet.addProperty(transformProperty);
        rotate(gasPlanet);
        return gasPlanet;
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

    private GasPlanetProgram getGasPlanetProgram() {
        ProgramPool programPool = parent.getContext().getGraphics().getProgramPool();
        return programPool.getProgram(GasPlanetProgram.class).orElse(createPlanetProgram(programPool));
    }

    private GasPlanetProgram createPlanetProgram(ProgramPool programPool) {
        GasPlanetProgram program = new GasPlanetProgram();
        scheduleProgramUpdater(program);
        programPool.registerProgram(program);
        return program;
    }

    private void scheduleProgramUpdater(GasPlanetProgram program) {
        parent.getContext().getGraphics().getThread().scheduleTask(new UpdaterTask(program));
    }

}
