package net.warpgame.engine.graphics.animation.dataloader;

import net.warpgame.engine.graphics.animation.Animation;
import net.warpgame.engine.graphics.animation.JointTransform;
import net.warpgame.engine.graphics.animation.KeyFrame;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.AnimationData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.JointTransformData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.KeyFrameData;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Map;

/**
 * @author Jaca777
 * Created 2018-06-24 at 17
 */
public class AnimationLoader {


    public static Animation loadAnimation(AnimationData animationData, Map<String, Integer> idMap) {
        KeyFrame[] frames = new KeyFrame[animationData.keyFrames.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = createKeyFrame(animationData.keyFrames[i], idMap);
        }
        return new Animation(animationData.lengthSeconds, frames);
    }

    private static KeyFrame createKeyFrame(KeyFrameData data, Map<String, Integer> idMap) {
        JointTransform[] transforms = new JointTransform[data.jointTransforms.size()];
        for (JointTransformData jointData : data.jointTransforms) {
            transforms[idMap.get(jointData.jointNameId)] = createTransform(jointData);
        }
        return new KeyFrame(data.time, transforms);
    }

    /**
     * Creates a joint transform from the data extracted from the collada file.
     *
     * @param data - the data from the collada file.
     * @return The joint transform.
     */
    private static JointTransform createTransform(JointTransformData data) {
        Matrix4f mat = data.jointLocalTransform;
        Vector3f translation = new Vector3f(mat.m30(), mat.m31(), mat.m32());
        Quaternionf rotation = new Quaternionf().setFromNormalized(mat);
        return new JointTransform(translation, rotation);
    }

}

