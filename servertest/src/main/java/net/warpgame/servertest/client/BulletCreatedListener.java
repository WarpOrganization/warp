package net.warpgame.servertest.client;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.Property;
import net.warpgame.servertest.BulletCreatedEvent;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class BulletCreatedListener extends Listener<BulletCreatedEvent> {
    private final BulletCreator bulletCreator;

    protected BulletCreatedListener(Component owner, BulletCreator bulletCreator) {
        super(owner, Event.getTypeId(BulletCreatedEvent.class));
        this.bulletCreator = bulletCreator;
    }

    @Override
    public void handle(BulletCreatedEvent event) {
        if (!getOwner().hasEnabledProperty(Property.getTypeId(PlayerProperty.class)))
            bulletCreator.create(getOwner(), event.getBulletComponentId());
    }
}
