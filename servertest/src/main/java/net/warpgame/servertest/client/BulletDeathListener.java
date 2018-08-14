package net.warpgame.servertest.client;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.servertest.BulletDeathEvent;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class BulletDeathListener extends Listener<BulletDeathEvent> {
    protected BulletDeathListener(Component owner) {
        super(owner, Event.getTypeId(BulletDeathEvent.class));
    }

    @Override
    public void handle(BulletDeathEvent event) {
        getOwner().destroy();
    }
}
