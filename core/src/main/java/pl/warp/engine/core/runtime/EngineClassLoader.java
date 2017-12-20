package pl.warp.engine.core.runtime;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.warp.engine.core.runtime.processing.Processor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jaca777
 * Created 2017-12-17 at 17
 */
public class EngineClassLoader extends ClassLoader {

    private boolean preprocessing = false;

    private Processor processor;

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
        System.out.println("Loading " + definedName);
        Class c = findLoadedClass(definedName);
        if (c != null) return c;
        else {
            try {
                return loadAndProcess(name, definedName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private Class<?> loadAndProcess(String name, String definedName) throws IOException {
        String resourceName = name.replace('.', '/') + ".class";
        InputStream classData = getResourceAsStream(resourceName);
        ClassReader reader = new ClassReader(classData);
        ClassNode node = new ClassNode(Opcodes.ASM6);
        reader.accept(node, 0);
        processor.process(node);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        byte code[] = writer.toByteArray();
        return defineClass(name, code, 0, code.length);
    }

    private boolean isProcessed(String name) {
        return name.startsWith("pl.warp");
    }

    public void startProcessing(Processor processor) {
        this.preprocessing = false;
        this.processor = processor;
    }
}
