package pl.warp.engine.core.runtime.subclass;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.warp.engine.core.runtime.processing.Processor;

import java.util.*;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class SubclassResolverProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(SubclassResolverProcessor.class);

    private String[] resolvedClassesNames;
    private Multimap<String, String> classes = HashMultimap.create();
    private ArrayListMultimap<String, String> result = ArrayListMultimap.create();

    public SubclassResolverProcessor(String[] resolvedClassesNames) {
        this.resolvedClassesNames = resolvedClassesNames;
    }

    @Override
    public void initializeProcessing() {
        logger.info("Resolving subclasses for: " + Arrays.toString(resolvedClassesNames));
    }

    @Override
    public void process(ClassNode classNode) {
        classes.put(classNode.superName, classNode.name);
    }

    @Override
    public void finalizeProcessing() {
        for (String name : resolvedClassesNames) {
            resolve(name, name);
        }
        for (String name : resolvedClassesNames) {
            result.get(name).sort(String::compareTo);
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

    private void resolve(String superClass, String className) {
        if (classes.containsKey(className)) {
            for (String subclassName : classes.get(className)) {
                result.put(superClass, subclassName);
                resolve(superClass, className);
            }
        }
    }

    public List<String> getSubclasses(String className) {
        if (!result.containsKey(className))
            throw new IllegalStateException(className + "hasn't been resolved or resolver hasn't finalized the processing");
        else return result.get(className);
    }
}
