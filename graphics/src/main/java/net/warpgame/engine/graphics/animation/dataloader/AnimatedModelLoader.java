package net.warpgame.engine.graphics.animation.dataloader;

import net.warpgame.engine.graphics.animation.AnimatedMesh;
import net.warpgame.engine.graphics.animation.AnimatedModel;
import net.warpgame.engine.graphics.animation.Joint;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.AnimatedModelData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.JointData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.MeshData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.SkeletonData;

/**
 * @author Jaca777
 * Created 2018-06-24 at 17
 */
public class AnimatedModelLoader {


    public static AnimatedModel loadData(AnimatedModelData animatedModelData) {
        MeshData meshData = animatedModelData.getMeshData();
        AnimatedMesh animatedMesh = new AnimatedMesh(
                meshData.getVertices(),
                meshData.getTextureCoords(),
                meshData.getNormals(),
                meshData.getJointIds(),
                meshData.getVertexWeights(),
                meshData.getIndices());
        SkeletonData skeletonData = animatedModelData.getJointsData();
        Joint headJoint = createJoints(skeletonData.headJoint);
        return new AnimatedModel(animatedMesh, headJoint, skeletonData.jointCount);
    }



    private static Joint createJoints(JointData data) {
        Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
        for (JointData child : data.children) {
            joint.addChild(createJoints(child));
        }
        return joint;
    }
}
