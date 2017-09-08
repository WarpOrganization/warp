package pl.warp.engine.core.script.metadata;

import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.initialization.ScriptInitializersSupplier;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptMetadataLoader {

    private ScriptInitializersSupplier scriptInitializersSupplier;
    private ScriptBuilderHandleProvider scriptBuilderHandleProvider;

    public ScriptMetadataLoader(ScriptInitializersSupplier scriptInitializersSupplier) {
        this.scriptInitializersSupplier = scriptInitializersSupplier;
        this.scriptBuilderHandleProvider = new ScriptBuilderHandleProvider();
    }

    public ScriptMetadata loadMetadata(Class<? extends Script> aClass) {
        MethodHandle builder =  scriptBuilderHandleProvider.getBuilderHandle(aClass);
        Consumer<? extends Script>[] initializers = scriptInitializersSupplier.getScriptInitializers(aClass);
        return new ScriptMetadata(builder, initializers);
    }
}
