package pl.warp.engine.core.scene.properties;

import pl.warp.engine.core.scene.Property;

import java.util.Objects;

public class RotationProperty implements Property {
    private double pitch;
    private double yaw;
    private double roll;

    public RotationProperty(double pitch, double yaw, double roll) {

        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }


    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public void addPitch(double value) {
        pitch += value;
    }

    public void addRoll(double value) {
        roll += value;
    }

    public void addYaw(double value) {
        yaw += value;
    }

    @Override
    public String getName() {
        return "RotationProperty";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotationProperty that = (RotationProperty) o;
        return Double.compare(that.pitch, pitch) == 0 &&
                Double.compare(that.yaw, yaw) == 0 &&
                Double.compare(that.roll, roll) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pitch, yaw, roll);
    }
}
