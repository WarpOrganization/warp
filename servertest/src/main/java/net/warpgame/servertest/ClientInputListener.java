package net.warpgame.servertest;

import net.warpgame.content.InputEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.server.RemoteInput;

import static java.awt.event.KeyEvent.*;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ClientInputListener extends Listener<InputEvent> {

    private RemoteInput input;

    protected ClientInputListener(Component owner) {
        super(owner, "inputEvent");
        RemoteInputProperty property = owner.getProperty(RemoteInputProperty.NAME);
        input = property.getRemoteInput();
    }

    @Override
    public void handle(InputEvent inputEvent) {
        switch (inputEvent.getInput()) {
            case VK_W:
                input.setForwardPressed(inputEvent.isPressed());
                break;
            case VK_S:
                input.setBackwardsPressed(inputEvent.isPressed());
                break;
            case VK_A:
                input.setLeftPressed(inputEvent.isPressed());
                break;
            case VK_D:
                input.setRightPressed(inputEvent.isPressed());
                break;
            case VK_UP:
                input.setRotationUp(inputEvent.isPressed());
                break;
            case VK_DOWN:
                input.setRotationDown(inputEvent.isPressed());
                break;
            case VK_LEFT:
                input.setRotationLeft(inputEvent.isPressed());
                break;
            case VK_RIGHT:
                input.setRotationRight(inputEvent.isPressed());
                break;
        }
    }
}
