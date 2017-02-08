package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.test.GunProperty;

/**
 * @author Hubertus
 *         Created 25.01.17
 */
public class StopShootingLeaf extends LeafNode{

    GunProperty gunProperty;

    @Override
    public int tick(Ticker ticker, int delta) {
        gunProperty.setTriggered(false);
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
        gunProperty = ticker.getOwner().getProperty(GunProperty.GUN_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
