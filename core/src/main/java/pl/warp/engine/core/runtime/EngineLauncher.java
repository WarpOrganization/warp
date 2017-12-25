package pl.warp.engine.core.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jaca777
 * Created 2017-12-17 at 19
 */
public class EngineLauncher {

    private static final Logger logger = LoggerFactory.getLogger(EngineLauncher.class);

    public static void main(String[] args) throws ReflectiveOperationException {
        try {
            loadRuntime();
            runEngine(args);
        } catch (ReflectiveOperationException e) {
            logger.error("Launcher encountered a problem while running the engine app.");
            throw e;
        }
    }

    private static void loadRuntime() {
        EngineRuntime runtime = new EngineRuntime();
        runtime.load();
    }

    protected static void runEngine(String[] args) throws ReflectiveOperationException {
        String className = args[0];
        Class<?> aClass = EngineLauncher.class.getClassLoader().loadClass(className);
        Method main = aClass.getMethod("main", String[].class);
        String[] engineArgs = Arrays.copyOfRange(args, 1, args.length, String[].class);
        main.invoke(null, (Object) engineArgs);
    }


}
