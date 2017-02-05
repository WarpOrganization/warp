package pl.warp.engine.ai.behaviortree;

import java.util.Collections;

/**
 * @author Hubertus
 *         Created 05.02.17
 */
public class RandomSelectorNode extends CompositeNode {
    @Override
    int tick(Ticker ticker, int delta) {
        Collections.shuffle(children);
        for (Node child : children) {
            int status = ticker.tickNode(child);
            if (status != Node.FAILURE) return status;
        }
        return Node.FAILURE;
    }

    @Override
    protected void onOpen(Ticker ticker) {

    }

    @Override
    protected void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {

    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
