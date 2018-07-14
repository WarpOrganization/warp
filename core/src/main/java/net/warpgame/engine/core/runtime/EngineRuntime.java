package net.warpgame.engine.core.runtime;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.runtime.idannotation.IdAnnotationProcessor;
import net.warpgame.engine.core.runtime.idcall.IdCallProcessor;
import net.warpgame.engine.core.runtime.idmethodgen.IdCodeGeneratorProcessor;
import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import net.warpgame.engine.core.runtime.processing.ComposedProcessor;
import net.warpgame.engine.core.runtime.processing.Processor;
import net.warpgame.engine.core.serialization.Serialization;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class EngineRuntime {

    public static final String PREFIX = "net.warpgame";
    public static final String PROPERTY_CLASS_NAME = "net/warpgame/engine/core/property/Property";
    public static final String EVENT_CLASS_NAME = "net/warpgame/engine/core/event/Event";
    public static final String SERIALIZATION_CLASS_NAME = "net/warpgame/engine/core/serialization/Serialization";

    private static final Class[] preprocessedTypes = {Property.class, Event.class, Serialization.class};
    private static final EngineRuntimePreprocessor PREPROCESSOR = new EngineRuntimePreprocessor(preprocessedTypes);
    private static final Processor PROCESSOR = createProcessor();

    private IdRegistry idRegistry; //IDK XD TODO sth


    private static Processor<ClassNode> createProcessor() {
        IdCodeGeneratorProcessor propertyIdGenerator = new IdCodeGeneratorProcessor(PROPERTY_CLASS_NAME, PREPROCESSOR);
        IdCodeGeneratorProcessor eventIdGenerator = new IdCodeGeneratorProcessor(EVENT_CLASS_NAME, PREPROCESSOR);
//        IdCodeGeneratorProcessor serializationIdGenerator = new IdCodeGeneratorProcessor(SERIALIZATION_CLASS_NAME, PREPROCESSOR);
        IdCallProcessor idCallProcessor = new IdCallProcessor(preprocessedTypes, PREPROCESSOR);
        IdAnnotationProcessor idAnnotationProcessor = new IdAnnotationProcessor(PREPROCESSOR);
        return new ComposedProcessor<>(propertyIdGenerator, eventIdGenerator, idCallProcessor, idAnnotationProcessor);
    }

    protected void load() {
        EngineClassLoader classLoader = (EngineClassLoader) Thread.currentThread().getContextClassLoader();
        PREPROCESSOR.preprocess();
        this.idRegistry = new IdRegistry(PREPROCESSOR);
        startProcessing(classLoader);
    }

    private void startProcessing(EngineClassLoader classLoader) {
        PROCESSOR.initializeProcessing();
        classLoader.startProcessing(PROCESSOR);
    }

    public IdRegistry getIdRegistry() {
        return idRegistry;
    }
}
