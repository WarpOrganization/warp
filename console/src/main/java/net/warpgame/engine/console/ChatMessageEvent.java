package net.warpgame.engine.console;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import net.warpgame.engine.net.messagetypes.event.NetworkEvent;

/**
 * @author KocproZ
 * Created 2018-07-15 at 17:17
 */
public class ChatMessageEvent extends NetworkEvent {

    private String sender;
    private String message;

    public ChatMessageEvent(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public ChatMessageEvent(String sender, String message, int targetId) {
        super(targetId);
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Service
    @Profile("console")
    public static class ChatMessageEventSerializer extends Serialization<ChatMessageEvent> {

        public ChatMessageEventSerializer() {
            super(ChatMessageEvent.class);
        }

        @Override
        public void serialize(ChatMessageEvent object, Serializer serializer) {
            serializer.write(object.sender).write(object.message);
        }

        @Override
        public ChatMessageEvent deserialize(Deserializer deserializer) {
            return new ChatMessageEvent(deserializer.getString(), deserializer.getString());
        }

        @Override
        public int getType() {
            return 8;
        }
    }

}
