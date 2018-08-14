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
public class BulletDeathEvent extends NetworkEvent {
    public BulletDeathEvent(int targetId) {
        super(targetId);
    }

    @Service
    public static class BulletDeathEventSerialization extends Serialization<BulletDeathEvent> {

        public BulletDeathEventSerialization() {
            super(BulletDeathEvent.class);
        }

        @Override
        public void serialize(BulletDeathEvent object, Serializer serializer) {
            serializer.write(object.getTargetPeerId());
        }

        @Override
        public BulletDeathEvent deserialize(Deserializer deserializer) {
            return new BulletDeathEvent(deserializer.getInt());
        }

        @Override
        public int getType() {
            return 13;
        }
    }
}
