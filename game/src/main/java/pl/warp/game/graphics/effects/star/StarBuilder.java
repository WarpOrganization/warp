package pl.warp.game.graphics.effects.star;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.updater.Updatable;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.CustomRendererProperty;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.game.graphics.effects.GameComponentBuilder;
import pl.warp.game.graphics.effects.star.corona.CoronaProgram;
import pl.warp.game.graphics.effects.star.corona.CoronaProperty;
import pl.warp.game.graphics.effects.star.corona.CoronaRenderer;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-02-08 at 23
 */
public class StarBuilder implements GameComponentBuilder {

    private GameComponent parent;
    private Texture1D temperatureTexture;
    private float temperature = 5000f;
    private Component corona;
    private StarContextProperty contextProperty;

    public StarBuilder(GameComponent parent, Texture1D temperatureTexture) {
        this.parent = parent;
        this.temperatureTexture = temperatureTexture;
    }

    public StarBuilder setTemperatureTexture(Texture1D temperatureTexture) {
        this.temperatureTexture = temperatureTexture;
        return this;
    }

    public StarBuilder setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    @Override
    public GameComponent build() {
        GameComponent star = new GameSceneComponent(parent);
        Mesh sphere = new Sphere(50, 50);
        star.addProperty(new RenderableMeshProperty(sphere));
        contextProperty = getContextProperty();
        star.addProperty(new CustomProgramProperty(contextProperty.getStarProgram()));
        star.addProperty(new StarProperty(temperature));
        star.addProperty(new RenderableMeshProperty(sphere));
        createCorona(star);
        return star;
    }

    private void createCorona(GameComponent star) {
        corona = new GameSceneComponent(star);
        corona.addProperty(new CustomRendererProperty(contextProperty.getCoronaRenderer()));
        corona.addProperty(new CoronaProperty(temperature, 2.0f));
    }

    private StarContextProperty getContextProperty() {
        GameScene scene = parent.getContext().getScene();
        if (scene.hasEnabledProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME))
            return scene.getProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME);
        else return createContext();
    }

    private StarContextProperty createContext() {
        StarProgram starProgram = new StarProgram(temperatureTexture);
        scheduleUpdater(starProgram);
        CoronaProgram coronaProgram = new CoronaProgram(temperatureTexture);
        scheduleUpdater(coronaProgram);
        CoronaRenderer coronaRenderer = new CoronaRenderer(parent.getContext().getGraphics(), coronaProgram);
        StarContextProperty property = new StarContextProperty(starProgram, coronaProgram, coronaRenderer);
        parent.getContext().getScene().addProperty(property);
        return property;
    }

    private void scheduleUpdater(Updatable updatable) {
        parent.getContext().getGraphics().getThread().scheduleTask(new UpdaterTask(updatable));
    }


}
