package net.warpgame.engine.graphics.animation;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 * Created 2018-06-08 at 18
 */
public class Joint {
    private int index;
    private String name;
    private List<Joint> children = new ArrayList<>();

    private Matrix4f animationTransform;
    private Matrix4f localBindTransform;
    private Matrix4f inverseBindTransform;

    public Joint(int index, String name, Matrix4f localBindTransform) {
        this.index = index;
        this.name = name;
        this.localBindTransform = localBindTransform;
    }

    public void addChild(Joint joint) {
        this.children.add(joint);
    }

    protected void calculateInverseBindTransform(Matrix4f parentBindTransform) {
        Matrix4f bindTransform = parentBindTransform.mul(localBindTransform, new Matrix4f());
        bindTransform.invert(inverseBindTransform);
        for (Joint child : children) {
            child.calculateInverseBindTransform(bindTransform);
        }
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public List<Joint> getChildren() {
        return children;
    }

    public Matrix4f getAnimationTransform() {
        return animationTransform;
    }

    public Matrix4f getLocalBindTransform() {
        return localBindTransform;
    }

    public Matrix4f getInverseBindTransform() {
        return inverseBindTransform;
    }
}
