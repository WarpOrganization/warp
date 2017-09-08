package pl.warp.test;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-12 at 14
 */
public class BulletProperty extends Property<GameComponent> {
    public static final String BULLET_PROPERTY_NAME = "bulletProperty";

    private GameComponent playerShip;
    private int ttl;

    public BulletProperty(GameComponent playerShip, int ttl) {
        super(BULLET_PROPERTY_NAME);
        this.playerShip = playerShip;
        this.ttl = ttl;
    }

    public GameComponent getPlayerShip() {
        return playerShip;
    }

    public int getTtl() {
        return ttl;
    }
}
