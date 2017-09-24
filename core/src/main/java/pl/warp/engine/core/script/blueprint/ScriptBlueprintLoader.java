package pl.warp.engine.core.script.blueprint;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.initialization.ScriptInitializersSupplier;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptBlueprintLoader {

    private ScriptInitializersSupplier scriptInitializersSupplier;
    private ScriptBuilderBlueprintProvider scriptBuilderBlueprintProvider;

    public ScriptBlueprintLoader(ScriptInitializersSupplier scriptInitializersSupplier) {
        this.scriptInitializersSupplier = scriptInitializersSupplier;
        this.scriptBuilderBlueprintProvider = new ScriptBuilderBlueprintProvider();
    }

    public ScriptBlueprint loadBlueprint(Class<? extends Script> aClass) {
        MethodHandle builder =  scriptBuilderBlueprintProvider.getBuilderHandle(aClass);
        Consumer<Script>[] initializers = scriptInitializersSupplier.getScriptInitializers(aClass);
        return new ScriptBlueprint(builder, initializers);
    }
}
