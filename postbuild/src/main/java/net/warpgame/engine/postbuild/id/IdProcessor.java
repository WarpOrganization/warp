package net.warpgame.engine.postbuild.id;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.filters.SubclassFilter;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.pipeline.Pipeline;

/**
 * @author Jaca777
 * Created 2018-08-08 at 19
 */
public class IdProcessor implements Processor<BuildClasses, BuildClasses> {

    @Override
    public BuildClasses process(BuildClasses buildClasses, Context c) {
        Pipeline.via(new SubclassFilter("net/warpgame/engine/core/property/Property"))
                .process(buildClasses, c);
        return buildClasses;
    }
}
