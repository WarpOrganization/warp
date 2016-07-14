package pl.warp.game;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;

/**
 * Created by hubertus on 7/12/16.
 */
public class BulletScript extends Script<Component> {

    private int life;

    public BulletScript(Component owner, int life) {
        super(owner);
        this.life = life;
    }

    @Override
    public void onInit() {
;
    }

    @Override
    public void onUpdate(int delta) {
        life -= delta;
        if (life < 0)
            getOwner().destroy();
    }
}
