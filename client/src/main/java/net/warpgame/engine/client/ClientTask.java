package net.warpgame.engine.client;

import io.netty.util.internal.SocketUtils;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@RegisterTask(thread = "client")
public class ClientTask extends EngineTask {

    private Config config;
    private ConnectionService connectionService;
    private ClientRemoteEventQueue eventQueue;

    private static int KEEP_ALIVE_INTERVAL = 1000 * 5;
    private InetSocketAddress address;


    public ClientTask(Config config, ConnectionService connectionService, ClientRemoteEventQueue eventQueue) {
        this.config = config;
        this.connectionService = connectionService;
        this.eventQueue = eventQueue;
    }

    @Override
    protected void onInit() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        address = SocketUtils.socketAddress(
                config.getValue("multiplayer.ip"),
                Integer.parseInt(System.getProperty("port", "" + config.getValue("multiplayer.port"))));
        connectionService.connect(address);
        eventQueue.setConnectionService(connectionService);
    }

    @Override
    protected void onClose() {
        connectionService.shutdown();
    }


    private int counter = KEEP_ALIVE_INTERVAL;

    @Override
    public void update(int delta) {
        counter -= delta;
        if (counter < 0) {
            counter = KEEP_ALIVE_INTERVAL;
            connectionService.sendKeepAlive();
        }
        eventQueue.update();
    }


}
