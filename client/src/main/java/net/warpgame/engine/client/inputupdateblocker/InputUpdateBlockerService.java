package net.warpgame.engine.client.inputupdateblocker;

import net.warpgame.engine.client.UpdateBlockerService;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SimpleListener;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 24.06.2018
 */
@Service
@Profile("client")
public class InputUpdateBlockerService {
    private UpdateBlockerService updateBlockerService;
    private Component playerComponent;
    private long lastInputEventTriggerTime;
    private Set<Listener<?>> activeListeners = new HashSet<>();
    private Set<Integer> inputEventIds = new HashSet<>();

    public InputUpdateBlockerService(UpdateBlockerService updateBlockerService) {
        this.updateBlockerService = updateBlockerService;

        lastInputEventTriggerTime = 0;
        //TODO load input event ids
    }

    public void setPlayerComponent(Component newPlayerComponent) {
        if(playerComponent !=null){
            updateBlockerService.unblock(playerComponent.getId());
            lastInputEventTriggerTime = 0;
            for (Listener<?> listener : activeListeners) playerComponent.removeListener(listener);
            activeListeners.clear();
        }

        playerComponent = newPlayerComponent;
        for (int eventId : inputEventIds) createInputEventListener(eventId);
    }

    private void createInputEventListener(int eventId) {
        Listener<?> listener = SimpleListener.createListener(
                playerComponent,
                eventId,
                this::notifyInputEventTriggered);

        activeListeners.add(listener);
        playerComponent.addListener(listener);
    }

    private void notifyInputEventTriggered(Event e) {
        if (e instanceof InputEvent) {
            lastInputEventTriggerTime = ((InputEvent) e).getTriggerTime();
            updateBlockerService.block(playerComponent.getId());
        }
    }

    void notifyInputEventConfirmed(Event e) {
        if (e instanceof InputEvent) {
            if (((InputEvent) e).getTriggerTime() >= lastInputEventTriggerTime)
                updateBlockerService.unblock(playerComponent.getId());
        }
    }

}
