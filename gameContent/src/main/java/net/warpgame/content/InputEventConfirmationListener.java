package net.warpgame.content;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;

/**
 * @author Hubertus
 * Created 10.01.2018
 */
public class InputEventConfirmationListener extends Listener{

    protected InputEventConfirmationListener(Component owner) {
        super(owner, "eventConfirmationEvent");
    }

    @Override
    public void handle(Event event) {

    }
}
