package net.warpgame.engine.console;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;

/**
 * @author KocproZ
 * Created 2018-07-14 at 13:12
 */
public class ChatMessageEventListener extends Listener<ChatMessageEvent> {

    private ConsoleService consoleService;
    private Context context;

    public ChatMessageEventListener(Component owner, Context context, ConsoleService consoleService) {
        super(owner, Event.getTypeId(ChatMessageEvent.class));
        this.context = context;
        this.consoleService = consoleService;
    }

    @Override
    public void handle(ChatMessageEvent event) {
        if (context.isProfileEnabled("server") && event.getSourcePeerId() != 0)
            consoleService.sendChatMessage(event.getSender(), event.getMessage());
        else if (context.isProfileEnabled("client") && event.getTargetPeerId() != 0)
            consoleService.print(String.format("[%s] %s", event.getSender(), event.getMessage()));
    }

}
