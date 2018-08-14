package net.warpgame.servertest;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import net.warpgame.engine.net.messagetypes.event.NetworkEvent;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class BulletCreatedEvent extends NetworkEvent {
    private int bulletComponentId;

    public BulletCreatedEvent(int bulletComponentId, int targetId) {
        super(targetId);
        this.bulletComponentId = bulletComponentId;
    }

    public int getBulletComponentId() {
        return bulletComponentId;
    }

    public void setBulletComponentId(int bulletComponentId) {
        this.bulletComponentId = bulletComponentId;
    }

    @Service
    public static class BulletCreatedEventSerialization extends Serialization<BulletCreatedEvent> {
        public BulletCreatedEventSerialization() {
            super(BulletCreatedEvent.class);
        }

        @Override
        public void serialize(BulletCreatedEvent object, Serializer serializer) {
            serializer
                    .write(object.bulletComponentId)
                    .write(object.getTargetPeerId());
        }

        @Override
        public BulletCreatedEvent deserialize(Deserializer deserializer) {
            return new BulletCreatedEvent(
                    deserializer.getInt(),
                    deserializer.getInt()
            );
        }

        @Override
        public int getType() {
            return 12;
        }
    }
}
