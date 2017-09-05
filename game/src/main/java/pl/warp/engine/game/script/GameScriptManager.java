package pl.warp.engine.game.script;

import org.apache.log4j.Logger;
import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.core.component.*;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.ScriptInitializationException;
import pl.warp.engine.core.script.ScriptManager;
import pl.warp.engine.game.script.updatescheduler.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */

@Service
public class GameScriptManager extends ScriptManager {

    private static final Logger LOGGER = Logger.getLogger(GameScriptManager.class);

    @Override
    public void initializeScript(Script script) {
        try {
            initScheduler((GameScript) script);
            loadHandlers((GameScript) script);
            loadProperties((GameScript) script);
        } catch (ClassCastException c) {
            LOGGER.warn("Script " + script.getClass().getSimpleName() + " is not a game script. It may cause it not to work properly.");
        }
        super.initializeScript(script);
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
            throw new ScriptInitializationException(
                    new IllegalArgumentException("Event handler method has to receive only the event as parameter."));
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
        if (!Modifier.isStatic(method.getModifiers()))
            handle = handle.bindTo(script);
        MethodHandle finalHandle = handle;
        SimpleListener.createListener(script.getOwner(), handler.eventName(), (Event e) -> {
            try {
                finalHandle.invoke(e);
            } catch (Throwable throwable) {
                throw new EventHandlerException(throwable);
            }
        });
    }

    private void loadProperties(GameScript script) {
        Class c = script.getClass();
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(OwnerProperty.class) != null)
                loadProperty(script, field);
        }
    }

    private void loadProperty(GameScript script, Field field) {
        OwnerProperty ownerProperty = field.getAnnotation(OwnerProperty.class);
        Component owner = script.getOwner();
        if (!owner.hasProperty(ownerProperty.name()))
            throw new ScriptInitializationException(
                    new IllegalStateException("Component has no property named " + ownerProperty.name() + "."));
        else {
            setField(script, field, ownerProperty);
        }
    }

    private void setField(GameScript script, Field field, OwnerProperty ownerProperty) {
        try {
            field.setAccessible(true);
            Property property = script.getOwner().getProperty(ownerProperty.name());
            field.set(script, property);
        } catch (Exception e) {
            throw new ScriptInitializationException(e);
        }
    }


}
