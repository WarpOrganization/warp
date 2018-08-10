package net.warpgame.engine.postbuild.id;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2018-08-11 at 01
 */
public class IdGenerator implements Processor<BuildClasses, IdRegistry> {

    private String superclassName;

    public IdGenerator(String superclassName) {
        this.superclassName = superclassName;
    }

    @Override
    public IdRegistry process(BuildClasses buildClasses, Context context) {
        IdRegistry registry = context.getOrCreate(IdRegistry.class, IdRegistry::new);
        ClassNode[] classes = buildClasses.getBuildClasses();
        for (int i = 0; i < classes.length; i++) {
            registry.put(superclassName, classes[i].name, i);
        }
        return registry;
    }
}
