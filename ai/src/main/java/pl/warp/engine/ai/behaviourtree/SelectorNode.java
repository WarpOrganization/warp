package pl.warp.engine.ai.behaviourtree;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class SelectorNode extends CompositeNode {

    @Override
    int tick(Ticker ticker, int delta) {
        for (Node child : children) {
            int status = ticker.tickNode(child);

            if (status == Node.SUCCESS) {
                return Node.SUCCESS;
            }
            if (status == Node.RUNNING) {
                return Node.RUNNING;
            }
        }
        return Node.FAILURE;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
