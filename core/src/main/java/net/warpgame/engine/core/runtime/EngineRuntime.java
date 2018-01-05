package net.warpgame.engine.core.runtime;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.runtime.idcall.IdCallProcessor;
import net.warpgame.engine.core.runtime.idmethodgen.IdCodeGeneratorProcessor;
import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import net.warpgame.engine.core.runtime.processing.ComposedProcessor;
import net.warpgame.engine.core.runtime.processing.Processor;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class EngineRuntime {

    public static final String PREFIX = "net.warpgame";
    public static final String PROPERTY_CLASS_NAME = "net/warpgame/engine/core/property/Property";
    public static final String EVENT_CLASS_NAME = "net/warpgame/engine/core/event/Event";

    private static final Class[] preprocessedTypes = {Property.class, Event.class};
    private static final EngineRuntimePreprocessor PREPROCESSOR = new EngineRuntimePreprocessor(preprocessedTypes);
    private static final Processor PROCESSOR = createProcessor();

    private static Processor<ClassNode> createProcessor() {
        IdCodeGeneratorProcessor propertyIdGenerator = new IdCodeGeneratorProcessor(PROPERTY_CLASS_NAME, PREPROCESSOR);
        IdCodeGeneratorProcessor eventIdGenerator = new IdCodeGeneratorProcessor(EVENT_CLASS_NAME, PREPROCESSOR);
        IdCallProcessor idCallProcessor = new IdCallProcessor(preprocessedTypes, PREPROCESSOR);
        return new ComposedProcessor(propertyIdGenerator, eventIdGenerator, idCallProcessor);
    }

    public void load() {
        EngineClassLoader classLoader = (EngineClassLoader) Thread.currentThread().getContextClassLoader();
        PREPROCESSOR.preprocess();
        startProcessing(classLoader);
    }

    private void startProcessing(EngineClassLoader classLoader) {
        PROCESSOR.initializeProcessing();
        classLoader.startProcessing(PROCESSOR);
    }
}
