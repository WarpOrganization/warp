package net.warpgame.engine.core.runtime;

import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;

/**
 * @author Jaca777
 * Created 2018-07-01 at 02
 */
public class IdRegistry {
    private EngineRuntimePreprocessor engineRuntimePreprocessor;

    public IdRegistry(EngineRuntimePreprocessor engineRuntimePreprocessor) {
        this.engineRuntimePreprocessor = engineRuntimePreprocessor;
    }


    public int getId(String className) {
        return engineRuntimePreprocessor.getId(className);
    }

    public String getName(int id) {
        return engineRuntimePreprocessor.getName(id);
    }
}
