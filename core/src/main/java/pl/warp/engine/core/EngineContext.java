
package pl.warp.engine.core;

import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.script.ScriptContext;

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
    private ScriptContext scriptContext;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

}
