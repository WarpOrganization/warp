package net.warpgame.engine.core.script.initialization;

import net.warpgame.engine.core.script.updatescheduler.DelaySchedulerImpl;
import net.warpgame.engine.core.script.updatescheduler.TickIntervalSchedulerImpl;
import net.warpgame.engine.core.script.updatescheduler.UpdatePerTickSchedulerImpl;
import net.warpgame.engine.core.script.updatescheduler.UpdateScheduler;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.ScriptInitializationException;
import net.warpgame.engine.core.script.annotation.ScheduleByDelay;
import net.warpgame.engine.core.script.annotation.ScheduleByTickInterval;

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
        if (scriptClass.getAnnotation(ScheduleByTickInterval.class) != null)
            return getTickIntervalSchedulerBuilder(scriptClass);
        else if (scriptClass.getAnnotation(ScheduleByDelay.class) != null)
            return getDelaySchedulerBuilder(scriptClass);
        else return getUpdatePerTickSchedulerBuilder(scriptClass);
    }

    private MethodHandle getTickIntervalSchedulerBuilder(Class<?> scriptClass) {
        ScheduleByTickInterval intervalScheduling = scriptClass.getAnnotation(ScheduleByTickInterval.class);
        int ticks = intervalScheduling.interval();
        MethodHandle factory = getConstructor(scriptClass, TickIntervalSchedulerImpl.class, int.class);
        return MethodHandles.insertArguments(factory, 0, ticks);
    }

    private MethodHandle getDelaySchedulerBuilder(Class<?> scriptClass) {
        ScheduleByDelay intervalScheduling = scriptClass.getAnnotation(ScheduleByDelay.class);
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
