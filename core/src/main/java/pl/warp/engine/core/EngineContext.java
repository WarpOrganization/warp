package pl.warp.engine.core;

import pl.warp.engine.core.scene.EventDispatcher;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.script.ScriptManager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 14
 */
public class EngineContext {

    public static final String GAME_DIR_PATH = getGameDirPath();

    private static String getGameDirPath() {
        try {
            String path = EngineContext.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            File jarFile = new File(URLDecoder.decode(path, "UTF-8"));
            return jarFile.getParent() + File.separator;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Scene scene;
    private ScriptManager scriptManager;
    private Input input;
    private EventDispatcher eventDispatcher;

    public Scene getScene() {
        return scene;
    }

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    protected void setScriptManager(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    public Input getInput() {
        return input;
    }

    protected void setInput(Input input) {
        this.input = input;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    protected void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }
}
