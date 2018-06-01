package net.warpgame.content;

import net.warpgame.engine.core.event.Event;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 30.12.2017
 */
public class InputEvent extends Event implements Serializable {
    private int input;
    private boolean pressed;

    public InputEvent(int input, boolean pressed) {
        super("inputEvent");
        this.input = input;
        this.pressed = pressed;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
