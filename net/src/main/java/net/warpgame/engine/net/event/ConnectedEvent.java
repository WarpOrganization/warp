package net.warpgame.engine.net.event;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.serialization.SerializationBuffer;
import net.warpgame.engine.net.serialization.SerializationIO;
import net.warpgame.engine.net.serialization.Serializers;

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
    public static class ConnectedEventSerializationIO extends SerializationIO<ConnectedEvent>{

        public ConnectedEventSerializationIO() {
            super(ConnectedEvent.class);
        }

        @Override
        public void serialize(ConnectedEvent object, SerializationBuffer buffer, Serializers serializers) {

        }

        @Override
        public ConnectedEvent deserialize(SerializationBuffer buffer, Serializers serializers) {
            return new ConnectedEvent();
        }
    }
}
