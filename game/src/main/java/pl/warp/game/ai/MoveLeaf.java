package pl.warp.game.ai;

import org.joml.Vector3f;
import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class MoveLeaf extends LeafNode{

    Vector3f vel = new Vector3f();

    @Override
    public int tick(Ticker ticker) {
        System.out.println("asd");
        Component owner = (Component) ticker.getData("owner");
        PhysicalBodyProperty property = owner.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        if(property.getVelocity().length()<100){
            vel.set(property.getVelocity());
            vel.normalize().mul((float) (Math.random()*100));
            property.applyForce(vel);
        }
        return Node.SUCCESS;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }

    @Override
    public void addChild(Node node) {

    }
}
