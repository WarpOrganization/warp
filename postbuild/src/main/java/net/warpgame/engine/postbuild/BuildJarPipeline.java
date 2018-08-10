package net.warpgame.engine.postbuild;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.buildclass.jar.BuildJarLoader;
import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.pipeline.RunnablePipeline;

import java.util.jar.JarFile;

import static net.warpgame.engine.postbuild.processing.pipeline.Pipeline.from;

/**
 * @author Jaca777
 * Created 2018-07-01 at 18
 */
public class BuildJarPipeline {
    private String processedPackagesRoot;
    private JarFile jarFile;

    public BuildJarPipeline(String processedPackagesRoot, JarFile jarFile) {
        this.processedPackagesRoot = processedPackagesRoot;
        this.jarFile = jarFile;
    }

    public void process() throws Exception {
        RunnablePipeline pipeline = createPipeline();
        pipeline.run();
    }

    private RunnablePipeline createPipeline() {
        return from(classesLoader())
                .via(new BuildProcessor())
                .to(classWriter());

    }

    private BuildJarLoader classesLoader() {
        return new BuildJarLoader(processedPackagesRoot, jarFile);
    }


    private Sink<BuildClasses> classWriter() {
        return null;
    }


}

