package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.receiver.EventReceiver;


/**
 * @author Hubertus
 * Created 27.11.2017
 */
public class ServerConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private int clientId;
    private SerializedSceneHolder sceneHolder;
    private EventReceiver eventReceiver;
    private ConnectionService connectionService;
    private ClientRemoteEventQueue eventQueue;

    public ServerConnectionHandler(SerializedSceneHolder sceneHolder,
                                   ConnectionService connectionService,
                                   ClientRemoteEventQueue eventQueue,
                                   EventReceiver eventReceiver) {
        this.sceneHolder = sceneHolder;
        this.connectionService = connectionService;
        this.eventQueue = eventQueue;
        this.eventReceiver = eventReceiver;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        int packetType = buffer.readInt();
        long timestamp = buffer.readLong();
//        if (packetType != PacketType.PACKET_SCENE_STATE)
//            System.out.println("server sends message type: " + packetType);

        switch (packetType) {
            case PacketType.PACKET_CONNECTED:
                connected(buffer.readInt());
                break;
            case PacketType.PACKET_CONNECTION_REFUSED:
                System.out.println("Connection refused!");
                break;

            case PacketType.PACKET_SCENE_STATE:
                sceneHolder.offerScene(timestamp, buffer);
                break;
            case PacketType.PACKET_EVENT:
                int eventType = buffer.readInt();
                int dependencyId = buffer.readInt();
                int targetComponentId = buffer.readInt();
                eventReceiver.addEvent(buffer, targetComponentId, eventType, dependencyId, timestamp);
                connectionService.confirmEvent(dependencyId);
                break;

            case PacketType.PACKET_EVENT_CONFIRMATION:
                int id = buffer.readInt();
                eventQueue.confirmEvent(id);
                break;
            case PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE:
//                System.out.println(System.currentTimeMillis());
//                System.out.println(timestamp);
                handleClockSynchronizationResponse(timestamp, buffer.readInt());
                break;
        }
    }

    private void connected(int clientId) {
        connectionService.setClientCredentials(clientId, 0);
        connectionService.setConnectionState(ConnectionState.SYNCHRONIZING);
        //TODO: send state change packet
    }

    private void handleClockSynchronizationResponse(long timestamp, int requestId) {
        connectionService.getClockSynchronizer().synchronize(timestamp, requestId);
        if (connectionService.getClockSynchronizer().getFinishedSynchronizations() >= 3) {
            connectionService.setConnectionState(ConnectionState.LIVE);
            //TODO: send state change event
            //TODO implement state logic
        }
    }
}
