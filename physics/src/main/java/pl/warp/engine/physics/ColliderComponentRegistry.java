package pl.warp.engine.physics;

import pl.warp.engine.core.component.Component;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.10.2017
 */
public class ColliderComponentRegistry {
    private HashMap<Integer, Component> components = new HashMap<>();

    private int counter = 0;

    void addComponennt(Component component) {
        components.put(counter, component);
        counter++;
    }

    void removeCompoent(int key) {
        components.remove(key);
    }

    public Component getCompoenent(int key) {
        return components.get(key);
    }
}
