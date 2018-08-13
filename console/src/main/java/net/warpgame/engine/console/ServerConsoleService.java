package net.warpgame.engine.console;

import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ClientRegistry;

/**
 * @author KocproZ
 * Created 2018-07-15 at 16:31
 */
@Service
@Profile("server")
public class ServerConsoleService extends ConsoleService {

    private ClientRegistry clientRegistry;

    public ServerConsoleService(Context context, SceneHolder sceneHolder, ClientRegistry registry) {
        super(context, sceneHolder);
        this.clientRegistry = registry;
    }

    public void sendChatMessage(String sender, String msg) {
        print(String.format("[%s] %s", sender, msg));
        consoleComponent.triggerEvent(new ChatMessageEvent(sender, msg, Client.ALL));
    }

}
