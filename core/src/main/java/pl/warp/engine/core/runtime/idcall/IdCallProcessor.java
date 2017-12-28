package pl.warp.engine.core.runtime.idcall;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.warp.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import pl.warp.engine.core.runtime.processing.Processor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jaca777
 * Created 2017-12-20 at 23
 */
public class IdCallProcessor implements Processor<ClassNode> {
    private static final Logger logger = LoggerFactory.getLogger(IdCallProcessor.class);

    private Class[] classes;
    private EngineRuntimePreprocessor preprocessor;

    private static final String METHOD_NAME = "getTypeId";
    private static final String METHOD_DECS = "(Ljava/lang/Class;)I";

    public IdCallProcessor(Class[] classes, EngineRuntimePreprocessor preprocessor) {
        this.classes = classes;
        this.preprocessor = preprocessor;
    }


    @Override
    public void process(ClassNode classNode) {
        for (MethodNode method : (List<MethodNode>) classNode.methods) {
            processMethod(method);
        }
    }

    private void processMethod(MethodNode methodNode) {
        InsnList instructions = methodNode.instructions;
        AbstractInsnNode[] original = instructions.toArray();
        AbstractInsnNode[] insnNodesCopy = Arrays.copyOf(original, original.length);
        processInstructions(instructions, insnNodesCopy);
    }


    private void processInstructions(InsnList instructions, AbstractInsnNode[] insnNodes) {
        int lastLdcIndex = -1;
        LdcInsnNode lastLdc = null;
        for (int i = 0; i < insnNodes.length; i++) {
            AbstractInsnNode insn = insnNodes[i];
            if (insn.getOpcode() == Opcodes.LDC) {
                lastLdcIndex = i;
                lastLdc = (LdcInsnNode) insn;
            } else if (insn.getOpcode() == Opcodes.INVOKESTATIC && lastLdcIndex == i - 1) {
                processInvocation(instructions, lastLdc, (MethodInsnNode) insn);
            }
        }
    }

    private void processInvocation(InsnList instructions, LdcInsnNode lastLdc, MethodInsnNode insn) {
        if (matchesCallPattern(insn)) {
            IntInsnNode bipushId = getIdPushInsn(lastLdc);
            instructions.insertBefore(lastLdc, bipushId);
            instructions.remove(lastLdc);
            instructions.remove(insn);
        }
    }

    private IntInsnNode getIdPushInsn(LdcInsnNode lastLdc) {
        int typeId = getTypeId(lastLdc);
        return new IntInsnNode(Opcodes.BIPUSH, typeId);
    }

    private int getTypeId(LdcInsnNode lastLdc) {
        Type type = (Type) lastLdc.cst;
        return preprocessor.getId(type.getClassName());
    }

    private boolean matchesCallPattern(MethodInsnNode methodInsnNode) {
        for (Class aClass : classes) {
            String ownerClassName = toClassName(methodInsnNode.owner);
            if (aClass.getName().equals(ownerClassName)
                    && methodInsnNode.name.equals(METHOD_NAME)
                    && methodInsnNode.desc.equals(METHOD_DECS))
                return true;
        }
        return false;
    }

    public String toClassName(String internalName) {
        return internalName.replace("/", ".");
    }
}
