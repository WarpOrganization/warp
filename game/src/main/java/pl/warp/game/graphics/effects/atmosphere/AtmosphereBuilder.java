package pl.warp.game.graphics.effects.atmosphere;

import org.joml.Vector3f;
import pl.warp.engine.graphics.CustomRendererProperty;
import pl.warp.game.GameContext;
import pl.warp.game.graphics.effects.GameComponentBuilder;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 11
 */
public class AtmosphereBuilder implements GameComponentBuilder {

    private GameComponent parent;
    private Vector3f color = new Vector3f(1.0f);
    private float radius = 1.15f;
    private float innerExp = 3.0f;
    private float innerMul = 0.35f;
    private float outerExp = 3.0f;
    private float outerMul = 1.0f;
    private GameContext context;

    public AtmosphereBuilder(GameComponent parent) {
        this.parent = parent;
        this.context = parent.getContext();
    }

    @Override
    public GameComponent build() {
        GameComponent atmosphere = new GameSceneComponent(parent);
        atmosphere.addProperty(new AtmosphereProperty(color, radius, innerExp, innerMul, outerExp, outerMul));
        AtmosphereContextProperty contextProperty = getContextProperty();
        atmosphere.addProperty(new CustomRendererProperty(contextProperty.getRenderer()));
        return atmosphere;
    }

    private AtmosphereContextProperty getContextProperty() {
        GameScene scene = context.getScene();
        if (scene.hasEnabledProperty(AtmosphereContextProperty.ATMOSPHERE_CONTEXT_PROPERTY_NAME))
            return scene.getProperty(AtmosphereContextProperty.ATMOSPHERE_CONTEXT_PROPERTY_NAME);
        else return createContext();
    }

    private AtmosphereContextProperty createContext() {
        AtmosphereProgram atmosphereProgram = new AtmosphereProgram();
        AtmosphereRenderer atmosphereRenderer = new AtmosphereRenderer(context.getGraphics(), atmosphereProgram);
        AtmosphereContextProperty property = new AtmosphereContextProperty(atmosphereRenderer, atmosphereProgram);
        context.getScene().addProperty(property);
        return property;
    }

    public AtmosphereBuilder setColor(Vector3f color) {
        this.color = color;
        return this;
    }

    public AtmosphereBuilder setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public AtmosphereBuilder setInnerExp(float innerExp) {
        this.innerExp = innerExp;
        return this;
    }

    public AtmosphereBuilder setInnerMul(float innerMul) {
        this.innerMul = innerMul;
        return this;
    }

    public AtmosphereBuilder setOuterExp(float outerExp) {
        this.outerExp = outerExp;
        return this;
    }

    public AtmosphereBuilder setOuterMul(float outerMul) {
        this.outerMul = outerMul;
        return this;
    }
}
