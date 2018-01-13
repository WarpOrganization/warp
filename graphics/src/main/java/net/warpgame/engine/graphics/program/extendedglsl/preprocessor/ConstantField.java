package net.warpgame.engine.graphics.program.extendedglsl.preprocessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Jaca777
 * Created 2016-07-20 at 13
 */
public class ConstantField {

    public static final ConstantField EMPTY_CONSTANT_FIELD = new ConstantField() {
        @Override
        public <T> ConstantField set(String name, T value) {
            throw new UnsupportedOperationException("Empty constant field is immutable.");
        }

        @Override
        public boolean isSet(String name) {
            return false;
        }

        @Override
        public String get(String name) {
            throw new NoSuchElementException("This constant field is empty");
        }

        @Override
        public Map<String, String> getConstants() {
            return Collections.emptyMap();
        }
    };

    private Map<String, String> constants = new HashMap<>();

    public <T> ConstantField set(String name, T value) {
        constants.put(name, value.toString());
        return this;
    }

    public boolean isSet(String name) {
        return constants.get(name) != null;
    }

    public String get(String name) {
        if (isSet(name)) return constants.get(name);
        else throw new NoSuchElementException("Element with a given value " + name + " is not present in the constant field");
    }

    public Map<String, String> getConstants() {
        return constants;
    }
}
