package net.warpgame.engine.core.runtime.preprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2017-12-25 at 15
 */
public class TypeIdProvider {

    private static final Logger logger = LoggerFactory.getLogger(TypeIdProvider.class);

    private Map<Integer, String> idToName = new HashMap<>(); //BiMap won't do its work.
    private Map<String, Integer> nameToId = new HashMap<>();
    private SubclassResolver subclassResolver;
    private Class<?>[] preprocessedTypes;

    public TypeIdProvider(SubclassResolver subclassResolver, Class<?>[] preprocessedTypes) {
        this.subclassResolver = subclassResolver;
        this.preprocessedTypes = preprocessedTypes;
    }

    public void generateIds() {
        List<String> allProcessedClasses = new ArrayList<>();
        for (Class<?> preprocessedType : preprocessedTypes) {
            List<String> subclasses = subclassResolver.getSubclasses(preprocessedType.getName());
            allProcessedClasses.addAll(subclasses);
        }
        generateIds(allProcessedClasses);
    }

    private void generateIds(List<String> classes) {
        logger.info(String.format("Generating ids for %d classes", classes.size()));
        for (int i = 0; i < classes.size(); i++) {
            String name = classes.get(i);
            idToName.put(i, name);
            nameToId.put(name, i);
            logger.debug(String.format("%s id: %d", name, i));
        }
    }

    public String getName(int id) {
        return idToName.get(id);
    }

    public int getId(String name) {
        return nameToId.get(name);
    }
}
