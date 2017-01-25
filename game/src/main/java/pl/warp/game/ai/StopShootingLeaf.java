package pl.warp.game.ai;

import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.game.GunProperty;

/**
 * @author Hubertus
 *         Created 25.01.17
 */
public class StopShootingLeaf extends LeafNode{

    GunProperty gunProperty;

    @Override
    public int tick(Ticker ticker, int delta) {
        if (gunProperty == null)
            gunProperty = ((Component) ticker.getData(OWNER_KEY)).getProperty(GunProperty.GUN_PROPERTY_NAME);
        gunProperty.setTriggered(false);
        return Node.SUCCESS;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
