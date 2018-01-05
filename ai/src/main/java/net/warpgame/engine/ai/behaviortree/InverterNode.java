package net.warpgame.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class InverterNode extends DecoratorNode {

    @Override
    int tick(Ticker ticker, int delta) {
        switch (ticker.tickNode(child)) {
            case RUNNING:
                return RUNNING;
            case SUCCESS:
                return FAILURE;
            case FAILURE:
                return SUCCESS;
            default:
                return FAILURE;
        }
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
