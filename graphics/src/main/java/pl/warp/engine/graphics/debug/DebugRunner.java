package pl.warp.engine.graphics.debug;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 15
 */
public class DebugRunner {
    public static void debugApp(String appClass) throws ReflectiveOperationException {
        DebugClassLoader classLoader = new DebugClassLoader();
        Class<?> aClass = classLoader.loadClass(appClass);
        runMain(aClass);
    }

    private static void runMain(Class<?> aClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method main = aClass.getMethod("main", String[].class);
        main.setAccessible(true);
        main.invoke(null, (Object) new String[0]);
    }
}
