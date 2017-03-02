package pl.warp.test;

import org.joml.Vector3f;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;

/**
 * @author Hubertus
 *         Created 02.03.17
 */
public class TankControlScript extends GameScript{

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);


    public TankControlScript(GameComponent owner) {
        super(owner);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {

    }
}
