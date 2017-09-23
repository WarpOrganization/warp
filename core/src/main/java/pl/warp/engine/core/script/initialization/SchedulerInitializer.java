package pl.warp.engine.core.script.initialization;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.ScriptInitializationException;
import pl.warp.engine.core.script.annotation.DelayScheduling;
import pl.warp.engine.core.script.annotation.TickIntervalScheduling;
import pl.warp.engine.core.script.updatescheduler.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 23
 */
@Service
public class SchedulerInitializer implements ScriptInitializerGenerator {

    @Override
    public Consumer<? extends Script> getInitializer(Class<? extends Script> scriptClass) {
        MethodHandle schedulerFactory = getSchedulerFactory(scriptClass);
        return s -> setScheduler(s, schedulerFactory);
    }

    private void setScheduler(Script script, MethodHandle schedulerFactory) {
        try {
            UpdateScheduler scheduler = (UpdateScheduler) schedulerFactory.invoke();
            script.setScheduler(scheduler);
        } catch (Throwable throwable) {
            throw new ScriptInitializationException(script.getClass(), "Failed to set update scheduler", throwable);
        }
    }

    private MethodHandle getSchedulerFactory(Class<?> scriptClass) {
        if (scriptClass.getAnnotation(TickIntervalScheduling.class) != null)
            return getTickIntervalSchedulerBuilder(scriptClass);
        else if (scriptClass.getAnnotation(DelayScheduling.class) != null)
            return getDelaySchedulerBuilder(scriptClass);
        else return getUpdatePerTickSchedulerBuilder(scriptClass);
    }

    private MethodHandle getTickIntervalSchedulerBuilder(Class<?> scriptClass) {
        TickIntervalScheduling intervalScheduling = scriptClass.getAnnotation(TickIntervalScheduling.class);
        int ticks = intervalScheduling.interval();
        MethodHandle factory = getConstructor(scriptClass, TickIntervalSchedulerImpl.class, int.class);
        return MethodHandles.insertArguments(factory, 0, ticks);
    }

    private MethodHandle getDelaySchedulerBuilder(Class<?> scriptClass) {
        DelayScheduling intervalScheduling = scriptClass.getAnnotation(DelayScheduling.class);
        int delay = intervalScheduling.delayInMillis();
        MethodHandle factory = getConstructor(scriptClass, DelaySchedulerImpl.class, int.class);
        return MethodHandles.insertArguments(factory, 0, delay);
    }


    private MethodHandle getUpdatePerTickSchedulerBuilder(Class<?> scriptClass) {
        return getConstructor(scriptClass, UpdatePerTickSchedulerImpl.class);
    }

    private MethodHandle getConstructor(Class<?> scriptClass, Class<?> c, Class<?>... args) {
        try {
            Constructor<?> constructor =
                    c.getConstructor(args);
            return MethodHandles.lookup()
                    .unreflectConstructor(constructor);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new ScriptInitializationException(scriptClass, e);
        }
    }
}
