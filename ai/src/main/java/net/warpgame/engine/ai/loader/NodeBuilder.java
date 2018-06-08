package net.warpgame.engine.ai.loader;

import net.warpgame.engine.ai.behaviortree.Node;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class NodeBuilder {
    private String path;
    private ArrayList<NodeBuilder> children = new ArrayList<>();

    public Node build() throws BehaviourTreeBuildException {
        Node node;
        try {
            Class<?> clazz;
            clazz = Class.forName(path);
            Constructor<?> ctor = clazz.getConstructor();
            node = (Node) ctor.newInstance();
        }catch (Exception e){
            throw new BehaviourTreeBuildException(e);
        }
        children.forEach(child -> node.addChild(child.build()));
        return node;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<NodeBuilder> getChildren() {
        return children;
    }
}
