package net.warpgame.content;

import net.warpgame.engine.core.event.Event;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 30.12.2017
 */
public class InputEvent extends Event implements Serializable{
    private int input;

    public InputEvent(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }
}
