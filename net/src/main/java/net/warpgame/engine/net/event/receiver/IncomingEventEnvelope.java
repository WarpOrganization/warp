package net.warpgame.engine.net.event.receiver;

/**
 * @author Hubertus
 * Created 15.05.2018
 */
public class IncomingEventEnvelope extends IncomingEnvelope {

    IncomingEventEnvelope(Object deserializedEvent, int targetComponentId, int dependencyId, int eventType, long timestamp) {
        super(deserializedEvent, targetComponentId, dependencyId, eventType, timestamp);
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
