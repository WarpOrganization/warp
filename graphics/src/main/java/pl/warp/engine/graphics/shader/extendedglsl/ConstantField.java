package pl.warp.engine.graphics.shader.extendedglsl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ConstantField {
    private Map<String, Object> constants = new HashMap<>();

    public <T> ConstantField set(String name, T value) {
        constants.put(name, value);
        return this;
    }

    public boolean isSet(String name){
        return constants.get(name) != null;
    }

    public <T> T get(String name) {
        return (T) constants.get(name);
    }
}
