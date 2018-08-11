package net.warpgame.engine.postbuild;

import java.util.jar.JarFile;

/**
 * @author Jaca777
 * Created 2018-07-01 at 17
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String processedJarName = args[0];
        String destination = args[1];
        String processedPackageRoot = args[2];
        JarFile jarFile = new JarFile(processedJarName);

        BuildJarPipeline buildJarProcessor = new BuildJarPipeline(destination, processedPackageRoot, jarFile);
        buildJarProcessor.process();
    }

}
