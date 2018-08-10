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

    public void put(String scope, String element, int id) {
        Map<String, Integer> elementsMap = ids.computeIfAbsent(scope, s -> new HashMap<>());
        elementsMap.put(element, id);
    }

    public Optional<Integer> get(String scope, String element) {
        return Optional
                .ofNullable(ids.get(scope))
                .map(m -> m.get(element));
    }


}
