package net.warpgame.engine.postbuild.buildclass.jar;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Sink;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipException;

import static java.util.stream.Collectors.toMap;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public class BuildJarWriter implements Sink<BuildClasses> {

    private static final Logger logger = LoggerFactory.getLogger(BuildJarWriter.class);

    private String destination;
    private JarFile initialFile;

    public BuildJarWriter(String destination, JarFile initialFile) {
        this.destination = destination;
        this.initialFile = initialFile;
    }

    @Override
    public void process(BuildClasses buildClasses, Context context) {
        logger.debug("Writing to file " + destination);
        try {
            Map<String, ClassNode> updatedClasses = loadUpdatedClasses(buildClasses);
            deleteIfExists(destination);
            JarOutputStream out = new JarOutputStream(new FileOutputStream(destination));
            Enumeration<JarEntry> entries = initialFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry nextEntry = entries.nextElement();
                String name = nextEntry.getName();
                JarEntry entryCopy = new JarEntry(nextEntry);
                entryCopy.setCompressedSize(-1);
                try {
                    out.putNextEntry(entryCopy);
                    if (updatedClasses.containsKey(name)) {
                        writeUpdatedClass(updatedClasses, out, name);
                    } else {
                        writeOldEntry(out, nextEntry);
                    }
                } catch (ZipException ex) {
                    logger.error("Error occurred while writing to " + entryCopy.getName() + ". Skipping.", ex);
                }
            }
            logger.debug("Done writing to file " + destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void deleteIfExists(String destination) {
        File f = new File(destination);
        if (f.exists()) {
            logger.debug("File " + f.getName() + " already exists. Deleting it.");
            f.delete();
        }
    }

    private void writeUpdatedClass(
            Map<String, ClassNode> updatedClasses,
            JarOutputStream out,
            String name
    ) throws IOException {
        ClassNode classNode = updatedClasses.get(name);
        ClassWriter classWriter = new ClassWriter(Opcodes.ASM6);
        classNode.accept(classWriter);
        byte[] bytes = classWriter.toByteArray();
        IOUtils.write(bytes, out);
    }

    private void writeOldEntry(JarOutputStream out, JarEntry nextEntry) throws IOException {
        InputStream in = initialFile.getInputStream(nextEntry);
        IOUtils.copy(in, out);
    }

    private Map<String, ClassNode> loadUpdatedClasses(BuildClasses buildClasses) {
        return Arrays.stream(buildClasses.getBuildClasses())
                .collect(toMap(this::toClassFileName, n -> n));
    }

    private String toClassFileName(ClassNode node) {
        return node.name + ".class";
    }
}
