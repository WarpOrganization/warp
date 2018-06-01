package net.warpgame.engine.net.event.receiver;

/**
 * @author Hubertus
 * Created 15.05.2018
 */
public class IncomingInternalMessageEnvelope extends IncomingEnvelope {


    IncomingInternalMessageEnvelope(Object deserializedEvent, int dependencyId, long timestamp) {
        super(deserializedEvent, -1, dependencyId, -1, timestamp);
    }

    @Override
    boolean isInternal() {
        return true;
    }
}
