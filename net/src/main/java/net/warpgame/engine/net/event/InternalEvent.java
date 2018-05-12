package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 14.01.2018
 */
public class InternalEvent extends Event {
    private Message message;

    public InternalEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public enum Message {
        STATE_SYNC,
        STATE_LOADING,
        STATE_LIVE
    }

}

