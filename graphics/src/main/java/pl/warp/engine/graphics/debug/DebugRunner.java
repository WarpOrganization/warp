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

    public static void debugApp(String appClass, URL codesourceLocation) throws ReflectiveOperationException {

        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        Class<?> aClass = classLoader1.loadClass(appClass);
        System.out.println(aClass.getClassLoader());
        runMain(aClass, codesourceLocation);
    }

    private static void runMain(Class<?> aClass, URL codesourceLocation) throws ReflectiveOperationException {
        //setupCodesource(aClass, codesourceLocation);
        Method main = aClass.getMethod("main", String[].class);
        main.setAccessible(true);
        main.invoke(null, (Object) new String[0]);
    }

    private static void setupCodesource(Class<?> aClass, URL codesourceLocation) throws NoSuchFieldException, IllegalAccessException {
        ProtectionDomain domain = aClass.getProtectionDomain();
        CodeSource source = domain.getCodeSource();
        Class sourceClass = source.getClass();
        Field location = sourceClass.getDeclaredField("location");
        location.setAccessible(true);
        location.set(source, codesourceLocation);
    }
}
