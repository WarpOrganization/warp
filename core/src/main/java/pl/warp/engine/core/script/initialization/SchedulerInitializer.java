package pl.warp.engine.core.script.initialization;

import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.script.Script;

import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 23
 */
@Service
public class SchedulerInitializer implements ScriptInitializerGenerator {
    @Override
    public Consumer<? extends Script> getInitializer(Class<? extends Script> scriptClass) {
        //TODO
        return (Consumer<Script>) script -> {};
    }
}
