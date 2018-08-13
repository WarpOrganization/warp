package net.warpgame.engine.postbuild.id;

import com.google.common.collect.ImmutableSet;
import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.filters.SubclassFilter;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.pipeline.Pipeline;

import java.util.Set;

import static net.warpgame.engine.postbuild.processing.Processor.around;
import static net.warpgame.engine.postbuild.processing.pipeline.Pipeline.via;

/**
 * @author Jaca777
 * Created 2018-08-08 at 19
 */
public class IdProcessor implements Processor<BuildClasses, BuildClasses> {

    public static final Set<String> PROCESSED_CLASSES = ImmutableSet.of(
            "net/warpgame/engine/core/property/Property",
            "net/warpgame/engine/core/event/Event"
    );


    @Override
    public BuildClasses process(BuildClasses buildClasses, Context c) {
        Pipeline.via(classIdProcessor("net/warpgame/engine/core/property/Property"))
                .via(classIdProcessor("net/warpgame/engine/core/event/Event"))
                .process(buildClasses, c);
        return buildClasses;
    }

    private Processor<BuildClasses, BuildClasses> classIdProcessor(String className) {
        return around(via(new SubclassFilter(className))
                .via(new IdGenerator(className))
                .via(new IdMethodGenerator(className))
        );
    }

}
