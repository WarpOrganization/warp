package net.warpgame.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class SelectorNode extends CompositeNode {

    @Override
    int tick(Ticker ticker, int delta) {
        for (Node child : children) {
            int status = ticker.tickNode(child);

            if (status == SUCCESS) {
                return SUCCESS;
            }
            if (status == RUNNING) {
                return RUNNING;
            }
        }
        return FAILURE;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {

    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
