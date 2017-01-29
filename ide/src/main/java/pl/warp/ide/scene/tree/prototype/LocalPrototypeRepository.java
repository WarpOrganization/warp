package pl.warp.ide.scene.tree.prototype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public class LocalPrototypeRepository implements PrototypeRepository {

    private List<ComponentPrototype> prototypes;


    public LocalPrototypeRepository() {
        prototypes = new ArrayList<>();
    }

    @Override
    public Collection<ComponentPrototype> getPrototypes() {
        return prototypes;
    }

    @Override
    public void add(ComponentPrototype prototype){
        prototypes.add(prototype);
    }

    @Override
    public void remove(ComponentPrototype prototype) {
        prototypes.remove(prototype);
    }
}
