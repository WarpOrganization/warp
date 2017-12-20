package pl.warp.engine.core.runtime.idgen;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class IdMethodGenerator {

    public static final String GET_ID_METHOD_NAME = "getId";
    public static final String GET_ID_METHOD_DESC = "()I";

    private static final Logger logger = LoggerFactory.getLogger(IdMethodGenerator.class);

    public void generateIdMethod(int id, ClassNode classNode) {
        checkIfMethodPresent(classNode);
        generateMethod(classNode, id);
    }

    protected void checkIfMethodPresent(ClassNode classNode) {
        for (MethodNode method : (List<MethodNode>) classNode.methods) {
            if (method.name.equals(GET_ID_METHOD_NAME) && method.desc.equals(GET_ID_METHOD_DESC)) {
                logger.warn("Overriding user-declared method getId for class " + classNode.name);
            }
        }
    }

    private void generateMethod(ClassNode classNode, int id) {
        MethodVisitor getIdMethod = classNode.visitMethod(
                Opcodes.ACC_PUBLIC,
                GET_ID_METHOD_NAME,
                GET_ID_METHOD_DESC,
                null,
                null
        );
        getIdMethod.visitIntInsn(Opcodes.BIPUSH, id);
        getIdMethod.visitInsn(Opcodes.IRETURN);
        getIdMethod.visitMaxs(1, 0);
        getIdMethod.visitEnd();
    }
}
