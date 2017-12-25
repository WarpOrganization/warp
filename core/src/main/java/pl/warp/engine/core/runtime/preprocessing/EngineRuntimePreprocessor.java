package pl.warp.engine.core.runtime.preprocessing;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2017-12-25 at 15
 */
public class EngineRuntimePreprocessor {

    private TypeIdProvider typeIdProvider;
    private SubclassResolver subclassResolver;

    private Class<?>[] preprocessedTypes;

    public EngineRuntimePreprocessor(Class<?>[] preprocessedTypes) {
        this.preprocessedTypes = preprocessedTypes;
        this.subclassResolver = new SubclassResolver(preprocessedTypes);
        this.typeIdProvider = new TypeIdProvider(subclassResolver, preprocessedTypes);
    }

    public void preprocess() {
        Set<Class<?>> resolvedSubtypes = new HashSet<>();
        for(Class<?> type : preprocessedTypes) {
            Reflections reflections = new Reflections();
            Set<Class<?>> subtypes = (Set<Class<?>>) reflections.getSubTypesOf(type);
            resolvedSubtypes.addAll(subtypes);
        }
        resolveSubtypes(resolvedSubtypes);
        typeIdProvider.generateIds();
    }

    private void resolveSubtypes(Set<Class<?>> subtypes) {
        for (Class<?> subtype : subtypes) {
            subclassResolver.resolve(subtype);
        }
        subclassResolver.finalizeResolving();
    }


    public int getId(String className) {
        return typeIdProvider.getId(className);
    }

    public String getName(int id) {
        return typeIdProvider.getName(id);
    }

    public List<String> getSubclasses(String className) {
        return subclassResolver.getSubclasses(className);
    }
}
