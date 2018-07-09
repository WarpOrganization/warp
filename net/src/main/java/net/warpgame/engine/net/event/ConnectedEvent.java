package net.warpgame.engine.net.event;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.*;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class ConnectedEvent extends NetworkEvent {
    public ConnectedEvent(int targetId) {
        super(targetId);
    }
    private ConnectedEvent(){}

    @Service
    public static class ConnectedEventSerialization extends Serialization<ConnectedEvent> {

        public ConnectedEventSerialization() {
            super(ConnectedEvent.class);
        }

        @Override
        public void serialize(ConnectedEvent object, Serializer serializer) {

        }

        @Override
        public ConnectedEvent deserialize(Deserializer deserializer) {
            return new ConnectedEvent();
        }
    }
}
