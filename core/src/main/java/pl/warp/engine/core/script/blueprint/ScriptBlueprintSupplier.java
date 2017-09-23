package pl.warp.engine.core.script.blueprint;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.script.Script;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptBlueprintSupplier {

    private Map<Class<? extends Script>, ScriptBlueprint> scriptBlueprint;
    private ScriptBlueprintLoader blueprintLoader;

    public ScriptBlueprintSupplier(ScriptBlueprintLoader blueprintLoader) {
        this.scriptBlueprint = new HashMap<>();
        this.blueprintLoader = blueprintLoader;
    }

    public ScriptBlueprint getBlueprint(Class<? extends Script> scriptClass) {
        return scriptBlueprint.computeIfAbsent(scriptClass, blueprintLoader::loadBlueprint);
    }
}
