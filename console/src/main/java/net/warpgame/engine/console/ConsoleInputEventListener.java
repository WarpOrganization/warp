package net.warpgame.engine.console;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import org.apache.log4j.Logger;

/**
 * @author KocproZ
 * Created 2018-07-14 at 13:12
 */
public class ConsoleInputEventListener extends Listener<ConsoleInputEvent> {

    public ConsoleInputEventListener(Component owner) {
        super(owner, Event.getTypeId(ConsoleInputEvent.class));
    }

    @Override
    public void handle(ConsoleInputEvent event) {
        Logger.getLogger(ConsoleInputEventListener.class).info(event.getInput());
    }

}
