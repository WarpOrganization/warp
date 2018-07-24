package net.warpgame.engine.console;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;

/**
 * @author KocproZ
 * Created 2018-07-15 at 18:04
 */
public class ConsoleInputEventListener extends Listener<ConsoleInputEvent> {

    private ConsoleService consoleService;

    public ConsoleInputEventListener(Component owner, ConsoleService consoleService) {
        super(owner, Event.getTypeId(ConsoleInputEvent.class));
        this.consoleService = consoleService;
    }

    @Override
    public void handle(ConsoleInputEvent event) {
        if (event.getInput().startsWith("/"))
            consoleService.parseAndExecute(event.getInput());
        else
            consoleService.sendChatMessage("User", event.getInput()); //TODO: Change to filter commands
    }

}
