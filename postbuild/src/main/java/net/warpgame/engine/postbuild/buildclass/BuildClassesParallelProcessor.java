package net.warpgame.engine.postbuild.buildclass;


import net.warpgame.engine.postbuild.processing.ParallelProcessor;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jaca777
 * Created 2018-08-11 at 23
 */
public abstract class BuildClassesParallelProcessor extends ParallelProcessor<BuildClasses, BuildClasses, ClassNode, ClassNode> {
    public BuildClassesParallelProcessor(String name) {
        super(name);
    }

    public BuildClassesParallelProcessor() {
    }

    @Override
    protected List<ClassNode> getElements(BuildClasses buildClasses) {
        return Arrays.asList(buildClasses.getBuildClasses());
    }

    @Override
    protected BuildClasses mapResults(List<ClassNode> results) {
        BuildClasses buildClasses = new BuildClasses();
        results.forEach(buildClasses::putClass);
        return buildClasses;
    }


}
