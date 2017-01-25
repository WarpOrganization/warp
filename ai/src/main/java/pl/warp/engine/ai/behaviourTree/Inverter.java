package pl.warp.engine.ai.behaviourTree;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class Inverter extends DecoratorNode {

    @Override
    int tick(Ticker ticker) {
        switch (child.tick(ticker)) {
            case Node.RUNNING:
                return Node.RUNNING;
            case Node.SUCCESS:
                return Node.FAILURE;
            case Node.FAILURE:
                return Node.SUCCESS;
            default:
                return Node.FAILURE;
        }
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
