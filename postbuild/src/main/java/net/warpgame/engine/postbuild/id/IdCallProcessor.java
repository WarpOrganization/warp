package net.warpgame.engine.postbuild.id;

import net.warpgame.engine.postbuild.buildclass.CodeProcessor;
import net.warpgame.engine.postbuild.processing.Context;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2018-08-11 at 22
 */
public class IdCallProcessor implements CodeProcessor {

    private static final Logger logger = LoggerFactory.getLogger(IdCallProcessor.class);

    private static final String METHOD_NAME = "getTypeId";
    private static final String METHOD_DECS = "(Ljava/lang/Class;)I";

    private Set<String> processedClasses;
    private IdRegistry idRegistry;

    public IdCallProcessor(Set<String> processedClasses) {
        this.processedClasses = processedClasses;
    }

    @Override
    public ClassNode process(ClassNode classNode, Context context) {
        this.idRegistry = context.require(IdRegistry.class);
        for (MethodNode method : classNode.methods) {
            processMethod(method, classNode);
        }
        return classNode;
    }

    private void processMethod(MethodNode methodNode, ClassNode classNode) {
        InsnList instructions = methodNode.instructions;
        AbstractInsnNode[] original = instructions.toArray();
        AbstractInsnNode[] insnNodesCopy = Arrays.copyOf(original, original.length);
        processInstructions(instructions, insnNodesCopy, methodNode, classNode);
    }


    private void processInstructions(
            InsnList instructions,
            AbstractInsnNode[] insnNodes,
            MethodNode methodNode,
            ClassNode classNode
    ) {
        LdcInsnNode lastLdc = null;
        for (int i = 0; i < insnNodes.length; i++) {
            AbstractInsnNode insn = insnNodes[i];
            if (insn.getOpcode() == Opcodes.LDC) {
                lastLdc = (LdcInsnNode) insn;
            } else if (insn.getOpcode() == Opcodes.INVOKESTATIC && matchesCallPattern((MethodInsnNode) insn)) {
                processInvocation(instructions, lastLdc, (MethodInsnNode) insn, methodNode, classNode);
            }
        }
    }

    private void processInvocation(
            InsnList instructions,
            LdcInsnNode lastLdc,
            MethodInsnNode insn,
            MethodNode methodNode,
            ClassNode classNode
    ) {
        Optional<IntInsnNode> bipushId = getIdPushInsn(lastLdc);
        if (bipushId.isPresent()) {
            instructions.insertBefore(lastLdc, bipushId.get());
            instructions.remove(lastLdc);
            instructions.remove(insn);
            logger.debug("Replaced invocation in {}:{} for {}", classNode.name, methodNode.name, lastLdc.cst);
        } else {
            logger.error("Couldn't resolve id for {}. Skipping call, project may not work properly.", lastLdc.cst);
        }
    }

    private Optional<IntInsnNode> getIdPushInsn(LdcInsnNode lastLdc) {
        return getTypeId(lastLdc)
                .map(typeId -> new IntInsnNode(Opcodes.BIPUSH, typeId));
    }

    private Optional<Integer> getTypeId(LdcInsnNode lastLdc) {
        Type type = (Type) lastLdc.cst;
        return idRegistry.get(toRegistryClassName(type));
    }

    private String toRegistryClassName(Type type) {
        return type.getClassName().replace(".", "/");
    }

    private boolean matchesCallPattern(MethodInsnNode methodInsnNode) {
        for (String classNames : processedClasses) {
            String ownerClassName = methodInsnNode.owner;
            if (classNames.equals(ownerClassName)
                    && methodInsnNode.name.equals(METHOD_NAME)
                    && methodInsnNode.desc.equals(METHOD_DECS))
                return true;
        }
        return false;
    }

}
