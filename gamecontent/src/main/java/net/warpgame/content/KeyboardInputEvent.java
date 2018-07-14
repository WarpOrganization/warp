package net.warpgame.content;


import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import net.warpgame.engine.net.event.NetworkEvent;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 30.12.2017
 */
public class KeyboardInputEvent extends NetworkEvent implements Serializable {
    private int input;
    private boolean pressed;

    public KeyboardInputEvent(int input, boolean pressed) {
        this.input = input;
        this.pressed = pressed;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    @Service
    public static class KeyboardInputEventSerialization extends Serialization<KeyboardInputEvent> {

        public KeyboardInputEventSerialization() {
            super(KeyboardInputEvent.class);
        }

        @Override
        public void serialize(KeyboardInputEvent object, Serializer serializer) {
            serializer
                    .write(object.input)
                    .write(object.pressed);
        }

        @Override
        public KeyboardInputEvent deserialize(Deserializer deserializer) {
            return new KeyboardInputEvent(
                    deserializer.getInt(),
                    deserializer.getBoolean()
            );
        }

        @Override
        public int getType() {
            return 7;
        }
    }
}
