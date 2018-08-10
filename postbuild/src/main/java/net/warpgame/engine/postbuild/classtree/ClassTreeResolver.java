package net.warpgame.engine.postbuild.classtree;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2018-07-01 at 17
 */
public class ClassTreeResolver implements Processor<BuildClasses,  ClassTree> {

    private BuildClasses buildClasses;
    private Multimap<String, String> inheritanceLinks = HashMultimap.create();

    @Override
    public ClassTree process(BuildClasses buildClasses, Context c) {
        this.buildClasses = buildClasses;
        createInheritanceLinks();
        return new ClassTree(inheritanceLinks);
    }

    private void createInheritanceLinks() {
        for(ClassNode classNode : buildClasses.getBuildClasses()) {
            inheritanceLinks.put(classNode.superName, classNode.name);
        }
    }


}