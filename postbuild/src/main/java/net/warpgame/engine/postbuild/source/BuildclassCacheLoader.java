package net.warpgame.engine.postbuild.source;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;

/**
 * @author Jaca777
 * Created 2018-08-12 at 00
 */
public class BuildclassCacheLoader implements Processor<BuildClasses, BuildclassCache> {
    @Override
    public BuildclassCache process(BuildClasses buildClasses, Context context) {
        return new BuildclassCache(); //todo
    }
}
