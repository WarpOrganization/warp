package net.warpgame.engine.server.message;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.MessageEnvelope;
import net.warpgame.engine.net.message.MessageSender;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ConnectionUtil;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class ServerMessageSender extends MessageSender {
    private ConnectionUtil connectionUtil;

    public ServerMessageSender(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public void send(MessageEnvelope addressedEnvelope) {
        ByteBuf packet = connectionUtil
                .getHeader(PacketType.PACKET_MESSAGE, addressedEnvelope.getSerializedMessage().length + 8);
        packet.writeInt(addressedEnvelope.getMessageType());
        packet.writeInt(addressedEnvelope.getDependencyId());
        packet.writeBytes(addressedEnvelope.getSerializedMessage());
        connectionUtil.sendPacket(packet, (Client) addressedEnvelope.getTargetPeer());
    }
}
