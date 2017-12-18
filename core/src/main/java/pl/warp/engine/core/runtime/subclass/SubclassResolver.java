package pl.warp.engine.core.runtime.subclass;


import org.objectweb.asm.tree.ClassNode;
import pl.warp.engine.core.runtime.processing.Processor;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class SubclassResolver implements Processor{

    private String[] superclasses;

    public SubclassResolver(String[] superclasses) {
        this.superclasses = superclasses;
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
