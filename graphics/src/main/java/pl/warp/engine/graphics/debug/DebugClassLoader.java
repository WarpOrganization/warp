package pl.warp.engine.graphics.debug;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Szymon on 2015-05-30.
 */
public class DebugClassLoader extends ClassLoader{

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("pl.warp"))
            return injectDebugging(name);
        else return super.loadClass(name, resolve);
    }

    private Class<?> injectDebugging(String name) throws ClassNotFoundException {
        try {
            String res = name.replace('.', '/') + ".class";
            InputStream is = getResourceAsStream(res);
            ClassReader reader = new ClassReader(is);
            ClassNode node = new ClassNode(Opcodes.ASM5);
            reader.accept(node, 0);
            if (res.startsWith("pl/warp/engine/graphics")) CallsInjector.inject(node, name);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            byte code[] = writer.toByteArray();
            return defineClass(name, code, 0, code.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}