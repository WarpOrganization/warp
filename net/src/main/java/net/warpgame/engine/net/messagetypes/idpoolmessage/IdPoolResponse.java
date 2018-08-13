package net.warpgame.engine.net.messagetypes.idpoolmessage;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public class IdPoolResponse extends IdPoolMessage {
    private int poolOffset;

    public IdPoolResponse(int poolOffset, int targetPeer) {
        super(targetPeer);
        this.poolOffset = poolOffset;
    }

    public int getPoolOffset() {
        return poolOffset;
    }

    public void setPoolOffset(int poolOffset) {
        this.poolOffset = poolOffset;
    }

    @Service
    public static class IdPoolResponseSerialization extends Serialization<IdPoolResponse> {

        public IdPoolResponseSerialization() {
            super(IdPoolResponse.class);
        }

        @Override
        public void serialize(IdPoolResponse object, Serializer serializer) {
            serializer
                    .write(object.poolOffset)
                    .write(object.getTargetPeerId());
        }

        @Override
        public IdPoolResponse deserialize(Deserializer deserializer) {
            return new IdPoolResponse(deserializer.getInt(), deserializer.getInt());
        }

        @Override
        public int getType() {
            return 10;
        }
    }
}
