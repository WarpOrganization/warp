package pl.warp.engine.core.runtime.idcall;


import org.objectweb.asm.tree.MethodNode;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class IdCallMethodVisitor extends MethodNode {

    public IdCallMethodVisitor(int api, int access, String name, String desc, String signature, String[] exceptions) {
        super(api, access, name, desc, signature, exceptions);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}

