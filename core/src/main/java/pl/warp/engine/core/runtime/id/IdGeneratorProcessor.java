package pl.warp.engine.core.runtime.id;


import org.objectweb.asm.tree.ClassNode;
import pl.warp.engine.core.runtime.processing.Processor;

/**
 * @author Jaca777
 * Created 2017-12-17 at 19
 */
public class IdGeneratorProcessor implements Processor {

    private String superclassName;

    public IdGeneratorProcessor(String superclassName) {
        this.superclassName = superclassName;
    }

    @Override
    public void process(ClassNode classNode) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void finalizeProcessing() {
        //TODO
        throw new UnsupportedOperationException();
    }
}
