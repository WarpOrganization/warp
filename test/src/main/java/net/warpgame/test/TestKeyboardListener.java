package net.warpgame.test;

import net.warpgame.content.KeyboardInputEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.graphics.window.WindowManager;

import java.awt.event.KeyEvent;

/**
 * @author KocproZ
 * Created 2018-07-28 at 22:49
 */
public class TestKeyboardListener extends Listener<KeyboardInputEvent> {

    private WindowManager windowManager;

    public TestKeyboardListener(Component owner, WindowManager manager) {
        super(owner, Event.getTypeId(KeyboardInputEvent.class));
        this.windowManager = manager;
    }

    @Override
    public void handle(KeyboardInputEvent event) {
        if (event.getInput() == KeyEvent.VK_F1 && event.isPressed())
            if (windowManager.getCursorMode() == WindowManager.CURSOR_DISABLED)
                windowManager.setCursorNormal();
            else
                windowManager.setCursorDisabled();

    }

}
