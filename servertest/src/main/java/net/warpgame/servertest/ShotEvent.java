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
public class ShotEvent extends NetworkEvent {

    private int bulletComponentId;

    public ShotEvent(int bulletComponentId) {
        this.bulletComponentId = bulletComponentId;
    }

    public ShotEvent(int bulletComponentId, int targetId) {
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
    public static class ShotEventSerialization extends Serialization<ShotEvent> {

        public ShotEventSerialization() {
            super(ShotEvent.class);
        }

        @Override
        public void serialize(ShotEvent object, Serializer serializer) {
            serializer
                    .write(object.bulletComponentId)
                    .write(object.getTargetPeerId());
        }

        @Override
        public ShotEvent deserialize(Deserializer deserializer) {
            return new ShotEvent(
                    deserializer.getInt(),
                    deserializer.getInt()
            );
        }

        @Override
        public int getType() {
            return 11;
        }
    }
}
