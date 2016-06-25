package pl.warp.engine.core.scene.properties;

public class RotationProperty {
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
}
