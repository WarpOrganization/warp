package net.warpgame.engine.console;

import net.warpgame.engine.core.event.Event;

/**
 * @author KocproZ
 * Created 2018-06-12 at 09:23
 */
public class ConsoleInputEvent extends Event {

    private String input;

    public ConsoleInputEvent(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

}
