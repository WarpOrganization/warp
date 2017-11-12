package pl.warp.test;

import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.graphics.debug.DebugRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class Debug {
    public static void main(String[] args) throws ReflectiveOperationException, MalformedURLException {
        URL url = Paths.get(EngineContext.CODESOURCE_DIR).toUri().toURL();
        DebugRunner.debugApp(args[0], url);
    }
}
