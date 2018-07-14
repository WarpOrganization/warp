package net.warpgame.engine.net.internalmessage;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;

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
    public static class InternalMessageSerialization extends Serialization<InternalMessage> {

        private InternalMessageContent[] internalMessageContentValues = InternalMessageContent.values();

        public InternalMessageSerialization() {
            super(InternalMessage.class);
        }

        @Override
        public void serialize(InternalMessage object, Serializer serializer) {
            serializer.write(object.messageContent.ordinal());
        }

        @Override
        public InternalMessage deserialize(Deserializer deserializer) {
            return new InternalMessage(internalMessageContentValues[deserializer.getInt()]);
        }

        @Override
        public int getType() {
            return 2;
        }
    }
}
