package pl.warp.engine.core.runtime.idmethodgen;


import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.warp.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import pl.warp.engine.core.runtime.processing.Processor;
import pl.warp.engine.core.runtime.preprocessing.SubclassResolver;

import java.util.HashMap;
import java.util.List;

/**
 * @author Jaca777
 * Created 2017-12-17 at 19
 */
public class IdCodeGeneratorProcessor implements Processor<ClassNode> {

    private static final Logger logger = LoggerFactory.getLogger(IdCodeGeneratorProcessor.class);

    private String superclassName;
    private EngineRuntimePreprocessor runtimePreprocessor;
    private List<String> processedSubclasses;
    private IdMethodGenerator generator = new IdMethodGenerator();

    private HashMap<String, Integer> ids = new HashMap<>();

    public IdCodeGeneratorProcessor(String superclassName, EngineRuntimePreprocessor runtimePreprocessor) {
        this.superclassName = superclassName;
        this.runtimePreprocessor = runtimePreprocessor;
    }

    @Override
    public void initializeProcessing() {
        logger.info("Generating ids for " + superclassName + " subclasses");
        this.processedSubclasses = runtimePreprocessor.getSubclasses(superclassName);
    }

    @Override
    public void process(ClassNode classNode) {
        String className = classNode.name;
        if(processedSubclasses.contains(className))
            processAndGenerateId(classNode);
    }

    private void processAndGenerateId(ClassNode classNode) {
        int id = processedSubclasses.indexOf(classNode.name);
        ids.put(classNode.name, id);
        generator.generateIdMethod(id, classNode);
        logger.debug(String.format("Generated methods with id %d for %s class", id, classNode.name));
    }


    @Override
    public void finalizeProcessing() {
        logger.info("Generated id methods.");
    }
}
