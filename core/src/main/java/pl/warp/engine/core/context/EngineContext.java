package pl.warp.engine.core.context;

import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.core.script.ScriptManager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author Jaca777
 * Created 2016-12-04 at 14
 */
public class EngineContext {

    public static final String CODESOURCE_DIR = getCodesourceDir();

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


    private Context context;
    private Scene scene;
    private ScriptManager scriptRegistry;
    private EventDispatcher eventDispatcher;

    public EngineContext() {
        this.context = Context.create();
    }

    public Scene getScene() {
        return scene;
    }

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScriptManager getScriptManager() {
        return scriptRegistry;
    }

    protected void setScriptManager(ScriptManager scriptRegistry) {
        this.scriptRegistry = scriptRegistry;
    }


    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    protected void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }
}
