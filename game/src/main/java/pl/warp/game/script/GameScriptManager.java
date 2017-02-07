package pl.warp.game.script;

import org.apache.log4j.Logger;
import pl.warp.engine.core.scene.Event;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.script.ScriptInitializationException;
import pl.warp.engine.core.scene.script.ScriptManager;
import pl.warp.game.script.updatescheduler.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public class GameScriptManager extends ScriptManager {

    private static final Logger LOGGER = Logger.getLogger(GameScriptManager.class);

    @Override
    public void initializeScript(Script script) {
        super.initializeScript(script);
        try {
            initScheduler((GameScript) script);
            loadHandlers((GameScript) script);
        }catch (ClassCastException c) {
            LOGGER.warn("Script " + script.getClass().getSimpleName() + " is not a game script. It may cause it not to work properly.");
        }
    }

    private void initScheduler(GameScript script) {
        Class c = script.getClass();
        if (c.getAnnotation(TickIntervalScheduling.class) != null)
            initTickIntervalScheduler(script);
        else if (c.getAnnotation(DelayScheduling.class) != null)
            initDelayScheduler(script);
        else initUpdatePerTickScheduler(script);
    }

    private void initTickIntervalScheduler(GameScript script) {
        Class c = script.getClass();
        TickIntervalScheduling intervalScheduling = (TickIntervalScheduling) c.getAnnotation(TickIntervalScheduling.class);
        int ticks = intervalScheduling.interval();
        TickIntervalSchedulerImpl scheduler = new TickIntervalSchedulerImpl(ticks);
        script.setScheduler(scheduler);
    }

    private void initDelayScheduler(GameScript script) {
        Class c = script.getClass();
        DelayScheduling intervalScheduling = (DelayScheduling) c.getAnnotation(DelayScheduling.class);
        int delay = intervalScheduling.delayInMillis();
        DelaySchedulerImpl scheduler = new DelaySchedulerImpl(delay);
        script.setScheduler(scheduler);
    }

    private void initUpdatePerTickScheduler(GameScript script) {
        script.setScheduler(new UpdatePerTickSchedulerImpl());
    }

    private void loadHandlers(GameScript script) {
        Class c = script.getClass();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) != null)
                createHandler(script, method);
        }
    }

    private void createHandler(GameScript script, Method method) {
        if (method.getParameterCount() != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0]))
            throw new ScriptInitializationException(new IllegalArgumentException("Event handler method has to receive only the event as parameter."));
        try {
            bindHandle(script, method);
        } catch (IllegalAccessException e) {
            throw new ScriptInitializationException(e);
        }
    }

    private void bindHandle(GameScript script, Method method) throws IllegalAccessException {
        method.setAccessible(true);
        EventHandler handler = method.getAnnotation(EventHandler.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.unreflect(method);
        MethodHandle scriptHandle = handle.bindTo(script);
        SimpleListener.createListener(script.getOwner(), handler.eventName(), (Event e) -> {
            try {
                scriptHandle.invoke(e);
            } catch (Throwable throwable) {
                throw new EventHandlerException(throwable);
            }
        });
    }
}
