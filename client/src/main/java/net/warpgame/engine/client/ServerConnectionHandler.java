package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.receiver.EventReceiver;


/**
 * @author Hubertus
 * Created 27.11.2017
 */
public class ServerConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private boolean connected = false;
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
//        System.out.println("server sends message type: " + packetType);

        switch (packetType) {
            case PacketType.PACKET_CONNECTED:
                connected = true;
                clientId = buffer.readInt();
                connectionService.setClientCredentials(clientId, 0);
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
        }
    }
}
