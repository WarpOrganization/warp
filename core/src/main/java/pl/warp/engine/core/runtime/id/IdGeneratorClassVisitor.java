package pl.warp.engine.core.runtime.id;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class IdGeneratorClassVisitor extends ClassVisitor {

    private String superclassName;

    public IdGeneratorClassVisitor(String superclassName) {
        super(Opcodes.ASM5);
        this.superclassName = superclassName;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

}
