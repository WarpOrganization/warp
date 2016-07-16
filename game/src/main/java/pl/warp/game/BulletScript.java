package pl.warp.game;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.physics.event.CollisionEvent;

/**
 * Created by hubertus on 7/12/16.
 */
public class BulletScript extends Script<Component> {

    private int life;
    Listener<Component, CollisionEvent> collisionListener;

    public BulletScript(Component owner, int life) {
        super(owner);
        this.life = life;
    }

    @Override
    public void onInit() {
        collisionListener = SimpleListener.createListener(getOwner(),CollisionEvent.COLLISION_EVENT_NAME,this::onCollision);
    }

    @Override
    public void onUpdate(int delta) {
        life -= delta;
        if (life < 0)
            getOwner().destroy();
    }

    private void onCollision(CollisionEvent event){
        getOwner().destroy();
    }
}
