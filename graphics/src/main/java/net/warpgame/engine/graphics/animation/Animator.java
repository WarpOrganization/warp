package net.warpgame.engine.graphics.animation;

import net.warpgame.engine.core.math.Matrices;
import org.joml.Matrix4f;

/**
 * @author Jaca777
 * Created 2018-06-09 at 15
 */
public class Animator {
    private AnimatedModel animatedModel;

    private Animation currentAnimation;
    private float animationTime = 0;

    public Animator(AnimatedModel animatedModel) {
        this.animatedModel = animatedModel;
    }

    public void startAnimation(Animation animation) {
        this.animationTime = 0;
        this.currentAnimation = animation;
    }

    public void update(int delta) {
        if(currentAnimation != null) {
           increaseTime(delta);
           KeyFrame[] previousAndNextFrames = getPreviousAndNextFrames();
           calculateJointTransform(
                   animatedModel.getRootJoint(),
                   previousAndNextFrames[0],
                   previousAndNextFrames[1],
                   Matrices.IDENTITY_MATRIX_4F
           );
        }
    }



    private void increaseTime(int delta) {
        animationTime += (delta / 1000.0f);
        if(animationTime > currentAnimation.getLength()) {
            animationTime %= currentAnimation.getLength();
        }
    }

    private KeyFrame[] getPreviousAndNextFrames() {
        KeyFrame[] allFrames = currentAnimation.getFrames();
        KeyFrame previousFrame = allFrames[0];
        KeyFrame nextFrame = allFrames[0];
        for (int i = 1; i < allFrames.length; i++) {
            nextFrame = allFrames[i];
            if (nextFrame.getTimeStamp() > animationTime) {
                break;
            }
            previousFrame = allFrames[i];
        }
        return new KeyFrame[] { previousFrame, nextFrame };
    }

    private JointTransform destTransform = new JointTransform();
    private void calculateJointTransform(
            Joint joint,
            KeyFrame prevFrame,
            KeyFrame nextFrame,
            Matrix4f parentTransform
    ) {
        Matrix4f localTransform = getLocalTransform(joint, prevFrame, nextFrame);
        Matrix4f currentTransform = parentTransform.mul(localTransform, joint.getAnimationTransform());
        for(Joint childJoint : joint.getChildren()) {
            calculateJointTransform(childJoint, prevFrame, nextFrame, currentTransform);
        }
        currentTransform.mul(joint.getInverseBindTransform());
    }

    private Matrix4f getLocalTransform(Joint joint, KeyFrame prevFrame, KeyFrame nextFrame) {
        float t = getInterpolationAlpha(prevFrame, nextFrame);
        JointTransform prevTransform = prevFrame.getPoses()[joint.getIndex()];
        JointTransform nextTransform = nextFrame.getPoses()[joint.getIndex()];
        destTransform.setInterpolate(prevTransform, nextTransform, t);
        return destTransform.getLocalTransform();
    }

    private float getInterpolationAlpha(KeyFrame prevFrame, KeyFrame nextFrame) {
        float totalInterval = nextFrame.getTimeStamp() - prevFrame.getTimeStamp();
        float currentTime = animationTime - prevFrame.getTimeStamp();
        return currentTime / totalInterval;
    }
}
