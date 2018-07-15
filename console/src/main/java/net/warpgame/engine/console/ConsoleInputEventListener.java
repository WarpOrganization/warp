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

    private ConsoleService consoleService;

    public ConsoleInputEventListener(Component owner, ConsoleService consoleService) {
        super(owner, Event.getTypeId(ConsoleInputEvent.class));
        this.consoleService = consoleService;
    }

    @Override
    public void handle(ConsoleInputEvent event) {
        Logger.getLogger(ConsoleInputEventListener.class)
                .debug("ConsoleInputEvent from " + event.getSourceClientId() + ": " + event.getInput());
        if (!event.getInput().startsWith("/"))
            consoleService.sendChatMessage(Integer.toString(event.getSourceClientId()), event.getInput());
        else
            consoleService.parseAndExecute(event.getInput());
    }

}
