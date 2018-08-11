package net.warpgame.engine.postbuild.id;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Context;
import net.warpgame.engine.postbuild.processing.Processor;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jaca777
 * Created 2018-08-11 at 01
 */
public class IdGenerator implements Processor<BuildClasses, BuildClasses> {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    private String superclassName;

    public IdGenerator(String superclassName) {
        this.superclassName = superclassName;
    }

    @Override
    public BuildClasses process(BuildClasses buildClasses, Context context) {
        IdRegistry registry = context.getOrCreate(IdRegistry.class, IdRegistry::new);
        ClassNode[] classes = buildClasses.getBuildClasses();
        for (int i = 0; i < classes.length; i++) {
            registry.put(superclassName, classes[i].name, i);
            logger.debug("Assigning id {} to {}", i, classes[i].name);
        }
        return buildClasses;
    }
}
