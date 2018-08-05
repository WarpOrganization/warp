package net.warpgame.engine.postbuild.buildclass;

import org.objectweb.asm.tree.ClassNode;

import java.util.*;

/**
 * @author Jaca777
 * Created 2018-07-01 at 18
 */
public class BuildClasses {
    private Map<String, ClassNode> buildClasses = new HashMap<>();

    public void putClass(ClassNode buildClass) {
        buildClasses.put(buildClass.name, buildClass);
    }

    public Collection<ClassNode> getBuildClasses() {
        return buildClasses.values();
    }

    public ClassNode getClassByName(String name) {
        return buildClasses.get(name);
    }
}
