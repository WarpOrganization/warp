package pl.warp.engine.ai.behaviourTree;

import java.util.HashMap;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class Ticker {

    private BehaviourTree tree;
    private HashMap<String, Object> data;
    private int currentTick = Integer.MIN_VALUE;

    public Ticker(BehaviourTree behaviourTree, HashMap<String, Object> data) {

        this.tree = behaviourTree;
        this.data = data;
    }

    public int tickNode(Node node) {
        if (!node.isOpen(currentTick)) {
            openNode(node);
        }
        return enterNode(node);
    }

    private void openNode(Node node) {
        node.onOpen(this);
    }

    private int enterNode(Node node) {
        node.onEnter(this);
        int status = node.tick(this);
        if (status != Node.RUNNING) {
            node.close();
        }
        return status;
    }

    void initializeTick() {
        currentTick++;
    }

    public Object getData(String key){
        return data.get(key);
    }

    public void setData(Object value, String key){
        data.put(key, value);
    }
}
