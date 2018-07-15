package net.warpgame.engine.console;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import net.warpgame.engine.net.event.NetworkEvent;

/**
 * @author KocproZ
 * Created 2018-06-12 at 09:23
 */
public class ConsoleInputEvent extends NetworkEvent {

    private String input;

    public ConsoleInputEvent(String input) {
        this.input = input;
    }

    public ConsoleInputEvent(int targetId, String input) {
        super(targetId);
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    @Service
    public static class ConsoleInputSerializer extends Serialization<ConsoleInputEvent> {

        public ConsoleInputSerializer() {
            super(ConsoleInputEvent.class);
        }

        @Override
        public void serialize(ConsoleInputEvent object, Serializer serializer) {
            serializer.write(object.getInput());
        }

        @Override
        public ConsoleInputEvent deserialize(Deserializer deserializer) {
            return new ConsoleInputEvent(deserializer.getString());
        }

        @Override
        public int getType() {
            return 8;
        }
    }

}
