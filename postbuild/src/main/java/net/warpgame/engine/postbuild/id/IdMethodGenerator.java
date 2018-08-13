package net.warpgame.engine.postbuild.id;

import net.warpgame.engine.postbuild.buildclass.BuildClassesParallelProcessor;
import net.warpgame.engine.postbuild.processing.Context;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jaca777
 * Created 2018-08-11 at 22
 */
public class IdMethodGenerator extends BuildClassesParallelProcessor {

    private static final Logger logger = LoggerFactory.getLogger(IdMethodGenerator.class);

    public static final String GET_ID_METHOD_NAME = "getType";
    public static final String GET_ID_METHOD_DESC = "()I";

    private String superclassName;
    private IdRegistry idRegistry;

    public IdMethodGenerator(String superclassName) {
        super("id method generator for " + superclassName);
        this.superclassName = superclassName;
    }

    @Override
    protected void init(Context c) {
        this.idRegistry = c.require(IdRegistry.class);
    }

    @Override
    protected ClassNode processElement(ClassNode classNode) {
        checkIfMethodPresent(classNode);
        int id = idRegistry.get(superclassName, classNode.name).get();
        generateMethod(classNode, id);
        return classNode;
    }

    protected void checkIfMethodPresent(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(GET_ID_METHOD_NAME) && method.desc.equals(GET_ID_METHOD_DESC)) {
                logger.warn("getId method should not be overridden by the user");
                logger.warn("Overriding user-declared method getId for class " + classNode.name);
                classNode.methods.remove(method);
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
