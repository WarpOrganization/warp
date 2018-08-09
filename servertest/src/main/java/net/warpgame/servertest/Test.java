package net.warpgame.servertest;

import net.warpgame.engine.console.ConsoleService;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.physics.PhysicsService;
import net.warpgame.engine.server.ClientRegistry;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Test {

    public static void start(EngineRuntime engineRuntime) {
        EngineContext engineContext = new EngineContext("dev", "fullPhysics", "server");
        engineContext.getLoadedContext().addService(engineRuntime.getIdRegistry());
        engineContext.getLoadedContext().findOne(ConsoleService.class).get().init();

//        Component root = new SceneComponent(engineContext);8558
        engineContext.getScene().addListener(new ConnectedListener(
                engineContext.getScene(),
                engineContext.getComponentRegistry(),
                engineContext.getLoadedContext().findOne(PhysicsService.class).get(),
                engineContext.getLoadedContext().findOne(ClientRegistry.class).get()));
        Component referenceShip = new SceneComponent(engineContext.getScene());
        TransformProperty transformProperty2 = new TransformProperty();
        transformProperty2.move(new Vector3f(5, 0, 0));
        referenceShip.addProperty(transformProperty2);
    }
}