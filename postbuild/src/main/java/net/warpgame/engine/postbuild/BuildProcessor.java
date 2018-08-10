package net.warpgame.engine.postbuild;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.classtree.ClassTreeResolver;
import net.warpgame.engine.postbuild.id.IdProcessor;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.StoreInContextSink;

import static net.warpgame.engine.postbuild.processing.Processor.around;
import static net.warpgame.engine.postbuild.processing.pipeline.Pipeline.via;

/**
 * @author Jaca777
 * Created 2018-08-06 at 19
 */
public class BuildProcessor implements Processor<BuildClasses, BuildClasses> {
    @Override
    public BuildClasses process(BuildClasses buildClasses, Context c) {
        return  via(around(via(new ClassTreeResolver()).to(new StoreInContextSink<>())))
                .via(idProcessor())
                .process(buildClasses, c);
    }


    private Processor<BuildClasses, BuildClasses> idProcessor() {
        return new IdProcessor();
    }
}
