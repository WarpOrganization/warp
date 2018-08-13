package net.warpgame.engine.postbuild.buildclass;

import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Sink;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public class BuildClassesUpdater implements Sink<BuildClasses> {

    private BuildClasses jarClasses;

    public BuildClassesUpdater(BuildClasses jarClasses) {
        this.jarClasses = jarClasses;
    }

    @Override
    public void process(BuildClasses buildClasses, Context c) {
        for (ClassNode classNode : buildClasses.getBuildClasses()) {
            jarClasses.putClass(classNode);
        }
    }
}
