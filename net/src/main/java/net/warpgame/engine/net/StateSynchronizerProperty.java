package net.warpgame.engine.net;

import net.warpgame.engine.core.property.Property;

/**
 * @author Hubertus
 * Created 15.08.2018
 */
public class StateSynchronizerProperty extends Property {
    public StateSynchronizerProperty(SerializationType serializationType) {
        this.serializationType = serializationType;
    }

    private SerializationType serializationType;

    public SerializationType getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(SerializationType serializationType) {
        this.serializationType = serializationType;
    }
}
