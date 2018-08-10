package net.warpgame.engine.postbuild.processing;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * @author Jaca777
 * Created 2018-08-11 at 00
 */
public class Context {

    private HashMap<String, Object> elements = new HashMap<>();

    public <T> T require(Class<?> clazz) {
     return (T) elements.get(clazz.getName());
    }

    public <T> T getOrCreate(Class<T> clazz, Supplier<T> creator) {
        return (T) elements.computeIfAbsent(clazz.getName(), s -> creator.get());
    }

    public void put(Object element) {
        this.elements.put(element.getClass().getName(), element);
    }

}
