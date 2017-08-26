package pl.warp.engine.common.input;

import pl.warp.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class MouseButtonPressedEvent extends Event {
    public static final String MOUSE_BUTTON_PRESSED_EVENT_NAME = "mouseButtonPressedEvent";

    private int button;

    public MouseButtonPressedEvent(int button) {
        super(MOUSE_BUTTON_PRESSED_EVENT_NAME);
        this.button = button;
    }

    public int getButton() {
        return button;
    }
}
