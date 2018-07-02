package net.warpgame.engine.postbuild;

import net.warpgame.engine.postbuild.classtree.ClassTree;
import net.warpgame.engine.postbuild.classtree.ClassTreeResolver;
import net.warpgame.engine.postbuild.loader.BuildClasses;
import net.warpgame.engine.postbuild.loader.BuildJarLoader;

import java.io.IOException;
import java.util.jar.JarFile;

/**
 * @author Jaca777
 * Created 2018-07-01 at 18
 */
public class BuildJarProcessor {
    private String processedPackagesRoot;

    public BuildJarProcessor(String processedPackagesRoot) {
        this.processedPackagesRoot = processedPackagesRoot;
    }

    public void process(JarFile jarFile) throws IOException {
        BuildClasses buildClasses = loadBuildClasses(jarFile);
        ClassTree classTree = resolveClassTree(buildClasses);
    }

    private BuildClasses loadBuildClasses(JarFile jarFile) throws IOException {
        BuildJarLoader buildJarLoader = new BuildJarLoader(processedPackagesRoot);
        return buildJarLoader.loadClasses(jarFile);
    }


    private ClassTree resolveClassTree(BuildClasses buildClasses) {
        ClassTreeResolver resolver = new ClassTreeResolver(buildClasses);
        return resolver.resolveClassTree();
    }

}

