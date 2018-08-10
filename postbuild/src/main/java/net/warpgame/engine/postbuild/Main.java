package net.warpgame.engine.postbuild;

import java.util.jar.JarFile;

/**
 * @author Jaca777
 * Created 2018-07-01 at 17
 */
public class Main {

    //cos do szukania subklas <- subklasy subklas tyż
    //cos do szukania annotowanych
    //1. Nadaje ID eventom i property
    //2. Inline'uje calle do getTypeID - szukanie calli
    //3. Generowanie de/serializatorow
    //4. serwisy

    public static void main(String[] args) throws Exception {
        String processedJarName = args[0];
        String processedPackageRoot = args[1];
        JarFile jarFile = new JarFile(processedJarName);

        BuildJarPipeline buildJarProcessor = new BuildJarPipeline(processedPackageRoot, jarFile);
        buildJarProcessor.process();
    }

}
