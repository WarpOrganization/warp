package pl.warp.engine.core.context;

import pl.warp.engine.core.component.ComponentRegistry;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneHolder;
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
    private SceneHolder sceneHolder;
    private ScriptManager scriptManager;
    private EventDispatcher eventDispatcher;
    private ComponentRegistry componentRegistry;

    public EngineContext() {
        this.context = Context.create();
        this.scriptManager = context.findOne(ScriptManager.class).get();
        this.eventDispatcher = context.findOne(EventDispatcher.class).get();
        this.sceneHolder = context.findOne(SceneHolder.class).get();
        this.componentRegistry = context.findOne(ComponentRegistry.class).get();
        this.sceneHolder.setScene(new Scene(this));
    }

    public Scene getScene() {
        return sceneHolder.getScene();
    }


    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public Context getLoadedContext() {
        return context;
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }
}
