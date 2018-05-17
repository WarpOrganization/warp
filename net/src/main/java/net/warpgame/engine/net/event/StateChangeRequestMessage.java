package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.net.ConnectionState;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 14.05.2018
 */
public class StateChangeRequestMessage extends Event implements Serializable {

    private ConnectionState message;
    private int targetId;

    public StateChangeRequestMessage(ConnectionState message, int targetId) {
        this.message = message;
        this.targetId = targetId;
    }

    public StateChangeRequestMessage(ConnectionState message) {
        this.message = message;
        targetId = -1;
    }

    public ConnectionState getConnectionState() {
        return message;
    }

    public void setMessage(ConnectionState message) {
        this.message = message;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }
}

