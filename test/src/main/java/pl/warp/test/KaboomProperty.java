package pl.warp.test;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class KaboomProperty extends Property {
    public static final String KABOOM_PROPERTY_NAME = "kaboomProperty";

    private GameComponent planet;
    private float planetRadius;

    public KaboomProperty(GameComponent planet, float planetRadius) {
        super(KABOOM_PROPERTY_NAME);
        this.planet = planet;
        this.planetRadius = planetRadius;
    }

    public GameComponent getPlanet() {
        return planet;
    }

    public KaboomProperty setPlanet(GameComponent planet) {
        this.planet = planet;
        return this;
    }

    public float getPlanetRadius() {
        return planetRadius;
    }

    public KaboomProperty setPlanetRadius(float planetRadius) {
        this.planetRadius = planetRadius;
        return this;
    }
}
