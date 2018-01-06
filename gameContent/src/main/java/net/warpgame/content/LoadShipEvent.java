package net.warpgame.content;

import net.warpgame.engine.core.event.Event;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class LoadShipEvent extends Event implements Serializable {
    private int shipComponentId;

    public LoadShipEvent(int shipComponentId) {
        super("loadShipEvent");
        this.shipComponentId = shipComponentId;
    }

    public int getShipComponentId() {
        return shipComponentId;
    }
}
