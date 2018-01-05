package net.warpgame.engine.core.runtime;

import net.warpgame.engine.core.runtime.processing.Processor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jaca777
 * Created 2017-12-17 at 17
 */
public class EngineClassLoader extends ClassLoader {


    private static final String RUNTIME_PREFIX = "net.warpgame.engine.core.runtime";
    private boolean preprocessing = true;

    private Processor<ClassNode> processor;

    public EngineClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!isProcessed(name)) {
            Class c = findLoadedClass(name);
            if (c != null) return c;
            else return super.loadClass(name);
        } else return loadProcessedClass(name);
    }

    private Class<?> loadProcessedClass(String name) {
        String definedName = preprocessing ? "_" + name : name;
        Class c = findLoadedClass(definedName);
        if (c != null) return c;
        else {
            try {
                return loadAndProcess(name, definedName);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Error occured while loading %s class", name), e);
            }
        }
    }

    private Class<?> loadAndProcess(String name, String definedName) throws IOException {
        String resourceName = name.replace('.', '/') + ".class";
        InputStream classData = getResourceAsStream(resourceName);
        byte[] code = loadAndProcessData(classData);
        return defineClass(definedName, code, 0, code.length);
    }

    private byte[] loadAndProcessData(InputStream classData) throws IOException {
        ClassReader reader = new ClassReader(classData);
        ClassNode node = new ClassNode(Opcodes.ASM6);
        reader.accept(node, 0);
        if (processor != null) processor.process(node);
        return write(node);
    }

    private byte[] write(ClassNode node) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }


    private boolean isProcessed(String name) {
        return name.startsWith("net.warpgame") && !name.startsWith(RUNTIME_PREFIX);
    }

    public void startProcessing(Processor processor) {
        this.preprocessing = false;
        this.processor = processor;
    }
}
