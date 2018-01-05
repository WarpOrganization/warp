package net.warpgame.servertest;

import org.joml.Vector3f;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.context.EngineContext;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Test {

    public static void main(String... args) {
        EngineContext engineContext = new EngineContext();
        Component root = new SceneComponent(engineContext);
        Component testComponent = new SceneComponent(root);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(1,2,3));
        testComponent.addProperty(transformProperty);

    }
}
