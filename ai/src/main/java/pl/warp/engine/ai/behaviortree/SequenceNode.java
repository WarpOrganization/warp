package pl.warp.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class SequenceNode extends CompositeNode{

    @Override
    int tick(Ticker ticker, int delta) {
        for(Node child : children){
            int status = ticker.tickNode(child);

            if(status !=Node.SUCCESS){
                return status;
            }
        }
        return Node.SUCCESS;
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
