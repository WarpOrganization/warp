package net.warpgame.content;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import net.warpgame.engine.net.event.NetworkEvent;
import org.joml.Vector3f;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class LoadShipEvent extends NetworkEvent implements Serializable {
    private int shipComponentId;
    private Vector3f pos;

    public LoadShipEvent(int shipComponentId, Vector3f pos, int clientId) {
        super(clientId);
        this.shipComponentId = shipComponentId;
        this.pos = pos;
    }

    public int getShipComponentId() {
        return shipComponentId;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    @Service
    public static class LoadShipEventSerialization extends Serialization<LoadShipEvent>{

        public LoadShipEventSerialization() {
            super(LoadShipEvent.class);
        }

        @Override
        public void serialize(LoadShipEvent object, Serializer serializer) {
            serializer
                    .write(object.shipComponentId)
                    //TODO fix when serialization ids work
                    .write(object.pos, 6); //in the future .write(object.pos, Vector3f.class);
        }

        @Override
        public LoadShipEvent deserialize(Deserializer deserializer) {
            return new LoadShipEvent(
                    deserializer.getInt(),
                    (Vector3f) deserializer.getObject(),
                    0);
        }

        @Override
        public int getType() {
            return 5;
        }
    }
}
