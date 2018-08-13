package net.warpgame.engine.client.message;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.client.ConnectionService;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.MessageEnvelope;
import net.warpgame.engine.net.message.MessageSender;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
@Service
@Profile("client")
public class ClientMessageSender extends MessageSender {

    private ConnectionService connectionService;

    public ClientMessageSender(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    public void send(MessageEnvelope addressedEnvelope) {
        ByteBuf packet = connectionService
                .getHeader(PacketType.PACKET_MESSAGE, addressedEnvelope.getSerializedMessage().length + 8);
        packet.writeInt(addressedEnvelope.getMessageType());
        packet.writeInt(addressedEnvelope.getDependencyId());
        packet.writeBytes(addressedEnvelope.getSerializedMessage());
        connectionService.sendPacket(packet);
    }
}
