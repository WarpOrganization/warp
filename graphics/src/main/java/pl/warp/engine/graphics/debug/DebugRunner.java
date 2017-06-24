package pl.warp.engine.graphics.debug;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

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

    private static void runMain(Class<?> aClass) throws ReflectiveOperationException {
        setupCodesource(aClass);
        Method main = aClass.getMethod("main", String[].class);
        main.setAccessible(true);
        main.invoke(null, (Object) new String[0]);
    }

    private static void setupCodesource(Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        URL codesourceLocation = DebugRunner.class.getProtectionDomain().getCodeSource().getLocation();
        ProtectionDomain domain = aClass.getProtectionDomain();
        CodeSource source = domain.getCodeSource();
        Class sourceClass = source.getClass();
        Field location = sourceClass.getDeclaredField("location");
        location.setAccessible(true);
        location.set(source, codesourceLocation);
    }
}
