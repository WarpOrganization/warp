package pl.warp.engine.graphics.debug;

import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.regex.Pattern;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * Created by Szymon on 2015-05-30.
 */
public class DebugCallsInjector {

    public static void inject(ClassNode clazz) {
        List<MethodNode> methods = clazz.methods;
        for (MethodNode method : methods) {
            injectCalls(method);
        }
    }

    private static final MethodInsnNode ERROR_CHECK_CALL = new MethodInsnNode(INVOKESTATIC, "pl/warp/engine/graphics/GLErrors", "checkOGLErrors", "()V", false);

    private static void injectCalls(MethodNode method) {
        AbstractInsnNode[] instructions = method.instructions.toArray();
        InsnList newStack = new InsnList();
        newStack.add(method.instructions);
        for (AbstractInsnNode instruction : instructions)
            processInstruction(newStack, instruction);
        method.instructions = newStack;
    }

    private static void processInstruction(InsnList newStack, AbstractInsnNode instruction) {
        if (instruction.getOpcode() == INVOKESTATIC) {
            MethodInsnNode methodInvoc = (MethodInsnNode) instruction;
            if (isOglInstruction(methodInvoc)) {
                InsnList tracerCall = new InsnList();
                tracerCall.add(ERROR_CHECK_CALL);
                newStack.insert(methodInvoc, tracerCall);
            }
        }
    }

    private static final Pattern GL_PATTERN = Pattern.compile("org/lwjgl/.+");

    private static boolean isOglInstruction(MethodInsnNode methodInvok) {
        return GL_PATTERN.matcher(methodInvok.owner).matches() && !methodInvok.name.equals("glGetError");
    }

}
