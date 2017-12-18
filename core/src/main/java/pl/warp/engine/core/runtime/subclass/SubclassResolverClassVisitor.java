package pl.warp.engine.core.runtime.subclass;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class SubclassResolverClassVisitor extends ClassVisitor {


    public SubclassResolverClassVisitor() {
        super(Opcodes.ASM5);
    }
}
