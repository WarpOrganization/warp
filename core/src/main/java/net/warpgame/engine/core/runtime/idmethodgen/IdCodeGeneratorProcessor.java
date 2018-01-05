package net.warpgame.engine.core.runtime.idmethodgen;


import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import net.warpgame.engine.core.runtime.processing.Processor;

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
        logger.info("Generating id methods for " + superclassName + " subclasses");
        this.processedSubclasses = runtimePreprocessor.getSubclasses(superclassName.replace("/", "."));
        System.out.println(processedSubclasses);
    }

    @Override
    public void process(ClassNode classNode) {
        String className = classNode.name
                .replace("/", ".");
        if(processedSubclasses.contains(className))
            processAndGenerateId(classNode, className);
    }

    private void processAndGenerateId(ClassNode classNode, String className) {
        int id = runtimePreprocessor.getId(className);
        ids.put(classNode.name, id);
        generator.generateIdMethod(id, classNode);
        logger.debug(String.format("Generated methods with id %d for %s class", id, classNode.name));
    }


    @Override
    public void finalizeProcessing() {
        logger.info("Generated id methods.");
    }
}
