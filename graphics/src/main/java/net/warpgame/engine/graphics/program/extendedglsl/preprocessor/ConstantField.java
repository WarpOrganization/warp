package net.warpgame.engine.graphics.program.extendedglsl.preprocessor;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
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
        public <T> T get(String name) {
            throw new NoSuchElementException("This constant field is empty");
        }
    };

    private Map<String, Object> constants = new HashMap<>();

    public <T> ConstantField set(String name, T value) {
        constants.put(name, value);
        return this;
    }

    public boolean isSet(String name) {
        return constants.get(name) != null;
    }

    public <T> T get(String name) {
        if (isSet(name)) return (T) constants.get(name);
        else throw new NoSuchElementException("Element with a given value " + name + " is not present in the constant field");
    }
}
