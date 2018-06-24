package net.warpgame.engine.graphics.animation;

/**
 * @author Jaca777
 * Created 2018-06-24 at 15
 */
public class Animation {
    private float length; //in seconds
    private KeyFrame[] frames;

    public Animation(float length, KeyFrame[] frames) {
        this.length = length;
        this.frames = frames;
    }

    public float getLength() {
        return length;
    }

    public KeyFrame[] getFrames() {
        return frames;
    }
}
