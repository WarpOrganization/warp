package net.warpgame.engine.physics;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class Collision {
    public enum ActivationState {
        DISABLE_DEACTIVATION(com.badlogic.gdx.physics.bullet.collision.Collision.DISABLE_DEACTIVATION),
        WANTS_DEACTIVATION(com.badlogic.gdx.physics.bullet.collision.Collision.WANTS_DEACTIVATION);

        public int val;

        ActivationState(int val) {
            this.val = val;
        }
    }
}
