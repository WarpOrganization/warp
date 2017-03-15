package pl.warp.game.graphics.effects.atmosphere;

import org.joml.Vector3f;
import pl.warp.engine.graphics.CustomRendererProperty;
import pl.warp.game.graphics.effects.star.StarContextProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 11
 */
public class Atmosphere extends GameSceneComponent {
    private Vector3f color;
    private float radius;

    public Atmosphere(GameComponent parent, Vector3f color, float radius) {
        super(parent);
        this.color = color;
        this.radius = radius;
        init();
    }

    private void init() {
        addProperty(new AtmosphereProperty(color, radius));
        AtmosphereContextProperty contextProperty = getContextProperty();
        addProperty(new CustomRendererProperty(contextProperty.getRenderer()));
    }

    private AtmosphereContextProperty getContextProperty() {
        GameScene scene = getContext().getScene();
        if (scene.hasEnabledProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME))
            return scene.getProperty(StarContextProperty.STAR_CONTEXT_PROPERTY_NAME);
        else return createContext();
    }

    private AtmosphereContextProperty createContext() {
        AtmosphereProgram atmosphereProgram = new AtmosphereProgram();
        AtmosphereRenderer atmosphereRenderer = new AtmosphereRenderer(getContext().getGraphics(), atmosphereProgram);
        AtmosphereContextProperty property = new AtmosphereContextProperty(atmosphereRenderer, atmosphereProgram);
        getContext().getScene().addProperty(property);
        return property;
    }

}
