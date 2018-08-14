package net.warpgame.servertest.client;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.graphics.window.WindowManager;
import net.warpgame.engine.input.event.KeyReleasedEvent;

import java.awt.event.KeyEvent;

/**
 * @author KocproZ
 * Created 2018-07-28 at 22:49
 */
public class TestKeyboardListener extends Listener<KeyReleasedEvent> {

    private WindowManager windowManager;

    public TestKeyboardListener(Component owner, WindowManager manager) {
        super(owner, Event.getTypeId(KeyReleasedEvent.class));
        this.windowManager = manager;
    }

    @Override
    public void handle(KeyReleasedEvent event) {
        if (event.getKey() == KeyEvent.VK_F1)
            if (windowManager.getCursorMode() == WindowManager.CURSOR_DISABLED)
                windowManager.setCursorNormal();
            else
                windowManager.setCursorDisabled();

    }

}
