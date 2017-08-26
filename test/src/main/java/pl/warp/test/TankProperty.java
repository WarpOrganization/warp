package pl.warp.test;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 05.03.17
 */
public class TankProperty extends Property {
    public static final String TANK_PROPERTY_NAME = "tankProperty";

    private boolean team;
    private Component target;
    private TankProperty targetTankProperty;
    private boolean alive = true;
    private ArrayList<Component> targets = new ArrayList<>();
    private Component tankTurret;
    private Component tankBarrel;

    public TankProperty(boolean team, Component tankTurret, Component tankBarrel) {
        super(TANK_PROPERTY_NAME);
        this.team = team;
        this.tankTurret = tankTurret;
        this.tankBarrel = tankBarrel;
    }

    public boolean isTeam() {
        return team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public Component getTarget() {
        return target;
    }

    public void setTarget(Component target) {
        this.target = target;
    }

    public TankProperty getTargetTankProperty() {
        return targetTankProperty;
    }

    public void setTargetTankProperty(TankProperty targetTankProperty) {
        this.targetTankProperty = targetTankProperty;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public ArrayList<Component> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Component> targets) {
        this.targets = targets;
    }

    public Component getTankTurret() {
        return tankTurret;
    }

    public Component getTankBarrel() {
        return tankBarrel;
    }
}
