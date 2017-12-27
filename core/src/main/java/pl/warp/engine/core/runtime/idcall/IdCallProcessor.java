package pl.warp.engine.core.runtime.idcall;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import pl.warp.engine.core.runtime.processing.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class IdCallProcessor implements Processor<ClassNode> {
    private Class[] superclasses;

    public IdCallProcessor(Class[] superclasses) {
        this.superclasses = superclasses;
    }

    @Override
    public void process(ClassNode classNode) {
        List<MethodNode> methods = new ArrayList<>();
        for (MethodNode method : (List<MethodNode>) classNode.methods) {
            MethodNode processed = processMethod(method);
            methods.add(processed);
        }
        classNode.methods = methods;
    }

    private MethodNode processMethod(MethodNode methodNode) {
        IdCallMethodVisitor callMethodVisitor = getVisitorWithMethodData(methodNode);
        methodNode.accept(callMethodVisitor);
        return callMethodVisitor;
    }

    private IdCallMethodVisitor getVisitorWithMethodData(MethodNode methodNode) {
        String[] exceptions = (String[]) methodNode.exceptions
                .toArray(new String[methodNode.exceptions.size()]);
        return new IdCallMethodVisitor(
                Opcodes.ASM6,
                methodNode.access,
                methodNode.name,
                methodNode.desc,
                methodNode.signature,
                exceptions
        );
    }

}
