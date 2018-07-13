package net.warpgame.content;


import net.warpgame.engine.net.event.NetworkEvent;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 30.12.2017
 */
public class InputEvent extends NetworkEvent implements Serializable {
    private int input;
    private boolean pressed;
    public InputEvent(int input, boolean pressed) {
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
