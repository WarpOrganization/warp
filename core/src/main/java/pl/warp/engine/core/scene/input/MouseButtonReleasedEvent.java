package pl.warp.engine.core.scene.input;

import pl.warp.engine.core.scene.Event;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class MouseButtonReleasedEvent extends Event {
    public static final String MOUSE_BUTTON_RELEASED_EVENT_NAME = "mouseButtonReleasedEvent";

    private int button;

    public MouseButtonReleasedEvent(int button) {
        super(MOUSE_BUTTON_RELEASED_EVENT_NAME);
        this.button = button;
    }

    public int getButton() {
        return button;
    }
}
