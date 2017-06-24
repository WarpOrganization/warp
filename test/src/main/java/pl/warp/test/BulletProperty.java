package pl.warp.test;

import pl.warp.engine.core.scene.Property;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-02-12 at 14
 */
public class BulletProperty extends Property<GameComponent> {
    public static final String BULLET_PROPERTY_NAME = "bulletProperty";

    public BulletProperty() {
        super(BULLET_PROPERTY_NAME);
    }
}
