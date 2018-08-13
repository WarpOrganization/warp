package net.warpgame.engine.console;

import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;

/**
 * @author KocproZ
 * Created 2018-07-15 at 16:47
 */
@Service
@Profile("client")
public class ClientConsoleService extends ConsoleService {

    public ClientConsoleService(SceneHolder holder, Context context) {
        super(context, holder);
    }

    public void sendChatMessage(String sender, String msg) {
        consoleComponent.triggerEvent(new ChatMessageEvent(sender, msg));
    }
}
