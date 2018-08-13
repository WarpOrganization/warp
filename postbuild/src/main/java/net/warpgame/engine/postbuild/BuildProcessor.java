package net.warpgame.engine.postbuild;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.classtree.ClassTreeResolver;
import net.warpgame.engine.postbuild.id.IdCallProcessor;
import net.warpgame.engine.postbuild.id.IdProcessor;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.StoreInContextSink;
import net.warpgame.engine.postbuild.source.BuildclassCacheLoader;
import net.warpgame.engine.postbuild.source.CoreCodeProcessor;

import static net.warpgame.engine.postbuild.processing.Processor.around;
import static net.warpgame.engine.postbuild.processing.pipeline.Pipeline.via;

/**
 * @author Jaca777
 * Created 2018-08-06 at 19
 */
public class BuildProcessor implements Processor<BuildClasses, BuildClasses> {
    @Override
    public BuildClasses process(BuildClasses buildClasses, Context c) {
        return  via(loadClassTree())
                .via(loadBuildclassCache())
                .via(idProcessor())
                .via(codeProcessor())
                .process(buildClasses, c);
    }

    private Processor<BuildClasses, BuildClasses> loadClassTree() {
        return around(via(new ClassTreeResolver()).to(new StoreInContextSink<>()));
    }

    private Processor<BuildClasses, BuildClasses>  loadBuildclassCache() {
        return around(via(new BuildclassCacheLoader()).to(new StoreInContextSink<>()));
    }

    private Processor<BuildClasses, BuildClasses> idProcessor() {
        return new IdProcessor();
    }

    private CoreCodeProcessor codeProcessor() {
        return new CoreCodeProcessor(
                new IdCallProcessor(IdProcessor.PROCESSED_CLASSES)
        );
    }
}
