package net.warpgame.engine.postbuild.processing;

import java.util.HashMap;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

/**
 * @author Jaca777
 * Created 2018-08-11 at 00
 */
public class Context {

    private HashMap<String, Object> elements = new HashMap<>();

    public <T> T require(Class<?> clazz) {
        T t = (T) elements.get(clazz.getName());
        if(isNull(t)) {
           throw new ContextElementNotFoundException(clazz.getName());
        }
        return t;
    }

    public <T> T getOrCreate(Class<T> clazz, Supplier<T> creator) {
        return (T) elements.computeIfAbsent(clazz.getName(), s -> creator.get());
    }

    public void put(Object element) {
        this.elements.put(element.getClass().getName(), element);
    }

}
