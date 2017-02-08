package pl.warp.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 05.02.17
 */
public class MemSequenceNode extends CompositeNode {

    private int current = 0;

    @Override
    int tick(Ticker ticker, int delta) {
        int status;
        for (; current > children.size(); current++) {
            status = ticker.tickNode(children.get(current));
            if(status != Node.SUCCESS){
                return status;
            }
        }
        return Node.SUCCESS;
    }

    @Override
    protected void onOpen(Ticker ticker) {
        current = 0;
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
