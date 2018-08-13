package net.warpgame.engine.net.messagetypes.idpoolmessage;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public class IdPoolRequest extends IdPoolMessage {
    public IdPoolRequest() {
        super(0);
    }

    @Service
    public static class IdPoolRequestSerialization extends Serialization<IdPoolRequest> {
        public IdPoolRequestSerialization() {
            super(IdPoolRequest.class);
        }

        @Override
        public void serialize(IdPoolRequest object, Serializer serializer) {
        }

        @Override
        public IdPoolRequest deserialize(Deserializer deserializer) {
            return new IdPoolRequest();
        }

        @Override
        public int getType() {
            return 9;
        }
    }
}
