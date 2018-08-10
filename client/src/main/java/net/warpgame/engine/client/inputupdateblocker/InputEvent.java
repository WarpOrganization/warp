package net.warpgame.engine.client.inputupdateblocker;

import net.warpgame.engine.net.messagetypes.event.ConfirmableNetworkEvent;

/**
 * @author Hubertus
 * Created 24.06.2018
 */
public class InputEvent extends ConfirmableNetworkEvent {

    private long triggerTime;

    public InputEvent(InputUpdateBlockerService inputUpdateBlockerService) {
        super(null);
        triggerTime = System.currentTimeMillis();
        super.setConfirmationConsumer((peer -> inputUpdateBlockerService.notifyInputEventConfirmed(this)));
    }

    long getTriggerTime() {
        return triggerTime;
    }
}
