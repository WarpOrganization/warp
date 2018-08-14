package net.warpgame.servertest.server;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.server.Client;
import net.warpgame.servertest.BulletDeathEvent;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class BulletDeathCountdownScript extends Script {

    private static final int BULLET_LIFETIME = 4000;

    private int sumDelta;

    public BulletDeathCountdownScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate(int delta) {
        sumDelta += delta;
        if (sumDelta > BULLET_LIFETIME) {
            getOwner().triggerEvent(new BulletDeathEvent(Client.ALL));
            getOwner().destroy();
        }
    }
}
