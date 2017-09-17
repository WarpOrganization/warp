package pl.warp.engine.core.script.initialization;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.SimpleListener;
import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.script.annotation.EventHandler;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.ScriptInitializationException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 23
 */
@Service
public class EventHandlerInitializer implements ScriptInitializerGenerator {
    @Override
    public Consumer<? extends Script> getInitializer(Class<? extends Script> scriptClass) {
        List<EventHandlerData> eventHandlerData = loadHandlers(scriptClass);
        return (Consumer<Script>) script -> initHandlers(script, eventHandlerData);
    }

    private void initHandlers(Script script, List<EventHandlerData> eventHandlerData) {
        Component scriptOwner = script.getOwner();
        for(EventHandlerData data : eventHandlerData) {
            MethodHandle handle = data.getHandle().bindTo(script);
            Consumer<? extends Event> invoker = ev -> {
                try {
                    handle.invoke(ev);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable); //pass it
                }
            };
            SimpleListener.createListener(scriptOwner, data.getEventName(), invoker);
        }
    }

    private List<EventHandlerData> loadHandlers(Class<? extends Script> scriptClass) {
        List<EventHandlerData> data = new ArrayList<>();
        Method[] methods = scriptClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) != null)
                data.add(createHandler(method));
        }
        return data;
    }

    private EventHandlerData createHandler(Method method) {
        if (method.getParameterCount() != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0]))
            throw new ScriptInitializationException(
                    method.getDeclaringClass(),
                    new IllegalArgumentException("Event handler method has to receive only the event as parameter."));
        try {
            return getHandlerData(method);
        } catch (IllegalAccessException e) {
            throw new ScriptInitializationException(method.getDeclaringClass(), e);
        }
    }

    private EventHandlerData getHandlerData(Method method) throws IllegalAccessException {
        method.setAccessible(true);
        EventHandler handler = method.getAnnotation(EventHandler.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.unreflect(method);
        if (Modifier.isStatic(method.getModifiers()))
            throw new ScriptInitializationException(
                    method.getDeclaringClass(),
                    "Event handler method must not be static: " + method.getName()
            );
        return new EventHandlerData(handler.eventName(), handle);
    }

    private class EventHandlerData {
        private String eventName;
        private MethodHandle handle;

        public EventHandlerData(String eventName, MethodHandle handle) {
            this.eventName = eventName;
            this.handle = handle;
        }

        public String getEventName() {
            return eventName;
        }

        public MethodHandle getHandle() {
            return handle;
        }
    }
}
