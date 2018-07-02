package net.warpgame.engine.core.script.initialization;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.script.Script;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptInitializersSupplier {

    private List<ScriptInitializerGenerator> scriptInitializers;

    public ScriptInitializersSupplier(ScriptInitializerGenerator[] initializerGenerators) {
        this.scriptInitializers = Arrays.asList(initializerGenerators);
    }

    public Consumer<Script>[] getScriptInitializers(Class<? extends Script> aClass) {
       return scriptInitializers
                .stream()
                .map(i -> i.getInitializer(aClass))
                .toArray(Consumer[]::new);
    }
}
