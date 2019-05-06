package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.runtime.EngineRuntime;

/**
 * @author MarconZet
 * Created 06.05.2019
 */
public class GraphicsTest {
    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev", "graphics");
        engineContext.getLoadedContext().addService(engineRuntime.getIdRegistry());
    }
}
