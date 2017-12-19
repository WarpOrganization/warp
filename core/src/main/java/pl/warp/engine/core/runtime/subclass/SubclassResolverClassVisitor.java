package pl.warp.engine.core.runtime.subclass;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class SubclassResolverClassVisitor extends ClassVisitor {


    public SubclassResolverClassVisitor() {
        super(Opcodes.ASM5);
    }
}
