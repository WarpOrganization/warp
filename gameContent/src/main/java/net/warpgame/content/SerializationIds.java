package net.warpgame.content;

import net.warpgame.engine.net.event.ConnectedEvent;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 11.07.2018
 */
//TODO remove when id generation works
public class SerializationIds {

    public static int CONNECTED_EVENT_ID = 1;
    public static int INTERNAL_MESSAGE_ID = 2;
    public static int INPUT_EVENT_ID = 3;
    public static int BOARD_SHIP_EVENT_ID = 4;
    public static int LOAD_SHIP_EVENT_ID = 5;
    public static int VECTOR3_ID = 6;
    public static int KEYBOARD_INPUT_EVENT = 7;

    public static int getId(Class clazz) {
        if (clazz.equals(ConnectedEvent.class)) return CONNECTED_EVENT_ID;
        else if (clazz.equals(InternalMessage.class)) return INTERNAL_MESSAGE_ID;
        else if(clazz.equals(KeyboardInputEvent.class)) return INPUT_EVENT_ID;
        else if (clazz.equals(BoardShipEvent.class)) return BOARD_SHIP_EVENT_ID;
        else if (clazz.equals(LoadShipEvent.class)) return LOAD_SHIP_EVENT_ID;
        else if(clazz.equals(Vector3f.class)) return VECTOR3_ID;
        else if (clazz.equals(KeyboardInputEvent.class)) return KEYBOARD_INPUT_EVENT;
        else throw new RuntimeException("unknown object "+ clazz.toString());
    }
}
