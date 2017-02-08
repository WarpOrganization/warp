package pl.warp.engine.ai.loader;

import pl.warp.engine.ai.behaviortree.Node;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class NodeBuilder {
    private String path;
    private ArrayList<NodeBuilder> children = new ArrayList<>();

    public Node build() throws Exception {
        Class<?> clazz = null;
        clazz = Class.forName(path);
        Constructor<?> ctor = clazz.getConstructor();
        Node node = (Node) ctor.newInstance();
        children.forEach(child -> {
            try {
                node.addChild(child.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
