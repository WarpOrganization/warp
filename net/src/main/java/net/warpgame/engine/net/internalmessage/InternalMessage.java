package net.warpgame.engine.net.internalmessage;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.serialization.SerializationBuffer;
import net.warpgame.engine.net.serialization.SerializationIO;
import net.warpgame.engine.net.serialization.Serializers;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class InternalMessage implements Serializable {
    private InternalMessageContent messageContent;
    private int targetPeerId;

    public InternalMessage(InternalMessageContent messageContent, int targetPeerId) {
        this.messageContent = messageContent;
        this.targetPeerId = targetPeerId;
    }

    private InternalMessage(InternalMessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public InternalMessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(InternalMessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public int getTargetPeerId() {
        return targetPeerId;
    }

    public void setTargetPeerId(int targetPeerId) {
        this.targetPeerId = targetPeerId;
    }

    @Service
    public static class InternalMessageSerializationIO extends SerializationIO<InternalMessage> {

        private InternalMessageContent[] internalMessageContentValues = InternalMessageContent.values();

        public InternalMessageSerializationIO() {
            super(InternalMessage.class);
        }

        @Override
        public void serialize(InternalMessage object, SerializationBuffer buffer, Serializers serializers) {
            buffer.write(object.messageContent.ordinal());
        }

        @Override
        public InternalMessage deserialize(SerializationBuffer buffer, Serializers serializers) {
            return new InternalMessage(internalMessageContentValues[buffer.readInt()]);
        }
    }
}
