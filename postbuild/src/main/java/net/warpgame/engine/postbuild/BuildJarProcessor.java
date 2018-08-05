package net.warpgame.engine.postbuild;

import net.warpgame.engine.postbuild.buildclass.jar.BuildJarLoader;
import net.warpgame.engine.postbuild.filters.SubclassFilter;
import net.warpgame.engine.postbuild.processing.Pipeline;
import net.warpgame.engine.postbuild.processing.PrintSink;

import java.io.IOException;
import java.util.jar.JarFile;

/**
 * @author Jaca777
 * Created 2018-07-01 at 18
 */
public class BuildJarProcessor {
    private String processedPackagesRoot;
    private JarFile jarFile;

    public BuildJarProcessor(String processedPackagesRoot, JarFile jarFile) {
        this.processedPackagesRoot = processedPackagesRoot;
        this.jarFile = jarFile;
    }

    public void process() throws IOException {
        Pipeline pipeline = createPipeline();
        pipeline.run();
    }

    private Pipeline createPipeline() {
        return Pipeline
                .from(new BuildJarLoader(processedPackagesRoot, jarFile))
                .via(new SubclassFilter("Property"))
                .to(new PrintSink<>(s -> s.getBuildClasses().toString()));
    }


}

