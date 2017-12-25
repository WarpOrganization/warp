package pl.warp.engine.core.runtime.preprocessing;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class SubclassResolver {

    private static final Logger logger = LoggerFactory.getLogger(SubclassResolver.class);

    private Class<?>[] resolvedClasses;
    private Multimap<String, String> classes = HashMultimap.create();
    private ArrayListMultimap<String, String> result = ArrayListMultimap.create();

    public SubclassResolver(Class<?>[] resolvedClasses) {
        this.resolvedClasses = resolvedClasses;
    }


    public void resolve(Class aClass) {
        classes.put(aClass.getSuperclass().getName(), aClass.getName());
    }

    public void finalizeResolving() {
        for (Class<?> resolvedClass : resolvedClasses) {
            resolve(resolvedClass.getName(), resolvedClass.getName());
        }
        for (Class<?> resolvedClass : resolvedClasses) {
            result.get(resolvedClass.getName()).sort(String::compareTo);
        }
        logResult();
    }

    protected void logResult() {
        logger.info("Finalized resolving subclasses:");
        Set<Map.Entry<String, Collection<String>>> entries = result.asMap().entrySet();
        for (Map.Entry<String, Collection<String>> entry : entries) {
            logger.debug(String.format("Loaded %d subclasses for %s", entry.getValue().size(), entry.getKey()));
        }
    }

    private void resolve(String superClass, String resolvedClass) {
        if (classes.containsKey(resolvedClass)) {
            for (String subclassName : classes.get(resolvedClass)) {
                result.put(superClass, subclassName);
                resolve(superClass, subclassName);
            }
        }

    }

    public List<String> getSubclasses(String className) {
        if (!result.containsKey(className))
            throw new IllegalStateException(className + " hasn't been resolved or resolver hasn't finalized the processing");
        else return result.get(className);
    }
}
