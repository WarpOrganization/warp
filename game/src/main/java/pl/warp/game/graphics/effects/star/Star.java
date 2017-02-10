package pl.warp.game.graphics.effects.star;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.updater.Updatable;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.CustomRendererProperty;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.texture.Texture1D;
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
public class Star extends GameSceneComponent {

    private float temperature;
    private Component corona;
    private StarContextProperty contextProperty;

    public Star(GameComponent parent, float temperature) {
        super(parent);
        this.temperature = temperature;
        init();
    }

    private void init() {
        Mesh sphere = new Sphere(50, 50);
        this.addProperty(new RenderableMeshProperty(sphere));
        contextProperty = getContextProperty();
        this.addProperty(new CustomProgramProperty(contextProperty.getStarProgram()));
        this.addProperty(new StarProperty(temperature));
        this.addProperty(new RenderableMeshProperty(sphere));
        createCorona();
    }

    private void createCorona() {
        corona = new GameSceneComponent(this);
        corona.addProperty(new CustomRendererProperty(contextProperty.getCoronaRenderer()));
        corona.addProperty(new CoronaProperty(temperature, 2.0f));
    }

    private StarContextProperty getContextProperty() {
        GameScene scene = getContext().getScene();
        if (scene.hasEnabledProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME))
            return scene.getProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME);
        else return createContext();
    }

    private StarContextProperty createContext() {
        Texture1D temperature = getTemperatureTexture();
        StarProgram starProgram = new StarProgram(temperature);
        scheduleUpdater(starProgram);
        CoronaProgram coronaProgram = new CoronaProgram(temperature);
        scheduleUpdater(coronaProgram);
        CoronaRenderer coronaRenderer = new CoronaRenderer(getContext().getGraphics(), coronaProgram);
        StarContextProperty property = new StarContextProperty(starProgram, coronaProgram, coronaRenderer);
        getContext().getScene().addProperty(property);
        return property;
    }

    private void scheduleUpdater(Updatable updatable) {
        getContext().getGraphics().getThread().scheduleTask(new UpdaterTask(updatable));
    }

    private Texture1D getTemperatureTexture() {
        ImageData ringColorsData = ImageDecoder.decodePNG(Star.class.getResourceAsStream("star_temperature.png"), PNGDecoder.Format.RGBA);
        return new Texture1D(ringColorsData.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, false, ringColorsData.getData());
    }


}
