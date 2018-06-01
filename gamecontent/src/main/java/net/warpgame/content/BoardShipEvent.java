package net.warpgame.content;

import net.warpgame.engine.net.event.NetworkEvent;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class BoardShipEvent extends NetworkEvent implements Serializable {
    private int shipComponentId;

    public BoardShipEvent(int shipComponentId, int clientId) {
        super(clientId);
        this.shipComponentId = shipComponentId;
    }

    public int getShipComponentId() {
        return shipComponentId;
    }
}
