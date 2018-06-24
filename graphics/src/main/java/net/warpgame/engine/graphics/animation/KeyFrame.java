package net.warpgame.engine.graphics.animation;

/**
 * @author Jaca777
 * Created 2018-06-24 at 15
 */
public class KeyFrame {
    private float timeStamp;
    private JointTransform[] poses;

    public KeyFrame(float timeStamp, JointTransform[] poses) {
        this.timeStamp = timeStamp;
        this.poses = poses;
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public JointTransform[] getPoses() {
        return poses;
    }
}
