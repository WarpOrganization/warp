package net.warpgame.content;

import net.warpgame.engine.core.event.Event;
import org.joml.Vector3f;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class LoadShipEvent extends Event implements Serializable {
    private int shipComponentId;
    private Vector3f pos;

    public LoadShipEvent(int shipComponentId, Vector3f pos) {
        super("loadShipEvent");
        this.shipComponentId = shipComponentId;
        this.pos = pos;
    }

    public int getShipComponentId() {
        return shipComponentId;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
}
