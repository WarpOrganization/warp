package net.warpgame.engine.input.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class KeyReleasedEvent extends Event{
    public static final String KEY_RELEASED_EVENT_NAME = "keyReleasedEvent";
    private int key;

    public KeyReleasedEvent(int key) {
        super(KEY_RELEASED_EVENT_NAME);
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
