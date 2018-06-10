package net.warpgame.engine.input.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class MouseButtonReleasedEvent extends Event {

    private int button;

    public MouseButtonReleasedEvent(int button) {
        this.button = button;
    }

    public int getButton() {
        return button;
    }
}
