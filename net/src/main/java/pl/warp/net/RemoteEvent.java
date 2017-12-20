package pl.warp.net;

import pl.warp.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 17.12.2017
 */
public abstract class RemoteEvent extends Event {
    private int targetComponentId;

    public int getTargetComponentId() {
        return targetComponentId;
    }

    public void setTargetComponentId(int targetComponentId) {
        this.targetComponentId = targetComponentId;
    }
}
