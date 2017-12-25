package pl.warp.engine.core.runtime.idcall;

import org.objectweb.asm.tree.ClassNode;
import pl.warp.engine.core.runtime.processing.Processor;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class IdCallProcessor implements Processor<ClassNode> {
    private String[] superclasses;

    public IdCallProcessor(String[] superclasses) {
        this.superclasses = superclasses;
    }


    @Override
    public void process(ClassNode classNode) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
