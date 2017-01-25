package pl.warp.game;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class DroneProperty extends Property{

    private int hitPoints;
    private int team;
    private ArrayList<Component> targetList;

    public static final String DRONE_PROPERTY_NAME = "droneProperty";

    public DroneProperty(Component owner, int hitPoints, int team, ArrayList<Component> targetList) {
        super(owner, DRONE_PROPERTY_NAME);
        this.hitPoints = hitPoints;
        this.team = team;
        this.targetList = targetList;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getTeam() {
        return team;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public ArrayList<Component> getTargetList() {
        return targetList;
    }
}
