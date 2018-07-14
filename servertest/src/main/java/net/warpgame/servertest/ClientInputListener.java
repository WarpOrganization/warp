package net.warpgame.servertest;

import net.warpgame.content.KeyboardInputEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.server.RemoteInput;

import static java.awt.event.KeyEvent.*;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ClientInputListener extends Listener<KeyboardInputEvent> {

    private RemoteInput input;

    protected ClientInputListener(Component owner) {
        super(owner, Event.getTypeId(KeyboardInputEvent.class));
        RemoteInputProperty property = owner.getProperty(Property.getTypeId(RemoteInputProperty.class));
        input = property.getRemoteInput();
    }

    @Override
    public void handle(KeyboardInputEvent keyboardInputEvent) {
        switch (keyboardInputEvent.getInput()) {
            case VK_W:
                input.setForwardPressed(keyboardInputEvent.isPressed());
                break;
            case VK_S:
                input.setBackwardsPressed(keyboardInputEvent.isPressed());
                break;
            case VK_A:
                input.setLeftPressed(keyboardInputEvent.isPressed());
                break;
            case VK_D:
                input.setRightPressed(keyboardInputEvent.isPressed());
                break;
            case VK_UP:
                input.setRotationUp(keyboardInputEvent.isPressed());
                break;
            case VK_DOWN:
                input.setRotationDown(keyboardInputEvent.isPressed());
                break;
            case VK_LEFT:
                input.setRotationLeft(keyboardInputEvent.isPressed());
                break;
            case VK_RIGHT:
                input.setRotationRight(keyboardInputEvent.isPressed());
                break;
        }
    }
}
