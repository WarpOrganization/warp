package net.warpgame.servertest;

import net.warpgame.engine.core.context.EngineContext;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Test {

    public static void main(String... args) {
        EngineContext engineContext = new EngineContext();
//        Component root = new SceneComponent(engineContext);
        engineContext.getScene().addListener(new ConnectedListener(engineContext.getScene()));
    }
}
