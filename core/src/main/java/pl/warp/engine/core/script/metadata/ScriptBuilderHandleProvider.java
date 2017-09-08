package pl.warp.engine.core.script.metadata;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.ScriptInitializationException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

/**
 * @author Jaca777
 * Created 2017-09-08 at 23
 */
public class ScriptBuilderHandleProvider {
    public MethodHandle getBuilderHandle(Class<? extends Script> aClass) {
        try {
            Constructor<? extends Script> builderConstructor = getBuilderConstructor(aClass);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            return lookup.unreflectConstructor(builderConstructor);
        } catch (ReflectiveOperationException e) {
            throw new ScriptInitializationException(e);
        }
    }

    private Constructor<? extends Script> getBuilderConstructor(Class<? extends Script> aClass) throws NoSuchMethodException {
        return aClass.getConstructor(Component.class);
    }
}
