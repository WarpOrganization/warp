package net.warpgame.engine.postbuild.id;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jaca777
 * Created 2018-08-08 at 19
 */
public class IdRegistry {

    private Map<String, Map<String, Integer>> ids = new HashMap<>();
    private Map<String, String> classToSuperclass = new HashMap<>();

    public void put(String superclass, String cls, int id) {
        classToSuperclass.put(cls, superclass);
        Map<String, Integer> subclassesMap = ids.computeIfAbsent(superclass, s -> new HashMap<>());
        subclassesMap.put(cls, id);
    }

    public Optional<Integer> get(String cls) {
        return get(classToSuperclass.get(cls), cls);
    }

    public Optional<Integer> get(String superclass, String cls) {
        return Optional
                .ofNullable(ids.get(superclass))
                .map(m -> m.get(cls));
    }


}
