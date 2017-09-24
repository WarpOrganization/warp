package pl.warp.engine.core.script.initialization;

import com.google.common.collect.Lists;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.script.Script;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptInitializersSupplier {

    private List<ScriptInitializerGenerator> scriptInitializers;

    public ScriptInitializersSupplier(
            SchedulerInitializer schedulerInitializer,
            OwnerPropertyInitializer ownerPropertyInitializer,
            EventHandlerInitializer eventHandlerInitializer,
            ServiceDependencyInitializer serviceDependencyInitializer
    ) {
        this.scriptInitializers = Lists.newArrayList(
                schedulerInitializer,
                ownerPropertyInitializer,
                eventHandlerInitializer,
                serviceDependencyInitializer
        );
    }

    public Consumer<Script>[] getScriptInitializers(Class<? extends Script> aClass) {
       return scriptInitializers
                .stream()
                .map(i -> i.getInitializer(aClass))
                .toArray(Consumer[]::new);
    }
}
