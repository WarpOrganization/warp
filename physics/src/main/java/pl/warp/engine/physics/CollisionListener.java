package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btManifoldPoint;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    @Override
    public boolean onContactAdded(btManifoldPoint cp, int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
        //TODO: process collision
        return true;
    }
}