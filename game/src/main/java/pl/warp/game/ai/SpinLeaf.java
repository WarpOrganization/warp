package pl.warp.game.ai;

import org.joml.Vector3f;
import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 17.01.2017
 */
public class SpinLeaf extends LeafNode {

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }

    @Override
    public int tick(Ticker ticker) {
        Component owner = (Component) ticker.getData("owner");
        PhysicalBodyProperty property = owner.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        property.addAngularVelocity(new Vector3f((float) Math.random()/1000, (float) Math.random()/1000, (float) Math.random()/1000));
        return Node.SUCCESS;
    }
}
