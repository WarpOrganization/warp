package net.warpgame.engine.core.runtime;

import net.warpgame.engine.core.context.EngineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author Jaca777
 * Created 2017-12-17 at 19
 */
public class EngineLauncher {

    private static final Logger logger = LoggerFactory.getLogger(EngineLauncher.class);
    public static final String CODESOURCE_DIR = getCodesourceDir(); //TODO throw away and make sth like homedir

    private static String getCodesourceDir() {
        try {
            ProtectionDomain protectionDomain = EngineContext.class.getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            URL location = codeSource.getLocation();
            String path = location.getPath();
            File jarFile = new File(URLDecoder.decode(path, "UTF-8"));
            return jarFile.getParent() + File.separator;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        try {
            loadRuntime();
            runEngine(args);
        } catch (ReflectiveOperationException e) {
            logger.error("Runtime launcher encountered a problem while running the engine app.");
            throw e;
        }
    }

    private static void loadRuntime() {
        EngineRuntime runtime = new EngineRuntime();
        runtime.load();
    }

    protected static void runEngine(String[] args) throws ReflectiveOperationException {
        String className = args[0];
        Class<?> aClass = Thread.currentThread()
                .getContextClassLoader()
                .loadClass(className);
        Method main = aClass.getMethod("main", String[].class);
        String[] engineArgs = Arrays.copyOfRange(args, 1, args.length, String[].class);
        main.invoke(null, (Object) engineArgs);
    }

}
