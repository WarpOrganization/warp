package net.warpgame.engine.input.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class KeyPressedEvent extends Event {
    private int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
